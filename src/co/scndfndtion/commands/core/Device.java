package co.scndfndtion.commands.core;

import co.scndfndtion.commands.def.ExeParameter;
import co.scndfndtion.commands.ruby.Debugger;
import co.scndfndtion.commands.ruby.RubyLocalWrapper;
import co.scndfndtion.commands.ruby.RubyWrapper;

public class Device {
	public String doPing(ExeParameter param){
		String result=null;
		final RubyWrapper rubyWrap = new RubyLocalWrapper();
		Debugger.println(param.toString());
		result = rubyWrap.executeCommand( param );
		return result;
	}
}
