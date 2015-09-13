package co.scndfndtion.commands.core;

import java.util.ArrayList;
import java.util.List;

import co.scndfndtion.commands.def.ExeParameter;

/**
 * Runs a command
 * @author alfonso.ayala
 *
 */
public class Client {
	/**
	 * Client for running commands
	 * @param probeType 
	 * @param serviceId
	 * @param ip 
	 * @param cmd
	 * @return
	 */
	public static String run(String probeType, String serviceId, String ip, String cmd){
		String result="";
		String deviceIp="216.58.219.110"; //"127.0.0.1";
		String strCmd = deviceIp;
	
		List<Probe> testList = new ArrayList<Probe>();

		testList.add( ProbeFactory.create(PROBETYPE.PING) );
		testList.add( ProbeFactory.create(PROBETYPE.IPCONFIG));

		final ExeParameter param= new ExeParameter("aayala", "XXXXX", strCmd, deviceIp, "", "/tmp", "ssh", "", "", "", "", "");

		for(Probe probe : testList){
			if(probe!=null ){
				//Debugger.println("probe:"+probe.getCmd());
				result += probe.run(param)+ "\n------------------------------\n";
			}
		}	
		//Debugger.println("result:{"+result+"}");
		return result;
	}
}
