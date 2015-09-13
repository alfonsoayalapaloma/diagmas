package co.scndfndtion.diagmas.probes;

import co.scndfndtion.commands.core.Client;

public class CmdGate {
	
	public static String run(String probeType, String serviceId) {
		String result=null;
		
		result = Client.run( probeType, serviceId, null, null);
		
		return result;
	}

}
