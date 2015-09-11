package co.scndfndtion.diagmas.deprecated;

import jade.core.Agent;

public class DiagnosisAgent00  extends Agent {
	
	  protected void setup() {
		  	System.out.println("Hello World! My name is "+getLocalName());
		  	
		  	// Make this agent terminate
		  	doDelete();
		  } 

}
