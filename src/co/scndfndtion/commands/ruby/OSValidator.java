package co.scndfndtion.commands.ruby;



public class OSValidator {
	private static String operativeSystem = System.getProperty("os.name").toLowerCase();
	
	public static CBrand getOS(){
		Debugger.println("operativeSystem: "+operativeSystem);
		CBrand result=CBrand.LINUX;
		if(operativeSystem.indexOf("win") >= 0){
			result=CBrand.WINDOWS;
		}else{
			if( operativeSystem.indexOf("nix") >= 0 || operativeSystem.indexOf("nux") >= 0 || operativeSystem.indexOf("aix") > 0 ){
				result=CBrand.LINUX;
			}
		}
		return result;
	}
}
