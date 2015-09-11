package co.scndfndtion.commands.ruby;

import co.scndfndtion.commands.def.ExeParameter;



/**
 * Receiver (command pattern)
 * Script ejecutable en el servidor
 * @author alfonso.ayala
 *
 */
public abstract class CScript {
	String programPath="";
	String workingPath="";
	GeneralParams paraContainer= new GeneralParams();
	public    abstract String executeCommand(ExeParameter parameterObject);
	protected abstract String generateRemoteCommand(String command);
	protected abstract String parse(String rawOutput);
	
	public boolean areParametersOK(){
		boolean result=false;
		if(getParaContainer()!=null){
			result=getParaContainer().areParametersOK();
		}
		return result;
	}
	
	public void addRequiredParam(String value){
		if(getParaContainer()!=null){
			getParaContainer().addRequiredParam(value);
		}
	}
	public void addParam(String key, String value){
		if(getParaContainer()!=null){
			getParaContainer().addParam(key, value);
			getParaContainer().getRequiredParams().put(key,"OK");
		}
	}
	
	public String getParamValue(String key){
		String result=null;
		if(getParaContainer()!=null){
			result=getParaContainer().getParamValue(key);
		}
		return result;
	}
	
	public void setUp(){};
		//getParaContainer().setRequiredParams(  new ArrayList<String>( Arrays.asList("workingPath", "programPath"))  );
		//getParaContainer().setRequiredParams(  new ArrayList<String>( Arrays.asList())  );

	
	public String getProgramPath() {
		return programPath;
	}
	public void setProgramPath(String programPath) {
		this.programPath = programPath;
	}
	public String getWorkingPath() {
		return workingPath;
	}
	public void setWorkingPath(String workingPath) {
		this.workingPath = workingPath;
	}
	public GeneralParams getParaContainer() {
		return paraContainer;
	}
	public void setParaContainer(GeneralParams paraContainer) {
		this.paraContainer = paraContainer;
	}
}
