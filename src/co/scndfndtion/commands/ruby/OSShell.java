package co.scndfndtion.commands.ruby;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class OSShell{
	public static String exec(final String args)
	{
		String sbuff = "";
		try
		{
			Debugger.println("osshell: args="+args);
			final Process prcs = Runtime.getRuntime().exec(args);
			
			final BufferedReader stdIn = new BufferedReader(new InputStreamReader(prcs.getInputStream()));
			String line = null;
			while ((line = stdIn.readLine()) != null) {  
				sbuff = sbuff+line+"\n";
			}
			prcs.waitFor(); 
			stdIn.close();
			//prcs.getErrorStream();
			
			Debugger.println(sbuff);
			
			String errBuff="";
			final BufferedReader stdErr = new BufferedReader(new InputStreamReader(prcs.getErrorStream()));
			while ((line = stdErr.readLine()) != null) {  
				errBuff = errBuff+line+"\n";
			}
		}                
		catch(Exception e){
			sbuff="ERROR: No se puede ejecutar";
			e.printStackTrace();
		}
		Debugger.println("osshell: result="+sbuff.toString());
		return sbuff;
	}
}