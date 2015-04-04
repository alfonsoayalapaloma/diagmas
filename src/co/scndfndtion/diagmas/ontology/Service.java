package co.scndfndtion.diagmas.ontology;

import jade.util.leap.List;
public class Service extends Incident { // Note that the Item class (omitted here)
	// implements Concept
	private String name;
	private List tracks;
	public String getName() {
		return name;
	}
	public void setName(String n) {
		name = n;
	}
	public List getTracks() {
		return tracks;
	}
	public void setTracks(List l) {
		tracks = l;
	}
} 