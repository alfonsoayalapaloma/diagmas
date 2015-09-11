package co.scndfndtion.diagmas.probes;

public class Probe {
	public Probe(String name, String command, String patternOK,
			String patternFAIL, String msgOK, String msgFAIL, String comment) {
		super();
		this.name = name;
		this.command = command;
		this.patternOK = patternOK;
		this.patternFAIL = patternFAIL;
		this.msgOK = msgOK;
		this.msgFAIL = msgFAIL;
		this.comment = comment;
	}

	String name;
	String command;
	String answer;
	
	String patternOK;
	String patternFAIL;
	
	String msgOK;
	String msgFAIL;
	
	String comment;

}
