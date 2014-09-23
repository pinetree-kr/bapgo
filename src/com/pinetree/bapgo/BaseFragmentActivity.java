package com.pinetree.bapgo;

import java.util.Calendar;

import com.pinetree.bapgo.model.BaseModel;
import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.model.ModelDishBook;
import com.pinetree.bapgo.model.ModelLogout;
import com.pinetree.bapgo.model.ModelMember;
import com.pinetree.bapgo.model.ModelPhoneBook;
import com.pinetree.bapgo.util.ActivityUtils;
import com.pinetree.bapgo.util.Cookie;
import com.pinetree.bapgo.views.FragmentDish;
import com.pinetree.bapgo.views.FragmentFriend;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class BaseFragmentActivity extends FragmentActivity{
	
	@Override
	protected void onResume(){
		super.onResume();
		
		getWindow().setWindowAnimations(android.R.style.Animation_InputMethod);
		
		ActivityUtils.getInstance()
			.setContext(this);
	}
	
	@Override
	protected void onRestart(){
		super.onRestart();
		//Log.i("DebugPrint","onRestart");
		ActivityUtils.getInstance()
		.setContext(this);
		loadBapgoPref();
	}
	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.i("DebugPrint","configChanged");
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
	}
	
	public void loadBapgoPref(){
		// 로그인 세션 체크
		if(!Cookie.getInstance().checkSession()){
			// 재로그인
			((ApplicationClass)getApplicationContext()).setUserInfoByCookie();
			
			if(ModelDeviceLogin.getInstance().getMemberSrl()==0){
				ModelDeviceLogin.getInstance().init();
				moveToActivity(LoginActivity.class);
			}else{
				ActivityUtils.getInstance().sendRequest(ModelDeviceLogin.getInstance());
			}
		}
		/*
		// 연락처 로드
		((ApplicationClass)getApplicationContext()).loadMyPhoneBook();
		
		// 자동 로그인
		if(((ApplicationClass)getApplicationContext()).getUserRegistered()){
			((ApplicationClass)getApplicationContext()).setUserInfoByCookie();
		}
		
		if(ModelDeviceLogin.getInstance().getMemberSrl()==0){
			ModelDeviceLogin.getInstance().init();
			moveToActivity(LoginActivity.class);
		}
		*/
	}
	
	public void onPostResponse(BaseModel objModel){
		
		if(objModel.getClass()==ModelDeviceLogin.class){
			if(objModel.isError()){
				ModelDeviceLogin.getInstance().init();
				moveToActivity(LoginActivity.class);
				return;
			}else{
				// 로그인 성공시의 쿠키 업데이트
				((ApplicationClass)getApplicationContext()).cookieUpdate();
				((ApplicationClass)getApplicationContext()).cookieRegister();
			}
		}
		else if(objModel.getClass()==ModelLogout.class){
			if(!objModel.isError()){
				((ApplicationClass)getApplicationContext()).cookieClear();
	
				ModelDeviceLogin.getInstance().init();
				moveToActivity(LoginActivity.class);
				return;
			}
		}
	}
	
	public void moveToActivity(Class activityClass){
		if(activityClass == null)
			return;
		
		Intent intentActivity = new Intent(this, activityClass);
		startActivity(intentActivity);
		this.finish();
	}
	
	public void moveToActivity(Class activityClass, BaseModel objModel){
		if(activityClass == null || objModel == null)
			return;
		
		//Log.i("DebugPrint","member_srl:"+((ModelMember)objModel).getMemberSrl());
		
		Intent intentActivity = new Intent(this, activityClass);
		intentActivity.putExtra("ObjModel", objModel);
		startActivity(intentActivity);
		//this.finish();
	}
	
	public void fragmentInit(int disherSrl){
		FragmentDish.getInstance(disherSrl).init();
		FragmentFriend.getInstance().init();
	}
	
	protected void onPreSyncDishBook(){
		// 스케쥴 동기화
		ModelDishBook dishBook = new ModelDishBook();
		
		dishBook
		.setDisherSrl(ModelDeviceLogin.getInstance().getMemberSrl())
		.setStartEndDate(Calendar.getInstance(), 14)
		.setFirst(true);
		
		ActivityUtils.getInstance()
		.sendRequest(dishBook);
	}
	
	protected void onPostSyncDishBook(ModelDishBook itemDishBook){
		((ApplicationClass)getApplicationContext()).setSyncedDishBok(itemDishBook);
	}
}
