package com.pinetree.bapgo.adapters;

import java.util.ArrayList;
import java.util.List;

import com.pinetree.bapgo.R;
import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.model.ModelLogout;
import com.pinetree.bapgo.model.ModelSettingVariable;
import com.pinetree.bapgo.model.ModelVersion;
import com.pinetree.bapgo.util.ActivityUtils;
import com.pinetree.bapgo.util.Cookie;
import com.pinetree.bapgo.views.CustomDialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListAdapterSetting extends ArrayAdapter<ModelSettingVariable>{
	protected LayoutInflater inflater;
	protected ArrayList<ModelSettingVariable> dataList;
	
	public ListAdapterSetting(Context context, int resource,
			List<ModelSettingVariable> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataList = (ArrayList<ModelSettingVariable>)objects;
		//Log.i("DebugPrint","Constructor");
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}
	
	@Override
	public ModelSettingVariable getItem(int position) {
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
		// TODO Auto-generated method stub
		//Log.i("DebugPrint","viewIn");
		
		View view = convertView;
		if(view!=null)
			return view;

		ModelSettingVariable item = getItem(position);
		
		
		if(item.isSection()){
			view = inflater.inflate(R.layout.layout_setting_header, null);
			TextView textSettingGroup = (TextView) view.findViewById(R.id.textSettingGroup);
			textSettingGroup.setText(item.getVarName());
			view.setTag(new SettingSection());
			view.setOnTouchListener(new SettingTouchListener());
		}else{
			view = inflater.inflate(R.layout.layout_setting_row, null);
			TextView textVariable = (TextView) view.findViewById(R.id.textVariable);
			textVariable.setText(item.getVarName());
			
			if(item.getModel()!=null){
				TextView textDescription = (TextView) view.findViewById(R.id.textDescription);
				
				String description = "";
				if(item.getModel().getClass()==ModelDeviceLogin.class){
					description = ((ModelDeviceLogin)item.getModel()).getUserId();
					view.setTag(new SettingLogin());
					view.setOnClickListener(new SettingClickListener());
				}
				if(item.getModel().getClass()==ModelVersion.class){
					description = ((ModelVersion)item.getModel()).getCurrentVersion();					
				}
				
				textDescription.setText(description);
				//LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) textDescription.getLayoutParams();
				textDescription.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
				textDescription.setLayoutParams(textDescription.getLayoutParams());
			}
			
		}

		return view;
	}
	
	protected class SettingTouchListener implements View.OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(v.getTag().getClass()==SettingSection.class)
				return true;
			return false;
		}
	}
	
	protected class SettingClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getTag().getClass().equals(SettingLogin.class)){
				AlertDialog dialog = CustomDialog.simpleDialog("Logout", new SettingDialogClickListener(), new SettingDialogClickListener(), true);
			}
		}
	}
	
	protected class SettingDialogClickListener implements DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				dialog.dismiss();
				if(Cookie.getInstance().checkSession()){
					ActivityUtils.getInstance().sendRequest(new ModelLogout());
				}else{
					ActivityUtils.getInstance().onPostResponse(new ModelLogout().setError(0));
				}
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.dismiss();
				break;
			default:
				break;
			}
		}
	}
	
	protected class SettingSection{}
	protected class DisabledSettingVariable{}
	protected class SettingLogin{}
	
}
