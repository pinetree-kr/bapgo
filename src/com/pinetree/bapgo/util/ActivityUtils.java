package com.pinetree.bapgo.util;

import com.google.android.gcm.GCMRegistrar;
import com.pinetree.bapgo.BaseActivity;
import com.pinetree.bapgo.BaseFragmentActivity;
import com.pinetree.bapgo.MainActivity;
import com.pinetree.bapgo.model.BaseModel;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

public class ActivityUtils {
	protected Context context;
	protected ProgressDialog progressDialog; 
	protected int rotation = -1;
	
	protected static ActivityUtils activityUtils;
	
	protected ActivityUtils(){}
	
	public static ActivityUtils getInstance(){
		if(activityUtils == null)
			activityUtils = new ActivityUtils();
		return activityUtils;
	}
	
	public ActivityUtils setContext(Context context){
		this.context = context;
		return this;
	}
	public Context getContext(){
		return context;
	}
	
	public ActivityUtils setRotation(int rotation){
		this.rotation = rotation;
		return this;
	}
	
	public int getRotation(){
		return rotation;
	}
	
	public void sendRequest(BaseModel objModel){
		if(getContext()==null){
			Log.i("DebugPrint","sendReqest: need to init context");
			return ;
		}
		HttpAsyncTask request = new HttpAsyncTask();
		request.execute(objModel);
	}
	
	public void onPostResponse(BaseModel objModel){
		if(getContext()==null){
			Log.i("DebugPrint","onPostResponse: need to init context");
			return ;
		}
		
		if(getContext().getClass().getSuperclass()==BaseFragmentActivity.class){
			BaseFragmentActivity activity = (BaseFragmentActivity) getContext();
			activity.onPostResponse(objModel);
		}
		else if(getContext().getClass().getSuperclass()==BaseActivity.class){
			BaseActivity activity = (BaseActivity) getContext();
			activity.onPostResponse(objModel);
		}
	}
	
	public void showDialog(String title, String message){
		if(getContext()==null)
			return ;
		
		progressDialog = ProgressDialog.show(context, title, message, true);
	}
	
	public void dismissDialog(){
		if(getContext()==null)
			return ;
		
		if(progressDialog!=null)
			progressDialog.dismiss();
	}
	
	public boolean isDialogShowing(){
		if(progressDialog==null)
			return false;
		return true;
	}

}
