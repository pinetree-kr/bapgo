package com.pinetree.bapgo.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ModelPokePeriod extends ModelDishPeriod{

	public ModelPokePeriod(int disherSrl, String datePeriod) {
		super(disherSrl, datePeriod);
	}
	
	
	
	@Override
	public List<NameValuePair> getParameters() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("act", "procBapgoPokingDish"));
		parameters.add(new BasicNameValuePair("member_srl", String.valueOf(ModelDeviceLogin.getInstance().getMemberSrl())));
		parameters.add(new BasicNameValuePair("disher_srl", String.valueOf(this.getDisherSrl())));
		parameters.add(new BasicNameValuePair("date_period", this.getDatePeriod()));
		//parameters.add(new BasicNameValuePair("is_open", this.isOpenDishForString()));
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
