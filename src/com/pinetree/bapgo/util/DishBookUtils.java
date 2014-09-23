package com.pinetree.bapgo.util;

import java.util.ArrayList;
import java.util.Calendar;

import com.pinetree.bapgo.model.ModelDishBook;
import com.pinetree.bapgo.model.ModelDishDate;

public class DishBookUtils {

	public static ArrayList<ModelDishDate> getDishDateListSync(int disherSrl, ModelDishBook itemBook){
		ArrayList<ModelDishDate> dataList = new ArrayList<ModelDishDate>();
		
		Calendar calendar = CalendarUtils.getCalendarByDate(itemBook.getStartDate());
		
		for(int i=0; i<itemBook.getDateRange(); i++){
			ModelDishDate itemDishDate = itemBook.getDishDateByDate(CalendarUtils.getDate(calendar));
			if(itemDishDate==null)
				itemDishDate = new ModelDishDate(disherSrl, calendar);
			dataList.add(itemDishDate);
			calendar.add(Calendar.DATE, 1);
		}
		
		return dataList;
	}
	
	public static ArrayList<ModelDishDate> getSortedDishDateList(int disherSrl, ArrayList<ModelDishDate> dishList, boolean isFirst){
		ArrayList<ModelDishDate> itemList = new ArrayList<ModelDishDate>();
		ModelDishDate item;
		
		//Log.i("DebugPrint","dataSize:"+dishList.size());
		for(int i=0; i<dishList.size(); i++){
			item = dishList.get(i);
			
			if(isFirst || item.getDay()==1){
				item = new ModelDishDate(disherSrl, item.getDate());
				item.setSection(true);
				itemList.add(item);
				item = dishList.get(i);
			}
			
			item.setSection(false);
			itemList.add(item);

			isFirst = false;
		}
		
		return itemList;
	}
}
