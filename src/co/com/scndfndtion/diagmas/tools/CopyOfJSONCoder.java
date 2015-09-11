/**
 * 
 */
package co.com.scndfndtion.diagmas.tools;

/**
 * @author Alfonso
 *
 */
public class CopyOfJSONCoder {
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
}
