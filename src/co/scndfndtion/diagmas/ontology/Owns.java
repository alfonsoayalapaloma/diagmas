package co.scndfndtion.diagmas.ontology;

import jade.content.Predicate;
import jade.core.AID;
public class Owns implements Predicate{
	private AID owner;
	private Incident item;
	public AID getOwner() {
		return owner;
	}
	public void setOwner(AID id) {
		owner = id;
	}
	public Incident getItem() {
		return item;
	}
	public void setItem(Incident i) {
		item = i;
	}
} 
