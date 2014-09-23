package com.pinetree.bapgo.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ModelLogin extends BaseModel{
	protected String user_id, password;
	protected int member_srl;
	
	public ModelLogin setUserId(String user_id){
		this.user_id = user_id;
		return this;
	}
	public ModelLogin setPassword(String password){
		this.password = password;
		return this;
	}
	public ModelLogin setMemberSrl(int member_srl){
		this.member_srl = member_srl;
		return this;
	}
	
	public String getUserId(){
		return user_id;
	}
	public String getPassword(){
		return password;
	}
	public int getMemberSrl(){
		return member_srl;
	}
	
	public boolean isValid(){
		if(user_id.trim().equals("") || password.trim().equals(""))
			return false;
		else return true;
	}
	
	public ModelLogin init(){
		super.init();
		user_id="";
		password="";
		return this;
	}
	
	@Override
	public List<NameValuePair> getParameters() {
		// TODO Auto-generated method stub
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("act", "procBapgoMemberLogin"));
		parameters.add(new BasicNameValuePair("user_id", this.getUserId()));
		parameters.add(new BasicNameValuePair("password", this.getPassword()));
		parameters.add(new BasicNameValuePair("response_type", "JSON"));
		return parameters;
	}
	@Override
	public BaseModel getFields(String json) {
		// TODO Auto-generated method stub
		return null;
	}
}
