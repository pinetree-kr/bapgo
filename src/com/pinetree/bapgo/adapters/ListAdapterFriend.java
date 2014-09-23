package com.pinetree.bapgo.adapters;

import java.util.ArrayList;
import java.util.List;

import com.pinetree.bapgo.MainActivity;
import com.pinetree.bapgo.R;
import com.pinetree.bapgo.SubActivity;
import com.pinetree.bapgo.adapters.ListAdapterDish.DishDialogClickListener;
import com.pinetree.bapgo.model.ModelMember;
import com.pinetree.bapgo.util.ActivityUtils;
import com.pinetree.bapgo.util.PhoneBookUtils;
import com.pinetree.bapgo.views.CustomDialog;
import com.pinetree.bapgo.views.DishMonthViewHolder;
import com.pinetree.bapgo.views.DishViewHolder;
import com.pinetree.bapgo.views.FriendDetailViewHolder;
import com.pinetree.bapgo.views.FriendViewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ListAdapterFriend extends ArrayAdapter<ModelMember>{
	protected LayoutInflater inflater;
	protected ArrayList<ModelMember> dataList;
	
	public ListAdapterFriend(Context context, int resource,
			List<ModelMember> objects) {		
		super(context, resource, objects);
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataList = (ArrayList<ModelMember>)objects;
		//Log.i("DebugPrint","Constructor");
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}
	
	@Override
	public ModelMember getItem(int position) {
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
		ModelMember item = getItem(position);
		
		FriendViewHolder viewHolder;
		
		if(view==null){
			view = inflater.inflate(R.layout.layout_friend_row, null);
			
			viewHolder = new FriendViewHolder();
			viewHolder.buttonDishBook = (Button) view.findViewById(R.id.buttonDishBook);
			viewHolder.textName = (TextView) view.findViewById(R.id.textName);
			viewHolder.textScript = (TextView) view.findViewById(R.id.textScript);
			viewHolder.regView = (ImageView) view.findViewById(R.id.imageRegistered);
			
			view.setTag(viewHolder);
		}else{
			viewHolder = (FriendViewHolder) view.getTag();
		}
		
		viewHolder.textName.setText(item.getName());
		
		if(item.getMemberSrl()>0){
			viewHolder.isRegistered = true;
			viewHolder.regView.setVisibility(ImageView.VISIBLE);
			viewHolder.buttonDishBook.setVisibility(View.VISIBLE);
			viewHolder.buttonDishBook.setTag(new FriendDetailViewHolder(item));
			viewHolder.buttonDishBook.setOnClickListener(new FriendClickListener());
		}else{
			viewHolder.isRegistered = false;
			viewHolder.regView.setVisibility(ImageView.INVISIBLE);
			viewHolder.buttonDishBook.setVisibility(View.INVISIBLE);
		}
		
		
		//Log.i("DebugPrint","phonebook:"+StringUtils.getGlobalPhoneNumber(item.getPhoneNo()));
		
		// 연락처 정리
		//textScript.setText(StringUtils.adjustPhoneNumber(item.getPhoneNo()));
		//textScript.setText(StringUtils.getLocalPhoneNumber(item.getPhoneNumber(0).getPhoneNumber()));
		//textScript.setText(""+item.getSizeOfPhoneNumbers());
		
		view.setOnTouchListener(new FreindTouchListener());
		return view;
	}
	
	
	protected class FreindTouchListener implements View.OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(v.getTag().getClass().equals(FriendViewHolder.class)){
				FriendViewHolder viewHolder  = (FriendViewHolder) v.getTag();
				if(viewHolder.isRegistered)
					return false;
			}
			return true;
		}
	}
	
	protected class FriendClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// Dish를 클릭했을때
			if(v.getTag().getClass().equals(FriendDetailViewHolder.class)){
				FriendDetailViewHolder viewHolder = (FriendDetailViewHolder)v.getTag();
				MainActivity activity = (MainActivity) ActivityUtils.getInstance().getContext();
				
				//Log.i("DebugPrint","member_srl:"+viewHolder.getMember().getMemberSrl());
				
				activity.moveToActivity(SubActivity.class, viewHolder.getMember());
				//((MainActivity)ActivityUtils.getInstance().getContext())
				/*
				String toggle = "";
				if(!((ToggleButton)v).isChecked())
					toggle = "Close?";
				else
					toggle = "Open?";
				AlertDialog dialog = CustomDialog.simpleDialog(toggle, new DishDialogClickListener(v), new DishDialogClickListener(v));
				*/
			}
		}
	}
}
