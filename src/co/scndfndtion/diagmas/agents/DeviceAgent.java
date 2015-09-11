/*****************************************************************

 *****************************************************************/

package co.scndfndtion.diagmas.agents;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import co.com.scndfndtion.diagmas.tools.ProbeAnswerCoder;
import co.scndfndtion.commands.core.CmdGateway;;

/**
 * Agent to communicate with devices
 * Runs commands on request
 * Informs results from command and own agent info.
 * 
 * @author Alfonso
 *
 */
public class DeviceAgent extends Agent {

	String ip;
	String deviceType;
	// Put agent initializations here
	protected void setup() {
		// Get the ip and devicetype as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			ip = (String) args[0];
			deviceType =(String) args[1];
		}

		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("device");
		sd.setName("JADE-device-sensor");
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
		System.out.println("Device-agent "+getAID().getName()+" terminating.");
	}

	/**
	   Inner class OfferRequestsServer.
	   This is the behaviour used by DeviceAgents to serve incoming requests from DiagnosticatorAgents.
	   If the command were executed, the agent replies INFORM. 
	   Otherwise a REFUSE message is sent back.
	 */
	private class OfferRequestsServer extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// REQUEST Message received. Process it
				String serviceId = msg.getContent();
				ACLMessage reply = msg.createReply();

				String rawAnswer = ProbeAnswerCoder.code( ip, 
						deviceType, 
						CmdGateway.run("check", serviceId));
				
				if (rawAnswer != null) {
					// Execution was successful, so return result and agent info.
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent( rawAnswer );
				}
				else {
					// Execution Refused.
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
			}
			else {
				block();
			}
		}
	}  // End of inner class OfferRequestsServer
}
