package co.scndfndtion.diagmas.ontology;

import jade.util.leap.List;
public class Service { 
	// implements Concept
	private String name;
	private List ports;
	
	public String getName() {
		return name;
	}
	public void setName(String n) {
		name = n;
	}
	public List getPorts() {
		return ports;
	}
	public void setPorts(List l) {
		ports = l;
	}
} 