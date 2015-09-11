package co.scndfndtion.commands.ruby;

import co.scndfndtion.commands.def.ExeParameter;

public abstract class RubyWrapper extends CScript {

	public String executeCommand(ExeParameter parameterObject){		
		setUp();
		return execInOS( createRubyCommand(parameterObject) );
	}

	String createRubyCommand(ExeParameter parameterObject) {
		return getParamValue("scriptName") + " "+
				parameterObject.command ;
	}
	
	public String execInOS(String rubyCommand)
	{
		String fullCommand = generateRemoteCommand(rubyCommand);
		Debugger.println( fullCommand );
		return OSShell.exec(fullCommand);
	}

	protected String generateRemoteCommand(String rubyCommand) {
		return getParaContainer().getParamValue("programPath")+" "
					+ getParaContainer().getParamValue("workingPath")+"/"+rubyCommand;
	}

	public void setUp() {
		super.setUp();	
		if(OSValidator.getOS().equals(CBrand.LINUX)){
			getParaContainer().addParam("workingPath", "/home/aayala/ruby");
			getParaContainer().addParam("programPath", "/usr/bin/ruby");
		}else{
			getParaContainer().addParam("workingPath", "/home/aayala/ruby");
			getParaContainer().addParam("programPath", "/Ruby22-x64/bin/ruby");
		}
	}
	
}
