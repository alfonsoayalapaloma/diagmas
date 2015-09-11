package co.scndfndtion.commands.core;

import co.scndfndtion.commands.def.ExeParameter;

public class Probe {

	public CommandInterface getCmd() {
		return cmd;
	}

	public void setCmd(CommandInterface cmd) {
		this.cmd = cmd;
	}

	public DeviceCisco getDevice() {
		return device;
	}

	public void setDevice(DeviceCisco device) {
		this.device = device;
	}

	private CommandInterface cmd;
	private DeviceCisco device;

	public Probe(DeviceCisco cisco, CommandInterface cmd) {
		this.cmd=cmd;
		this.device=cisco;
	}

	public String run(ExeParameter param) {
		Invoker invoker = new Invoker();
		invoker.setCmd(cmd);
		//final ExeParameter param= new ExeParameter("aayala", "FullFillment2013*", strCmd, deviceIp, "", "/tmp", "ssh", "", "", "", "", "");
		return invoker.exe(param);
	}

}
