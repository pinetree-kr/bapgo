package com.pinetree.bapgo.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ModelServerState extends BaseModel{
	
	protected String url = "http://cra16.handong.edu/pinetree/index.php";
	
	protected static ModelServerState serverStateModel = new ModelServerState();
	
	protected ModelServerState(){}

	public static ModelServerState getInstance(){
		return serverStateModel;
	}
	
	public ModelServerState setUrl(String newUrl){
		url = newUrl;
		return this;
	}
	
	public String getUrl(){
		return url;
	}

	public List<NameValuePair> getParameters() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("act", "getBapgoServerState"));
		parameters.add(new BasicNameValuePair("response_type", "JSON"));
		return parameters;
	}

	public BaseModel getFields(String json) {
		try {
			JSONArray jaResponse = new JSONArray(json);
			for(int i=0; i<jaResponse.length(); i++){
				JSONObject response = jaResponse.getJSONObject(i);
				
				error = response.getInt("error");
				message = response.getString("message");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("DebugPrint",e.getMessage());
		}
		return null;
	}
}
