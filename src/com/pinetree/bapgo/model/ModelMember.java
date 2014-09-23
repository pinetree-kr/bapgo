package com.pinetree.bapgo.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class ModelMember extends BaseModel{
	protected String memberName;
	
	protected ArrayList<String> phoneNumberList = new ArrayList<String>();
	protected String memberPhoto;

	protected int memberSrl;
	protected String user_id;
	protected long contactId;
	
	public ModelMember setName(String memberName){
		this.memberName = memberName;
		return this;
	}

	public ModelMember setPhoneNumbers(ArrayList<String> phoneNumberList){
		this.phoneNumberList = phoneNumberList;
		return this;
	}
	
	public ModelMember addPhoneNumber(String phoneNumber){
		if(!phoneNumberList.contains(phoneNumber) && phoneNumber.length()>=10)
			phoneNumberList.add(phoneNumber);
		return this;
	}
	
	public ModelMember setMemberSrl(int memberSrl){
		this.memberSrl = memberSrl;
		return this;
	}
	public ModelMember setMemberPhoto(String memberPhoto){
		this.memberPhoto = memberPhoto;
		return this;
	}
	public ModelMember setUserId(String user_id){
		this.user_id = user_id;
		return this;
	}
	public ModelMember setContactId(long contactId){
		this.contactId = contactId;
		return this;
	}
	
	public String getName(){
		return memberName;
	}
	public ArrayList<String> getPhoneNumbers(){
		return phoneNumberList;
	}
	public String getPhoneNumber(int idx){
		return phoneNumberList.get(idx);
	}

	public boolean hasPhoneNumber(String phoneNumber){
		if(phoneNumberList.contains(phoneNumber))
			return true;
		else return false;
	}
	public int getSizeOfPhoneNumbers(){
		return phoneNumberList.size();
	}
	
	public int getMemberSrl(){
		return memberSrl;
	}
	public String getMemberPhoto(){
		return memberPhoto;
	}
	public String getUserId(){
		return user_id;
	}
	public long getContactId(){
		return contactId;
	}
	
	public ModelMember init(){
		super.init();
		memberName = "";
		phoneNumberList.clear();
		memberSrl = 0;
		memberPhoto = "";
		return this;
	}
	
	@Override
	public List<NameValuePair> getParameters() {
		// TODO Auto-generated method stub
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("act", "getBapgoMemberLogIn"));
		parameters.add(new BasicNameValuePair("member_srl", String.valueOf(this.getMemberSrl())));
		parameters.add(new BasicNameValuePair("member_name", this.getName()));
		//parameters.add(new BasicNameValuePair("phone_no", this.getPhoneNo()));
		parameters.add(new BasicNameValuePair("response_type", "JSON"));
		return parameters;
	}
	
	@Override
	public BaseModel getFields(String json) {
		// TODO Auto-generated method stub
		return null;
	}
}
