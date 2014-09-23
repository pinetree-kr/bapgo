package com.pinetree.bapgo.model;

import java.util.List;

import org.apache.http.NameValuePair;

public class ModelPhoneNumber extends BaseModel{
	protected long _id;
	protected String phoneNumber;
	
	public ModelPhoneNumber setId(Long _id){
		this._id = _id;
		return this;
	}
	public ModelPhoneNumber setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
		return this;
	}
	
	public long getId(){
		return _id;
	}
	public String getPhoneNumber(){
		return phoneNumber;
	}
	@Override
	public List<NameValuePair> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BaseModel getFields(String json) {
		// TODO Auto-generated method stub
		return null;
	}
}
