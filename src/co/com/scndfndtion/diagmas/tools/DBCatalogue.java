package co.com.scndfndtion.diagmas.tools;

import java.util.Hashtable;


public class DBCatalogue {
	
	// The catalogue of services (contains service attributes)
	private static Hashtable<String,String> serviceTable = new Hashtable<String,String>();
	// The catalogue of devices 
	private static Hashtable<String,String> deviceTable = new Hashtable<String,String>();
	// The catalogue of probes
	private static Hashtable<String,String> probeTable = new Hashtable<String,String>();
	
	protected static void setup() {
		fillServiceTable();
		fillDeviceTable();
		fillProbeTable();
	}
	
	public static String getService(String serviceId){	
		String result=null;
		if(serviceTable.size()==0){
			fillServiceTable();
		}	
		if(serviceId!=null && serviceTable.containsKey(serviceId)){
			result=serviceTable.get(serviceId);
		}
		System.out.println(serviceId+":"+result);
		return result;
	}
	
	public static String getDevice(String key){
		String result=null;
		if(deviceTable.size()==0){
			fillDeviceTable();
		}	
		if(key!=null && deviceTable.containsKey(key)){
			result=deviceTable.get(key);
		}
		System.out.println( key+":"+result);
		return result;
	}

	
	public static String getProbe(String key){
		String result=null;
		if( probeTable.size()==0 ){
			fillProbeTable();
		}	
		if(key!=null && probeTable.containsKey(key)){
			result = probeTable.get(key);
		}
		System.out.println( key+":"+result);
		return result;
	}
	
	private static void fillServiceTable() {
		serviceTable.put("WIN0002", JSONCoder.code("WIN0002","GFLORA","GigabitEthernet0/1/7.351","hermita2","Ethernet3/0/42","10.175.248.182" ));
	}

	private static void fillDeviceTable() {
		deviceTable.put("GFLORA",         JSONCoder.code("device", "GFLORA",         "GSR", null ));
		deviceTable.put("hermita2",       JSONCoder.code("device", "hermita2",       "SWT", null ));
		deviceTable.put("10.175.248.182", JSONCoder.code("device", "10.175.248.182", "CPE", null ));
		
		deviceTable.put("GTRIARA",        JSONCoder.code("device", "GTRIARA",         "GSR", null ));
		deviceTable.put("MTRIARA",        JSONCoder.code("device", "MTRIARA",         "SWT", null ));
		
		deviceTable.put("10.175.248.120", JSONCoder.code("device", "10.175.248.120",  "CPE", null ));
		
	}
	
	private static void fillProbeTable() {
		probeTable.put("GSRDOWN",        JSONCoder.code("probe", "GSRDOWN",        "GSR", "CISCO" ));
		probeTable.put("SWTDOWN",        JSONCoder.code("probe", "SWTDOWN",        "SWT", "CISCO" ));
		probeTable.put("PORTREGISTERED", JSONCoder.code("probe", "PORTREGISTERED", "SWT", "CISCO" ));
		probeTable.put("LEARNINGMAC",    JSONCoder.code("probe", "LEARNINGMAC",    "SWT", "CISCO" ));
		probeTable.put("WANOK",          JSONCoder.code("probe", "WANOK",          "GSR", "CISCO" ));
		probeTable.put("GETCONF",        JSONCoder.code("probe", "GETCONF",        "CPE", "CISCO" ));
	}

	
}
