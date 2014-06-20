package mooklabs.auth;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class MojangAuth {

	private static String server = "https://authserver.mojang.com";

	public static Map<String, String> authenticate(String username, String password) throws Exception{
		Map<String, String> result = new HashMap<String, String>();
		String request = "{\"agent\": {\"name\": \"Minecraft\",\"version\": 1 },\"username\": \"" + username + "\",\"password\": \"" + password + "\"}";
		String response = NetAPI.sendPost(server + "/authenticate", request);

		JSONObject json = new JSONObject(response);

		result.put("accessToken", json.getString("accessToken"));
		result.put("clientToken", json.getString("clientToken"));
		result.put("UUID", json.getJSONArray("availableProfiles").getJSONObject(0).getString("id"));
		result.put("username", json.getJSONArray("availableProfiles").getJSONObject(0).getString("name"));
		result.put("loginName", username);

		return result;
	}

	public static boolean isValid(String accessToken){
		try{
			String request = "{\"accessToken\": \"" + accessToken + "\"}";
			String response = NetAPI.sendPost(server + "/validate", request);
			if(response == null || response.trim().length() == 0)
				return true;
		}catch(Exception e){}
		return false;
	}

}
