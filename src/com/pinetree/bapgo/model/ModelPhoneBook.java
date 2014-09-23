package com.pinetree.bapgo.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.pinetree.bapgo.util.PhoneBookUtils;

public class ModelPhoneBook extends BaseModel{
	protected ArrayList<ModelMember> dataList;// = new ArrayList<ModelMember>();
	protected int indexOfMember = 0;
	
	public ModelPhoneBook(){
		dataList = new ArrayList<ModelMember>();
	}
	
	public ModelPhoneBook(ArrayList<ModelMember> dataList){
		setList(dataList);
	}
	
	public ModelPhoneBook setList(ArrayList<ModelMember> dataList){
		this.dataList = dataList;
		return this;
	}
	public ArrayList<ModelMember> getList(){
		return dataList;
	}
	
	public ModelMember getMemberByPhoneNumber(String phoneNumber){
		ModelMember modelMember = null;
		for(int i=0; i<dataList.size(); i++){
			modelMember = dataList.get(i);
			if(modelMember.hasPhoneNumber(phoneNumber))
				return modelMember;
		}
		return null;
	}

	public int getMemberIdxByPhoneNumber(String phoneNumber){
		ModelMember modelMember = null;
		for(int i=0; i<dataList.size(); i++){
			modelMember = dataList.get(i);
			if(modelMember.hasPhoneNumber(phoneNumber))
				return i;
		}
		return -1;
	}
	
	public int getSizeOfMember(){
		return dataList.size();
	}
	public boolean hasNextMember(){
		if(getSizeOfMember()>indexOfMember)
			return true;
		else
			return false;
	}
	public ModelMember nextMember(){
		return dataList.get(indexOfMember++);
	}
	
	public String getJSONDataList(){
		String jSonDataList = "";
		if(this.hasNextMember()){
			while(this.hasNextMember()){
				ModelMember itemMember = this.nextMember();
				
				for(int i=0; i< itemMember.getPhoneNumbers().size(); i++){
					jSonDataList += "'" + PhoneBookUtils.getGlobalPhoneNumber(itemMember.getPhoneNumber(i)) + "'";
					jSonDataList += ",";
				}
			}
			jSonDataList = jSonDataList.substring(0, jSonDataList.length()-1);
		}
		return jSonDataList;
	}
	
	@Override
	public List<NameValuePair> getParameters() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("act", "procBapgoPhoneBookSync"));
		parameters.add(new BasicNameValuePair("member_srl", String.valueOf(ModelDeviceLogin.getInstance().getMemberSrl())));
		parameters.add(new BasicNameValuePair("phone_list", this.getJSONDataList()));
		parameters.add(new BasicNameValuePair("response_type", "JSON"));
		return parameters;
	}

	@Override
	public BaseModel getFields(String jSon) {
		try {
			JSONArray jSonArray = new JSONArray(jSon);
			for(int i=0; i<jSonArray.length(); i++){
				JSONObject response = jSonArray.getJSONObject(i);
				
				error = response.getInt("error");
				message = response.getString("message");
				if(error!=0)
					break;
				
				int friend_count =  response.getInt("friend_count");
				String friends = response.getString("friends");
				
				if(friend_count<2)
					friends = "["+friends+"]";
				
				JSONArray jSonFriendArray = new JSONArray(friends);
				
				for(int j=0; j<jSonFriendArray.length(); j++){
					JSONObject objFriend = jSonFriendArray.getJSONObject(j);
					
					String phoneNumber = objFriend.getString("phone_no");
					int memberSrl = objFriend.getInt("member_srl");
					
					ModelMember modelMember = this.getMemberByPhoneNumber(phoneNumber);
					
					if(modelMember!=null){
						modelMember.setMemberSrl(memberSrl);
					}
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("DebugPrint",e.getMessage());
		}
		return this;
	}

}
