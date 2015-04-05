package co.scndfndtion.diagmas.ontology;

import java.util.List;

public class Diagnosis {
	private String name;
	private String recommendation;
	private List tests; 	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List getTests() {
		return tests;
	}
	public void setTests(List tests) {
		this.tests = tests;
	}
}
