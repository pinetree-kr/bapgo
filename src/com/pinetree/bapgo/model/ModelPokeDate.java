package com.pinetree.bapgo.model;

import java.util.Calendar;

import com.pinetree.bapgo.util.CalendarUtils;

public class ModelPokeDate extends ModelDishDate{

	public ModelPokeDate(int disherSrl) {
		super(disherSrl);
	}

	public ModelPokeDate(int disherSrl, Calendar calendar){
		super(disherSrl, calendar);
	}
	
	public ModelPokeDate(int disherSrl, String date){
		super(disherSrl, date);
	}
	
	public ModelPokeDate setCalendar(Calendar calendar){
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH)+1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		date = CalendarUtils.getDate(calendar);
		period1 = new ModelPokePeriod(getDisherSrl(), date+"1");
		period2 = new ModelPokePeriod(getDisherSrl(), date+"2");
		period3 = new ModelPokePeriod(getDisherSrl(), date+"3");
		return this;
	}
	
}
