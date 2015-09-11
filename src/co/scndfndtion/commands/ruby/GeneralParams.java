package co.scndfndtion.commands.ruby;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class GeneralParams {
	Map<String,String> requiredParams     = new HashMap<String,String>();
	Hashtable<String,String> params = new Hashtable<String,String>();
	int paramCount                  = 0;
	
	public boolean areParametersOK(){
		Debugger.println("required.params:"+requiredParams);
		Debugger.println("actual.params  :"+params);
		
		boolean result=false;
		if ( params.keySet().containsAll( requiredParams.keySet() ) && areParametersNotNull() ){
			result=true;
		}
		if(result){
			Debugger.println("parameters are OK ");
		}else{
			Debugger.println("parameters ERR ");		
		}
		return result;
	}
	
	private boolean areParametersNotNull() {
		boolean result=true;
		for(String key : requiredParams.keySet()){
			if(  params.get(key)==null || "".equals(params.get(key))  ){
				result=false;
			}
		}
		
		return result;
	}

	public void addParam(String key, String value){
		if(key!=null && value!=null){
			params.put(key, value);
			paramCount = params.size();
		}
	}
	
	public void addRequiredParam(String param){
		requiredParams.put(param,"OK");
	}
	
	public Map<String,String> getRequiredParams() {
		return requiredParams;
	}
	public void setRequiredParams(Map<String,String> requiredParams) {
		this.requiredParams = requiredParams;
	}
	public Hashtable<String, String> getParams() {
		return params;
	}
	public void setParams(Hashtable<String, String> params) {
		this.params = params;
	}
	public int getParamCount() {
		return paramCount;
	}
	public void setParamCount(int paramCount) {
		this.paramCount = paramCount;
	}
	public String getParamValue(String key) {
		return params.get(key);
	}
	public String printRequired(){
		return requiredParams.toString();
	}
	public String printParams(){
		return params.toString();
	}
}
