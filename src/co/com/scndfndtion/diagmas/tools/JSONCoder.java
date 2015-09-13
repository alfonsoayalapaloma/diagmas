/**
 * 
 */
package co.com.scndfndtion.diagmas.tools;

/**
 * @author Alfonso
 *
 */
public class JSONCoder {
	public static String code(String serviceID, String gsr, String ifGsr,
			String swt, String ifSwt, String ipCpe) {
		String result=null;
		if(serviceID!=null){
			result="{"+
				    "\"id\": \""+ serviceID  +"\","+
				    "\"gsrIp\": \""+gsr + "\","+
				    "\"ifGsr\": \""+ifGsr + "\","+
				    "\"swtIp\": \""+swt +"\","+
				    "\"ifSwt\": \""+ ifSwt +"\","+
				    "\"cpeIp\": \""+ ipCpe+ "\""+  
				"}";
		}
		return result;
	}

	public static String code(String objType, String field1, String field2, String field3) {
		String result=null;
		if("device".equals(objType)){
			result="{"+
				    "\"id\": \""+ field1  +"\","+
				    "\"deviceType\": \""+field2 +"\""+  
				"}";
		}else{
			//"probe", "GSRDOWN",        "GSR", "CISCO"
			if("probe".equals(objType)){
				result="{"+
					    "\"id\": \""+      field1 +"\","+
					    "\"location\": \""+field2 +"\","+
					    "\"brand\": \""+   field3 +"\""+
					"}";	
				
			}
			
			
		}
		return result;
	}
}
