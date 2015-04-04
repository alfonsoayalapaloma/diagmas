package co.scndfndtion.diagmas.ontology;

import java.util.ArrayList;
import java.util.List;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.*;
import jade.lang.acl.ACLMessage;

public class FillOntology extends Agent { 

	public void setup(){
	// Prepare the Query-IF message
	ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
	AID checkerAID = null;
	msg.addReceiver(checkerAID);  // sellerAID is the AID of the Seller agent
	Ontology codec = null;
	msg.setLanguage(codec.getName());
	IncidentOntology ontology = null;
	msg.setOntology(ontology.getName());
	// Prepare the content. Optional fields are not set
	Diagnosis cd = new Diagnosis();
	cd.setName("");
	List tests = new ArrayList();
	
	Test t = new Test();
	t.setName("ping");
	tests.add(t);
	
	
	
	cd.setTests(tests);
	
	
//	Owns owns = new Owns();
//	owns.setOwner(checkerAID);
//	owns.setItem(cd);
	
//	try {
//	 // Let JADE convert from Java objects to string
// getContentManager().fillContent(msg, owns);
//	 send(msg);
//	}
//	catch (CodecException ce) {
//	 ce.printStackTrace();
//	}
//	catch (OntologyException oe) {
//	 oe.printStackTrace();
//	} 

	}
}
