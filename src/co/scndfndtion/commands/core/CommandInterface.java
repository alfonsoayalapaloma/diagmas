package co.scndfndtion.commands.core;

import co.scndfndtion.commands.def.ExeParameter;

public interface CommandInterface {
	public String execute(ExeParameter param);
	public String parse(String input);
}
