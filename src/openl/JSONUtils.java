package openl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtils {
	
	public static String joinStringFromInputStream(InputStream is, String lineSeparator){
		String s = "";
		
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while(br.ready()){
				s += br.readLine() + lineSeparator;
			}
		}catch(Exception e){}
		return s;
	}
	
	public static JSONObject getJSONObjectFromInputStream(InputStream is){
		return new JSONObject(joinStringFromInputStream(is, ""));
	}
	
	public static JSONArray getJSONArrayFromInputStream(InputStream is){
		return new JSONArray(joinStringFromInputStream(is, ""));
	}

}
