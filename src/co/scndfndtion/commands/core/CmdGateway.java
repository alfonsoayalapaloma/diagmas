package co.scndfndtion.commands.core;


public class CmdGateway {

	public static String run(final String command, final String serviceId) {
		String result=null;
		String ip=null;
		String cmd=null;
		
        		
		
		

		result = Client.run(command, serviceId, ip, cmd);
		
		return result;
	}

}
