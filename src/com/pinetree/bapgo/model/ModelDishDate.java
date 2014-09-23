package com.pinetree.bapgo.model;

import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;

import com.pinetree.bapgo.util.CalendarUtils;
import com.pinetree.bapgo.util.PhoneBookUtils;

public class ModelDishDate extends BaseModel{
	protected int disherSrl = 0;
	protected String date = "";
	protected int year, month, day, dayOfWeek;
	protected boolean isSection = false;
	
	protected ModelDishPeriod period1, period2, period3;

	public ModelDishDate(int disherSrl){
		setDisherSrl(disherSrl);
		Calendar calendar = Calendar.getInstance();
		setCalendar(calendar);
	}
	public ModelDishDate(int disherSrl, Calendar calendar){
		setDisherSrl(disherSrl);
		setCalendar(calendar);
	}
	
	public ModelDishDate(int disherSrl, String date){
		setDisherSrl(disherSrl);
		setCalendar(CalendarUtils.getCalendarByDate(date));
	}
	
	public ModelDishDate setCalendar(Calendar calendar){
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH)+1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		date = CalendarUtils.getDate(calendar);
		period1 = new ModelDishPeriod(getDisherSrl(), date+"1");
		period2 = new ModelDishPeriod(getDisherSrl(), date+"2");
		period3 = new ModelDishPeriod(getDisherSrl(), date+"3");
		return this;
	}
	
	public ModelDishDate setDisherSrl(int disherSrl){
		this.disherSrl = disherSrl;
		return this;
	}
	
	public int getDisherSrl(){
		return disherSrl;
	}
	
	public ModelDishDate setSection(boolean toggle){
		isSection = toggle;
		return this;
	}
	
	public boolean isSection(){
		return isSection;
	}
	
	public String getDate(){
		return date;
	}
	
	public ModelDishDate setYear(int year){
		this.year = year;
		return this;
	}
	public ModelDishDate setMonth(int month){
		this.month = month;
		return this;
	}
	public ModelDishDate setDay(int day){
		this.day = day;
		return this;
	}
	public ModelDishDate setDayOfWeek(int dayOfWeek){
		this.dayOfWeek = dayOfWeek;
		return this;
	}
	
	public int getYear(){
		return year;
	}
	public int getMonth(){
		return month;
	}
	public int getDay(){
		return day;
	}
	public int getDayOfWeek(){
		return dayOfWeek;
	}
	
	
	public ModelDishDate setPeriod1(ModelDishPeriod period1){
		this.period1 = period1;
		return this;
	}
	public ModelDishDate setPeriod2(ModelDishPeriod period2){
		this.period2 = period2;
		return this;
	}
	public ModelDishDate setPeriod3(ModelDishPeriod period3){
		this.period3 = period3;
		return this;
	}
	
	public ModelDishPeriod getPeriod1(){
		return period1;
	}
	public ModelDishPeriod getPeriod2(){
		return period2;
	}
	public ModelDishPeriod getPeriod3(){
		return period3;
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
