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

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import co.com.scndfndtion.diagmas.tools.DBCatalogue;
import co.com.scndfndtion.diagmas.tools.JSONParser;

public class NetworkAgent extends Agent {
	// Put agent initializations here
	protected void setup() {
		// Register the database service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("net");
		sd.setName("JADE-net");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		// Add the behaviour serving queries from query agents
		addBehaviour(new OfferRequestsServer());
	}

	// Put agent clean-up operations here
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Printout a dismissal message
		System.out.println("Agent "+getAID().getName()+" terminating.");
	}

	/**
     This is invoked by the GUI when the user adds a new book for sale
	 */
	public void updateCatalogue(final String title, final String body) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				//catalogue.put(title, body);
				System.out.println(title+" inserted into catalogue. id:"+title );
			}
		} );
	}

	/**
	   Inner class OfferRequestsServer.
	   This is the behaviour used by Book-seller agents to serve incoming requests 
	   for offer from buyer agents.
	   If the requested book is in the local catalogue the seller agent replies 
	   with a PROPOSE message specifying the price. Otherwise a REFUSE message is
	   sent back.
	 */
	private class OfferRequestsServer extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// CFP Message received. Process it
				String ip = msg.getContent();
				ACLMessage reply = msg.createReply();

				String rawAnswer = getDeviceInfo( ip );
				if (rawAnswer != null) {
					// The requested service is available. Reply with info
					String deviceType = JSONParser.get(rawAnswer, "deviceType");
					createDeviceAgent(ip, deviceType);
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent( "Created-OK" );
				}
				else {
					// The requested service is NOT available.
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("FAIL");
				}
				myAgent.send(reply);
			}
			else {
				block();
			}
		}
	}  // End of inner class OfferRequestsServer
	
	public String getDeviceInfo(String deviceName) {
		return DBCatalogue.getDevice( deviceName );
	}
	
	private void createDeviceAgent(String cpeIp, String deviceType) {	
		System.out.println("Creating "+ cpeIp+" as "+ deviceType);
		String name=cpeIp;
		String agentType="co.scndfndtion.diagmas.agents.DeviceAgent";
		jade.wrapper.AgentContainer c = getContainerController();
		try{
			Object[] args=new Object[2];
			args[0]=cpeIp;
			args[1]=deviceType;
			AgentController a = c.createNewAgent(name, agentType, args);
			a.start();
		}catch(Exception e){
			
		}
	}
}
