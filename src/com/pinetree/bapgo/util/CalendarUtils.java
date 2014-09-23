package com.pinetree.bapgo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
	public static Calendar getCalendarByDate(String date){
		if(date==null || "".equals(date))
			return null;
		
		int year, month, day;
		year = Integer.parseInt(date.substring(0,4));
		month = Integer.parseInt(date.substring(4,6));
		day = Integer.parseInt(date.substring(6,8));
		
		Calendar calendar = (Calendar) Calendar.getInstance().clone();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		
		return calendar;
	}
	
	public static String getDate(Calendar calendar){
		int year, month, day, dayOfWeek;
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH)+1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		//dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		return String.format("%04d%02d%02d", year, month, day); 
	}
	
	public static String getDate(Calendar calendar, int n){
		Calendar date = (Calendar) calendar.clone();
		date.add(Calendar.DATE, n);
		return getDate(date);
	}
	
	public static long diffOfDate(String start, String end){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		
		Date startDate = null, endDate = null;
		try {
			startDate = formatter.parse(start);
			endDate = formatter.parse(end);
			
			long diff = endDate.getTime() - startDate.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			return diffDays;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public static String getMonth(int month){
		switch(month){
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		}
		return "";
	}
	
	public static String getDayOfWeek(int dayOfWeek){
		String week = "";
		switch(dayOfWeek){
		case 1:
			week = "Monday";
			break;
		case 2:
			week = "Tuesday";
			break;
		case 3:
			week = "Wednesday";
			break;
		case 4:
			week = "Thursday";
			break;
		case 5:
			week = "Friday";
			break;
		case 6:
			week = "Saturday";
			break;
		case 7:
			week = "Sunday";
			break;
		}
		
		return week;
	}
}
