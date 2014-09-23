/*
 * class		: HttpPostRequest
 * author		: ?��???
 * email		: vanadate.kr@gmail.com
 * description	: HTTP protocol???�해 ?�으�?Asyncronous Task�?POST방식?�로 Request
 */

package com.pinetree.bapgo.util;

import com.pinetree.bapgo.LoginActivity;
import com.pinetree.bapgo.MainActivity;
import com.pinetree.bapgo.RegisterActivity;
import com.pinetree.bapgo.SplashActivity;
import com.pinetree.bapgo.model.BaseModel;
import com.pinetree.bapgo.model.ModelDishPeriod;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class HttpAsyncTask extends AsyncTask<BaseModel, BaseModel, BaseModel>{
	protected Dialog dialog;
	
	@Override
	protected BaseModel doInBackground(BaseModel... objInfo) {

		if(isCancelled())
			return null;
		
		BaseModel objModel = HttpPostRequest.httpPostRequest(objInfo[0]);
		
		// UI쪽에 response 처리
		publishProgress(objModel);
		return objModel;
	}
	
	@Override
	protected void onCancelled(){
		Log.i("DebugTest","AsyncTask Cancelled");
		super.onCancelled();
	}
	
	@Override
	protected void onPreExecute() {
		
		ActivityUtils activityUtils = ActivityUtils.getInstance(); 
		Class thisActivityClass = activityUtils.getContext().getClass();
		
		/*
		if(thisActivityClass==LoginActivity.class){
			activityUtils.showDialog("", "로그인중입니다");
		}
		else if(thisActivityClass==RegisterActivity.class){
			activityUtils.showDialog("", "유저 정보를 전송합니다");
		}
		else if(thisActivityClass==MainActivity.class){
			activityUtils.showDialog("", "로딩중입니다");
		}
		*/
		if(thisActivityClass!=SplashActivity.class)
			activityUtils.showDialog("", "잠시만 기다려주세요...");
		
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(BaseModel objModel) {
		ActivityUtils activityUtils = ActivityUtils.getInstance();
		
		activityUtils.dismissDialog();
		
		ActivityUtils.getInstance().onPostResponse(objModel);
		super.onPostExecute(objModel);
	}

	// UI 처리
	@Override
	protected void onProgressUpdate(BaseModel... objInfo){
		
		if(objInfo[0].isError()){
			Toast.makeText(ActivityUtils.getInstance().getContext(), objInfo[0].getMessage(), Toast.LENGTH_SHORT).show();
		}
		else if(objInfo.getClass().equals(ModelDishPeriod.class)){
			Toast.makeText(ActivityUtils.getInstance().getContext(), objInfo[0].getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
}
