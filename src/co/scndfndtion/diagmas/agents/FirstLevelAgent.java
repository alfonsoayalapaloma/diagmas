package co.scndfndtion.diagmas.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;

public class FirstLevelAgent extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6416644856962200239L;
	String name="";
	String serviceId="WIN0002";
	AMSAgentDescription [] agents = null;
	private AID diagnosticatorAgent=null;

	protected void setup() {
		System.out.println("Agent "+getLocalName()+" started.");

		System.out.println("Creating "+ serviceId);
		name=serviceId;
		String agentType="co.scndfndtion.diagmas.agents.DiagnosticatorAgent";
		jade.wrapper.AgentContainer c = getContainerController();
		try{
			Object[] args=new Object[1];
			args[0]=serviceId;
			AgentController a = c.createNewAgent(name, agentType, args);
			a.start();
			
		    try {
		        SearchConstraints cnstrains = new SearchConstraints();
		        cnstrains.setMaxResults ( new Long(-1) );
		        agents = AMSService.search( this, new AMSAgentDescription (), cnstrains );
		    }
		    catch (Exception e) {
		    	
		    }
		    for (int i=0; i<agents.length;i++){
			     AID agentID = agents[i].getName();
			     if( name.equals(agentID.getLocalName())  ){
			    	 System.out.println("agentId:"+  agentID.getLocalName());
			    	 diagnosticatorAgent=agentID;
			     }
			}
			
		}catch(Exception e){

		}
		addBehaviour(new DiagnosticatorBehavior() );
	}


	/**
	   Inner class RequestPerformer.
	   This is the behaviour used by Diagnosis agents to request device 
	   agents to run the commands.
	 */
	private class DiagnosticatorBehavior extends Behaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4526974063667076058L;
		int step=0;
		private MessageTemplate mt; // The template to receive replies
		private AID deviceAnswerer;

		@Override
		public void action() {
			if(agents.length>0){
				System.out.println("step:"+step);
				switch (step) {
				case 0:
					// Send the query to diagnosticatorAgent
					ACLMessage cfp = new ACLMessage(ACLMessage.REQUEST);
					System.out.println("adding receiver:"+diagnosticatorAgent.getName() );
					cfp.addReceiver( diagnosticatorAgent );
					cfp.setContent( serviceId );
					cfp.setConversationId("diagnosis");
					cfp.setReplyWith("query"+System.currentTimeMillis()); // Unique value
					myAgent.send(cfp);
					System.out.println("sending...");
					mt = MessageTemplate.and(MessageTemplate.MatchConversationId("diagnosis"),
							MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
					step = 1;
					break;
				case 1:
					// Receive answers
					ACLMessage reply = myAgent.receive(mt);
					if (reply != null) {
						// Reply received
						if (reply.getPerformative() == ACLMessage.INFORM) {
							// This is an answer 
							System.out.println("Received from:"+ reply.getSender().getName());
							deviceAnswerer = reply.getSender();
							if(reply.getContent()!=null){
								System.out.println(reply.getContent());
							}
						}
						// We received all replies
						step = 2; 
					}
					else {
						block();
					}
					break;
				}        
			}
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
