package co.com.scndfndtion.diagmas.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

	public static String get(String jsondata, String key) {
		String result=null;
		
		try {
			JSONObject json = new JSONObject(jsondata);
			result = json.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
