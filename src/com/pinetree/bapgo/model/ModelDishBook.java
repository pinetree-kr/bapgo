package com.pinetree.bapgo.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.pinetree.bapgo.util.CalendarUtils;
import com.pinetree.bapgo.util.PhoneBookUtils;

public class ModelDishBook extends BaseModel{
	protected ArrayList<ModelDishDate> dataList = new ArrayList<ModelDishDate>();
	protected ArrayList<String> dateList = new ArrayList<String>();
	
	protected String startDate = "", endDate = "";
	protected int disherSrl = 0;
	
	protected boolean isFirst = false;
	
	public ModelDishBook setFirst(boolean flag){
		isFirst = flag;
		return this;
	}
	
	public boolean isFirst(){
		return isFirst;
	}
	
	public ModelDishBook setList(ArrayList<ModelDishDate> dataList){
		this.dataList = dataList;
		return this;
	}
	
	public ModelDishBook addList(ArrayList<ModelDishDate> dataList){
		this.dataList.addAll(dataList);
		return this;
	}
	
	public ArrayList<ModelDishDate> getList(){
		return dataList;
	}
	
	public void addDishDate(ModelDishDate item){
		dataList.add(item);
		dateList.add(item.getDate());
	}
	
	public void setDishPeriodByPeriod(ModelDishDate itemDishDate, ModelDishPeriod itemDishPeriod, int period){
		ModelDishPeriod dishPeriod;
		
		switch(period){
		case 1:
			dishPeriod = itemDishDate.getPeriod1();
			break;
		case 2:
			dishPeriod = itemDishDate.getPeriod2();
			break;
		case 3:
			dishPeriod = itemDishDate.getPeriod3();
			break;
		default:
			dishPeriod = null;
			break;
		}
		
		if(dishPeriod==null)
			return;
		
		dishPeriod
		.setDisherSrl(itemDishPeriod.getDisherSrl())
		.setDatePeriod(itemDishPeriod.getDatePeriod())
		.setCount(itemDishPeriod.getCount())
		.setOpenCloseDish(itemDishPeriod.isOpenDish());
	}
	
	public int getDishDateIdx(ModelDishDate item){
		for(int i=0; i<dataList.size(); i++){
			if(dataList.get(i).getDate().equals(item.getDate()))
				return i;
		}
		return -1;
	}
	
	public ModelDishDate getDishDateByDate(String date){
		if(dateList.contains(date))
			return dataList.get(dateList.indexOf(date));
		else
			return null;
	}
	
	public int getDishDateIdxByDate(String date){
		if(dateList.contains(date))
			return dateList.indexOf(date);
		else
			return -1;
	}
	
	public ModelDishDate getDishDateByPeriod(String period){
		for(int i=0; i<dataList.size(); i++){
			if(dataList.get(i).getDate().equals(period.substring(0, 8)))
				return dataList.get(i);
		}
		return null;
	}
	
	public int getDishDateIdxByPeriod(String period){
		for(int i=0; i<dataList.size(); i++){
			if(dataList.get(i).getDate().equals(period.substring(0, 8)))
				return i;
		}
		return -1;
	}

	public int getSizeOfDishDate(){
		return dataList.size();
	}
	
	public ModelDishBook setStartDate(Calendar calendar){
		this.startDate = CalendarUtils.getDate(calendar);
		if("".equals(endDate))
			this.endDate = CalendarUtils.getDate(calendar);
		return this;
	}
	
	public ModelDishBook setEndDate(Calendar calendar){
		if("".equals(startDate))
			startDate = CalendarUtils.getDate(calendar);
		this.endDate = CalendarUtils.getDate(calendar);
		return this;
	}
	
	public ModelDishBook setStartEndDate(Calendar startCalendar, Calendar endCalendar){
		this.startDate = CalendarUtils.getDate(startCalendar);
		this.endDate = CalendarUtils.getDate(endCalendar);
		return this;
	}
	
	public ModelDishBook setStartEndDate(Calendar calendar, int day){
		this.startDate = CalendarUtils.getDate(calendar);
		this.endDate = CalendarUtils.getDate(calendar, day);
		return this;
	}
	
	public String getStartDate(){
		return startDate;
	}
	
	public String getEndDate(){
		return endDate;
	}
	
	public ModelDishBook setDisherSrl(int disherSrl){
		this.disherSrl = disherSrl;
		return this;
	}
	
	public int getDisherSrl(){
		return disherSrl;
	}
	
	public int getDateRange(){
		if("".equals(startDate) || "".equals(endDate))
			return 0;
		
		return (int)CalendarUtils.diffOfDate(startDate, endDate);
	}
	
	@Override
	public List<NameValuePair> getParameters() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("act", "getBapgoDishBook"));
		parameters.add(new BasicNameValuePair("member_srl", String.valueOf(ModelDeviceLogin.getInstance().getMemberSrl())));
		parameters.add(new BasicNameValuePair("disher_srl", String.valueOf(this.getDisherSrl())));
		parameters.add(new BasicNameValuePair("start_date", this.getStartDate()));
		parameters.add(new BasicNameValuePair("end_date", this.getEndDate()));
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

				int dish_count =  response.getInt("dish_count");
				int disher_srl =  response.getInt("disher_srl");
				String dishes = response.getString("dishes");
				
				if(dish_count<2)
					dishes = "["+dishes+"]";
				
				JSONArray jSonDishArray = new JSONArray(dishes);
				for(int j=0; j<jSonDishArray.length(); j++){
					JSONObject objDish = jSonDishArray.getJSONObject(j);
					
					//int disherSrl = objDish.getInt("disher_srl");
					String period = objDish.getString("date_period");
					int count = objDish.getInt("count");
					//boolean is_open = objDish.getBoolean("is_open");
					/*
					 * 각 period에 맞는 date 추가
					 */
					ModelDishDate itemDishDate = this.getDishDateByPeriod(period);
					
					if(itemDishDate==null){
						itemDishDate = new ModelDishDate(getDisherSrl(), period.substring(0, 8));
						addDishDate(itemDishDate);
					}
					
					int periodType = Integer.parseInt(period.substring(8));
					ModelDishPeriod itemDishPeriod = new ModelDishPeriod(getDisherSrl(), period);
					itemDishPeriod
					.setDisherSrl(disherSrl)
					.setDatePeriod(period)
					.setCount(count)
					.setOpenCloseDish(true);

					this.setDishPeriodByPeriod(itemDishDate, itemDishPeriod, periodType);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("DebugPrint",e.getMessage());
		}
		return this;
	}
}
