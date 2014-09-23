package com.pinetree.bapgo.adapters;

import java.util.ArrayList;
import java.util.List;

import com.pinetree.bapgo.R;
import com.pinetree.bapgo.adapters.ListAdapterSetting.SettingDialogClickListener;
import com.pinetree.bapgo.adapters.ListAdapterSetting.SettingLogin;
import com.pinetree.bapgo.adapters.ListAdapterSetting.SettingSection;
import com.pinetree.bapgo.model.ModelDishPeriod;
import com.pinetree.bapgo.model.ModelDishDate;
import com.pinetree.bapgo.model.ModelLogout;
import com.pinetree.bapgo.util.ActivityUtils;
import com.pinetree.bapgo.util.CalendarUtils;
import com.pinetree.bapgo.util.Cookie;
import com.pinetree.bapgo.util.PhoneBookUtils;
import com.pinetree.bapgo.views.CustomDialog;
import com.pinetree.bapgo.views.DishDateViewHolder;
import com.pinetree.bapgo.views.DishMonthViewHolder;
import com.pinetree.bapgo.views.DishViewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ListAdapterDish extends ArrayAdapter<ModelDishDate>{
	protected LayoutInflater inflater;
	protected ArrayList<ModelDishDate> dataList;
	
	public ListAdapterDish(Context context, int resource,
			List<ModelDishDate> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataList = (ArrayList<ModelDishDate>)objects;
		//Log.i("DebugPrint","Constructor");
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}
	
	@Override
	public ModelDishDate getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ModelDishDate item = getItem(position);
		
		if(item.isSection()){
			DishMonthViewHolder viewHolder;
			
			if(view==null || !view.getTag().getClass().equals(DishMonthViewHolder.class)){
				view = inflater.inflate(R.layout.layout_schedule_header, null);
				
				viewHolder = new DishMonthViewHolder();
				
				viewHolder.textMonth = (TextView) view.findViewById(R.id.textMonth);
				viewHolder.textMonthNum = (TextView) view.findViewById(R.id.textMonthNum);
				
				view.setTag(viewHolder);
			}else{
				viewHolder = (DishMonthViewHolder) view.getTag();
			}
			
			viewHolder.textMonth.setText(CalendarUtils.getMonth(item.getMonth()));
			viewHolder.textMonthNum.setText(String.valueOf(item.getMonth()));
		}else if(!item.isSection()){
			DishDateViewHolder viewHolder;
			
			if(view==null || !view.getTag().getClass().equals(DishDateViewHolder.class)){
				view = inflater.inflate(R.layout.layout_schedule_row, null);
				
				viewHolder = new DishDateViewHolder();
				
				viewHolder.textDate = (TextView) view.findViewById(R.id.textDate);
				viewHolder.textWeek = (TextView) view.findViewById(R.id.textWeek);
				
				viewHolder.buttonBreakFirst = (ToggleButton) view.findViewById(R.id.buttonBreakFirst);
				viewHolder.buttonLunch = (ToggleButton) view.findViewById(R.id.buttonLunch);
				viewHolder.buttonDinner = (ToggleButton) view.findViewById(R.id.buttonDinner);
				
				view.setTag(viewHolder);
			}else{
				viewHolder = (DishDateViewHolder) view.getTag();
			}
			
			viewHolder.textDate.setText(String.valueOf(item.getDay()));
			viewHolder.textWeek.setText(CalendarUtils.getDayOfWeek(item.getDayOfWeek()));
			
			viewHolder.buttonBreakFirst.setTag(new DishViewHolder(item.getPeriod1()));
			viewHolder.buttonLunch.setTag(new DishViewHolder(item.getPeriod2()));
			viewHolder.buttonDinner.setTag(new DishViewHolder(item.getPeriod3()));

			viewHolder.buttonBreakFirst.setChecked(item.getPeriod1().isOpenDish());
			viewHolder.buttonLunch.setChecked(item.getPeriod2().isOpenDish());
			viewHolder.buttonDinner.setChecked(item.getPeriod3().isOpenDish());
			
			DishClickListener clickListener = new DishClickListener();
			
			viewHolder.buttonBreakFirst.setOnClickListener(clickListener);
			viewHolder.buttonLunch.setOnClickListener(clickListener);
			viewHolder.buttonDinner.setOnClickListener(clickListener);
			
		}
		DishDateTouchListener touchListener = new DishDateTouchListener();
		view.setOnTouchListener(touchListener);
		
		return view;
	}
	
	protected class DishDateTouchListener implements View.OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(v.getTag().getClass().equals(DishMonthViewHolder.class))
				return true;
			return false;
		}
	}
	
	protected class DishClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// Dish를 클릭했을때
			if(v.getTag().getClass().equals(DishViewHolder.class)){
				String toggle = "";
				if(!((ToggleButton)v).isChecked())
					toggle = "Close?";
				else
					toggle = "Open?";
				AlertDialog dialog = CustomDialog.simpleDialog(toggle, new DishDialogClickListener(v), new DishDialogClickListener(v), false);
			}
		}
	}
	
	protected class DishDialogClickListener implements DialogInterface.OnClickListener{
		View v;
		ToggleButton buttonView;
		DishViewHolder viewHolder;
		
		public DishDialogClickListener(View v){
			this.v = v;
			buttonView = (ToggleButton)v;
			viewHolder = (DishViewHolder)v.getTag();
		}
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				dialog.dismiss();
				viewHolder.getDishPeriod().setOpenCloseDish(buttonView.isChecked());
				ActivityUtils.getInstance()
				.sendRequest(viewHolder.getDishPeriod());
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.dismiss();
				buttonView.setChecked(!buttonView.isChecked());
				break;
			default:
				buttonView.setChecked(!buttonView.isChecked());
				break;
			}
		}
	}
}
