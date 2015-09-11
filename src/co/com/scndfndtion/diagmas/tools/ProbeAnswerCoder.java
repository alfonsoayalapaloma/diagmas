/**
 * 
 */
package co.com.scndfndtion.diagmas.tools;

/**
 * @author Alfonso
 *
 */
public class ProbeAnswerCoder {
	public static String code(String ip, String deviceType, String output) {
		String result=null;
		if(output!=null){
			result="{"+
				    "\"ip\": \""+ ip  +"\","+
				    "\"deviceType\": \""+ deviceType + "\","+
				    "\"output\": \""+ output + "\""+
				 "}";
		}
		return result;
	}
}
