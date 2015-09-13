/*****************************************************************
JADE - Java Agent DEvelopment Framework is a framework to develop 
multi-agent systems in compliance with the FIPA specifications.
Copyright (C) 2000 CSELT S.p.A. 

GNU Lesser General Public License

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation, 
version 2.1 of the License. 

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
 *****************************************************************/

package co.scndfndtion.diagmas.agents;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import co.com.scndfndtion.diagmas.tools.DBCatalogue;
import co.com.scndfndtion.diagmas.tools.JSONParser;
import co.scndfndtion.diagmas.diagnostic.Diagnosticator;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class DiagnosticatorAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8492702395968997609L;
	private static final String GSR = "GSR";
	private static final String SWT = "SWT";
	private static final String CPE = "CPE";
	
	// The service code
	private String serviceId;
	// The list of known device agents
	private AID[] deviceAgents;
	List<String> deviceList = new ArrayList<String>();
	
	public Hashtable<AID,String> answerList= new Hashtable<AID,String>();

	AID netAgent;
	String serviceData = null;
	String diagnosis=null;
	int receivers=0;
	
	// Put agent initializations here
	protected void setup() {
		// Printout a welcome message
		System.out.println("Hallo! Diagnosticator-agent "+getAID().getName()+" is ready.");

		createNetworkAgent();
		// Get the serviceId as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			serviceId = (String) args[0];
			addBehaviour(new RequestPerformer());
		}
		else {
			// Make the agent terminate
			System.out.println("No args found");
			doDelete();
		}
	}

	private void startAgents() {
		System.out.println("Service code is "+serviceId);
		
		if(serviceId!=null ){
			serviceData= DBCatalogue.getService(serviceId);
			if(serviceData!=null){
				String cpeIp  = JSONParser.get(serviceData,"cpeIp");
				String gsrIp  = JSONParser.get(serviceData,"gsrIp");
				createDeviceAgent(gsrIp,GSR);
				deviceList.clear();
				deviceList.add(gsrIp);
				
				String swtIp  = JSONParser.get(serviceData,"swtIp");
				createDeviceAgent(swtIp,SWT);
				createDeviceAgent(cpeIp,CPE);	
				deviceList.add(swtIp);
				deviceList.add(cpeIp);
				
				createDeviceAgent("GTRIARA",GSR);	
				createDeviceAgent("MTRIARA",SWT);	
				createDeviceAgent("10.175.248.120",CPE);	
				
				
			}
		}
	}

	private void createDeviceAgent(String cpeIp, String deviceType) {
		//		System.out.println("Creating "+ cpeIp+" as "+ deviceType);
		//		String name=cpeIp;
		//		String agentType="co.scndfndtion.diagmas.agents.DeviceAgent";
		//		jade.wrapper.AgentContainer c = getContainerController();
		//		try{
		//			Object[] args=new Object[2];
		//			args[0]=cpeIp;
		//			args[1]=deviceType;
		//			AgentController a = c.createNewAgent(name, agentType, args);
		//			a.start();
		//		}catch(Exception e){
		//			
		//		}
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd  = new ServiceDescription();
		sd.setType( "net" );
		dfd.addServices(sd);
		DFAgentDescription[] result;
		try {
			result = DFService.search(this, dfd);
			//System.out.println(result.length + " results" );
			if (result.length>0){
				//System.out.println(" " + result[0].getName() );
				netAgent =result[0].getName();
			}

		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(netAgent!=null && cpeIp!=null){
			ACLMessage cfp = new ACLMessage(ACLMessage.REQUEST);
			cfp.addReceiver(netAgent);
			cfp.setContent( cpeIp );
			cfp.setConversationId("creation");
			cfp.setReplyWith("creation"+System.currentTimeMillis()); // Unique value
			this.send( cfp );
		}
	}


	private void createNetworkAgent() {
		System.out.println("Creating NetWork Agent");
		String agentType="co.scndfndtion.diagmas.agents.NetworkAgent";
		String name="mpls";
		jade.wrapper.AgentContainer c = getContainerController();
		try{
			Object[] args=new Object[1];
			args[0]="mpls";
			AgentController a = c.createNewAgent(name, agentType, args);
			a.start();
		}catch(Exception e){

		}
	}


	// Put agent clean-up operations here
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Agent "+getAID().getName()+" terminating.");
	}

	/**
	   Inner class RequestPerformer.
	   This is the behaviour used by DiagnosticatorAgents to request DeviceAgents
	   to run the commands, gather results, and produce a diagnosis
	 */
	private class RequestPerformer extends Behaviour {
	// extends CyclicBehaviour{

		/**
		 * 
		 */
		private static final long serialVersionUID = -2909627541052638659L;
		private static final int FINAL_STEP = 4;
		private static final int SENDING_DIAGNOSIS = 3;
		private static final int RECEIVING_ANSWERS = 2;
		private static final int QUERYING_DEVICES = 1;
		private static final int INITIAL_STEP = 0;
		private AID deviceAnswerer; // The agent who provides the best offer 
		private int repliesCnt = 0; // The counter of replies from seller agents
		private MessageTemplate mt; // The template to receive replies
		private MessageTemplate mtemplate; // The template to receive replies
		private int step = 0;
		private AID diangosisRequester;
		private String serviceToCheck;
		
		public void action() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("device");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template); 
				//System.out.println("Found "+result.length +" device agents:");
				deviceAgents = new AID[result.length];
				for (int i = 0; i < result.length; ++i) {
					deviceAgents[i] = result[i].getName();
					//System.out.println(deviceAgents[i].getName());
				}
			}
			catch (FIPAException fe) {
				fe.printStackTrace();
			}
			
			switch (step) {
			case INITIAL_STEP:
				//System.out.println("---Initial");
				mt = MessageTemplate.MatchConversationId("diagnosis");
				// Receive all answers from device agents
				ACLMessage initialQuery = myAgent.receive(mt);
				if (initialQuery != null) {
					receiveRequest(initialQuery);
				}
				else {
					block();
				}
				break;

			case QUERYING_DEVICES:	
				// Send the query to devices
				//System.out.println("---Querying "+deviceAgents.length +" devices");
				sendMsgs();
				break;

			case RECEIVING_ANSWERS:
				//System.out.println("---Receiving answers");
				// Receive all answers from device agents
				ACLMessage reply = myAgent.receive( mtemplate );
				if (reply != null) {
					receiveAnswers(reply);
				}
				else {
					block();
				}
				break;

			case SENDING_DIAGNOSIS:
				//System.out.println("---Sending diagnosis");
				diagnosis = Diagnosticator.analyze(answerList);
				ACLMessage diagMsg = new ACLMessage(ACLMessage.INFORM);
				diagMsg.addReceiver( diangosisRequester );
				diagMsg.setContent( diagnosis );
				diagMsg.setConversationId("diagnosis");
				//diagMsg.setReplyWith("query"+System.currentTimeMillis()); // Unique value
				myAgent.send(diagMsg);
				step=FINAL_STEP;
				break;
				
			case FINAL_STEP:
				step=INITIAL_STEP;
				break;
			}        
		}

		private void receiveRequest(ACLMessage initialQuery) {
			// Reply received
			if (initialQuery.getPerformative() == ACLMessage.REQUEST) {
				// This is a request for diagnosis
				System.out.println("Received diagnosis request from:"+ initialQuery.getSender().getName());
				diangosisRequester = initialQuery.getSender();
				if(initialQuery.getContent()!=null){
					serviceToCheck = initialQuery.getContent().toString();
					if(serviceToCheck!=null){
						serviceId=serviceToCheck;
						startAgents();
					}
					step = QUERYING_DEVICES;
				}
			}
		}

		private void receiveAnswers(ACLMessage reply) {
			// Reply received
			if ( reply.getPerformative() == ACLMessage.INFORM ) {
				// This is an answer 
				System.out.println("Received from:"+ reply.getSender().getName());
				deviceAnswerer = reply.getSender();
				System.out.println("Received:"+ reply.getContent());
				if(reply.getContent()!=null && answerList!=null){
					answerList.put(deviceAnswerer, reply.getContent().toString() );
				}
			}
			repliesCnt++;
			System.out.println("replies:"+repliesCnt+" length:"+receivers);
			if (repliesCnt >= receivers) {
				// We received all replies
				step = SENDING_DIAGNOSIS; 
			}
		}

		private void sendMsgs() {
			receivers=0;
			
			ACLMessage cfp = new ACLMessage(ACLMessage.REQUEST);
			for (int i = 0; i < deviceAgents.length; ++i) {
				System.out.println("adding receiver:"+ i +" "+ deviceAgents[i].getName() );
				String tmp = deviceAgents[i].getName();
				String[] arr = new String[2];
				arr = tmp.split("@");
				String agentName = arr[0];
				System.out.println("deviceName:"+agentName);
				if( deviceList.contains(agentName)  ){
					cfp.addReceiver( deviceAgents[i] );
					System.out.println("is ok");
					receivers++;
				}else{
					System.out.println("is NOT");
				}
			} 	
			
			cfp.setContent( serviceData );
			cfp.setConversationId("sensor");
			cfp.setReplyWith("sensor"+System.currentTimeMillis()); // Unique value
			
			//if(receivers>0){
			//	System.out.println("query sent to ["+receivers+"] devices.");
			myAgent.send( cfp );
			//}
			// Prepare the template to get answers
			mtemplate = MessageTemplate.and(MessageTemplate.MatchConversationId("sensor"),
					MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
			
			step = RECEIVING_ANSWERS;
			System.out.println("step:"+step);
		}

		public boolean done() {
			boolean isDone=false;
			if (step == FINAL_STEP ) {
				if(diagnosis==null){
					System.out.println("Attempt failed: "+serviceId+" could not be diagnosed.");
				}else{
					System.out.println(diagnosis);
				}
				isDone=true;
				step=INITIAL_STEP;
			}
			return isDone;
		}
		
	}  // End of inner class RequestPerformer
}
