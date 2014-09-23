package com.pinetree.bapgo.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ModelDishPeriod extends BaseModel{
	protected String datePeriod = "";
	protected int disherSrl = 0;
	protected int count = 0;
	protected boolean isOpen = false;
	
	public ModelDishPeriod(int disherSrl, String datePeriod){
		setDisherSrl(disherSrl);
		setDatePeriod(datePeriod);
	}
	
	public ModelDishPeriod setDatePeriod(String datePeriod){
		this.datePeriod = datePeriod;
		return this;
	}

	public ModelDishPeriod setDisherSrl(int disherSrl){
		this.disherSrl = disherSrl;
		return this;
	}
	
	public ModelDishPeriod setCount(int count){
		this.count = count;
		return this;
	}
		
	public String getDatePeriod(){
		return datePeriod;
	}
	
	public int getDisherSrl(){
		return disherSrl;
	}
	
	public int getCount(){
		return count;
	}

	public ModelDishPeriod setOpenCloseDish(boolean isOpen){
		this.isOpen = isOpen;
		return this;
	}
	
	public boolean isOpenDish(){
		return isOpen;
	}
	
	public String isOpenDishForString(){
		if(isOpen)
			return "Y";
		else
			return "N";
	}
	
	@Override
	public List<NameValuePair> getParameters() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("act", "procBapgoDishOpenClose"));
		parameters.add(new BasicNameValuePair("disher_srl", String.valueOf(this.getDisherSrl())));
		parameters.add(new BasicNameValuePair("date_period", this.getDatePeriod()));
		parameters.add(new BasicNameValuePair("is_open", this.isOpenDishForString()));
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
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("DebugPrint",e.getMessage());
		}
		return this;
	}
}
