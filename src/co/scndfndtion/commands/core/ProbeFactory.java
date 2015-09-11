package co.scndfndtion.commands.core;

import co.scndfndtion.commands.def.ExeParameter;

public class ProbeFactory {

	public static Probe create(PROBETYPE probeType) {
		
		Probe probeResult =null;
		if(probeType.equals(PROBETYPE.PING)){
			DeviceCisco cisco = new DeviceCisco();
			CommandInterface cmd = new Ping(cisco);
			probeResult = new Probe(cisco, cmd);
		}
		return probeResult;
	}

}
