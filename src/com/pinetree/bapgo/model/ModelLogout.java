package com.pinetree.bapgo.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ModelLogout extends BaseModel{

	@Override
	public List<NameValuePair> getParameters() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("act", "procBapgoMemberLogout"));
		parameters.add(new BasicNameValuePair("response_type", "JSON"));
		return parameters;
	}

	@Override
	public BaseModel getFields(String json) {
		try {
			JSONArray logoutJSON = new JSONArray(json);
			for(int i=0; i<logoutJSON.length(); i++){
				JSONObject response = logoutJSON.getJSONObject(i);
				
				error = response.getInt("error");
				message = response.getString("message");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("DebugPrint",e.getMessage());
		}
		return null;
	}
}
