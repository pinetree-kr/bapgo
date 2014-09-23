
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

public class ModelUserRegister extends BaseModel{
	protected String memberName;
	protected String phoneNo;
	protected String memberPhoto;
	protected int memberSrl;
	
	public ModelUserRegister setName(String memberName){
		this.memberName = memberName;
		return this;
	}
	public ModelUserRegister setPhoneNo(String phoneNo){
		this.phoneNo = phoneNo;
		return this;
	}
	public ModelUserRegister setMemberSrl(int memberSrl){
		this.memberSrl = memberSrl;
		return this;
	}
	public ModelUserRegister setMemberPhoto(String memberPhoto){
		this.memberPhoto = memberPhoto;
		return this;
	}
	
	public String getName(){
		return memberName;
	}
	public String getPhoneNo(){
		return phoneNo;
	}
	public int getMemberSrl(){
		return memberSrl;
	}
	public String getMemberPhoto(){
		return memberPhoto;
	}	
	
	public boolean isValid(){
		if(this.getName().trim().equals("") || this.getPhoneNo() == null || "".equals(this.getPhoneNo().trim()))
			return false;
		else return true;
	}
	
	public ModelUserRegister init(){
		super.init();
		//isRegistered = false;
		return this;
	}
	
	@Override
	public List<NameValuePair> getParameters() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("act", "procBapgoMemberRegister"));
		parameters.add(new BasicNameValuePair("member_srl", String.valueOf(this.getMemberSrl())));
		parameters.add(new BasicNameValuePair("member_name", this.getName()));
		parameters.add(new BasicNameValuePair("phone_no", this.getPhoneNo()));
		parameters.add(new BasicNameValuePair("device_id", GCMRegistrar.getRegistrationId(ActivityUtils.getInstance().getContext())));
		parameters.add(new BasicNameValuePair("response_type", "JSON"));
		return parameters;
	}
	
	@Override
	public BaseModel getFields(String json) {
		// TODO Auto-generated method stub
		try {
			JSONArray jaResponse = new JSONArray(json);
			for(int i=0; i<jaResponse.length(); i++){
				JSONObject response = jaResponse.getJSONObject(i);
				
				error = response.getInt("error");
				message = response.getString("message");
				if(error!=0)
					break;
				
				ModelDeviceLogin.getInstance().setMemberName(response.getString("member_name"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("DebugPrint",e.getMessage());
		}
		return null;
	}
}
