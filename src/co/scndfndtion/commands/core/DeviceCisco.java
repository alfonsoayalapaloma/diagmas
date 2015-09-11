package co.scndfndtion.commands.core;

import co.scndfndtion.commands.def.ExeParameter;
import co.scndfndtion.commands.ruby.RubyLocalWrapper;
import co.scndfndtion.commands.ruby.RubyWrapper;

public class DeviceCisco extends Device {
	public String doPing(ExeParameter param){
		String result=null;
		final RubyWrapper rubyWrap = new RubyLocalWrapper();
		result = rubyWrap.executeCommand( param );
		return result;
	}
}
