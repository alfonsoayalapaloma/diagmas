package co.scndfndtion.commands.core;

import co.scndfndtion.commands.def.ExeParameter;

public class Invoker {
	private CommandInterface cmd;	
	public void setCmd(CommandInterface cmd)
	{
		this.cmd=cmd;
	}
	public String exe(ExeParameter param){
		return cmd.execute(param);
	}
}
