package com.pinetree.bapgo.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;
import com.pinetree.bapgo.util.ActivityUtils;

import android.util.Log;

public class ModelDeviceLogin extends ModelLogin{
	protected String member_name;
	
	protected boolean isRegistered;
	
	protected static ModelDeviceLogin modelDeviceLogin;
	
	protected ModelDeviceLogin(){}
	
	public static ModelDeviceLogin getInstance(){
		if(modelDeviceLogin==null)
			modelDeviceLogin = new ModelDeviceLogin();
		return modelDeviceLogin;
	}
	
	public ModelDeviceLogin setMemberName(String member_name){
		this.member_name = member_name;
		return this;
	}
	
	public String getMemberName(){
		return member_name;
	}
	
	public ModelDeviceLogin setRegistered(boolean isRegistered){
		this.isRegistered = isRegistered;
		return this;
	}
	public boolean getIsRegistered(){
		return isRegistered;
	}
	
	@Override
	public List<NameValuePair> getParameters() {
		// TODO Auto-generated method stub
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("act", "procBapgoMemberLogin"));
		parameters.add(new BasicNameValuePair("user_id", this.getUserId()));
		parameters.add(new BasicNameValuePair("password", this.getPassword()));
		parameters.add(new BasicNameValuePair("device_id", GCMRegistrar.getRegistrationId(ActivityUtils.getInstance().getContext())));
		parameters.add(new BasicNameValuePair("response_type", "JSON"));
		return parameters;
	}
	
	@Override
	public BaseModel getFields(String json) {
		// TODO Auto-generated method stub
		try {
			JSONArray loginJSON = new JSONArray(json);
			for(int i=0; i<loginJSON.length(); i++){
				JSONObject loginResponse = loginJSON.getJSONObject(i);
				
				error = loginResponse.getInt("error");
				message = loginResponse.getString("message");
				
				// 로그인 실패
				if(error!=0)
					return null;
				
				int is_registered = loginResponse.getInt("is_registered");
				
				/** 등록된 단말기 확인 **/
				if(is_registered>0){
					this.setRegistered(true)
					.setMemberName(loginResponse.getString("member_name"))
					.setMemberSrl(loginResponse.getInt("member_srl"));
				}else{
					this.setRegistered(false)
					.setMemberSrl(loginResponse.getInt("member_srl"));
				}
				
				return this;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("DebugPrint",e.getMessage());
		}
		return null;
	}
}
