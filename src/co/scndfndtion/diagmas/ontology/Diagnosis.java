package co.scndfndtion.diagmas.ontology;

import java.util.List;

public class Diagnosis {
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
	private String name;
	 private List tests; 

}
