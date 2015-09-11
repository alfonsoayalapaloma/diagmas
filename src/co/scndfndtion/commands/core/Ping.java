package co.scndfndtion.commands.core;

import co.scndfndtion.commands.def.ExeParameter;


public class Ping implements CommandInterface {
	Device targetObject;
	public Ping(Device targetObject) {
		super();
		this.targetObject = targetObject;
	}
	@Override
	public String execute(ExeParameter param) {
		return targetObject.doPing( param );
	}
	@Override
	public String parse(String input) {
		// TODO Auto-generated method stub
		return null;
	}
}
