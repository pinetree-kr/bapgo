package com.pinetree.bapgo;

import java.util.Calendar;

import com.pinetree.bapgo.model.BaseModel;
import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.model.ModelDishBook;
import com.pinetree.bapgo.model.ModelLogin;
import com.pinetree.bapgo.model.ModelPhoneBook;
import com.pinetree.bapgo.model.ModelUserRegister;
import com.pinetree.bapgo.util.ActivityUtils;
import com.pinetree.bapgo.util.Cookie;
import com.pinetree.bapgo.util.PhoneBookUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public abstract class BaseActivity extends Activity {
	@Override
	protected void onResume(){
		super.onResume();
		
		getWindow().setWindowAnimations(android.R.style.Animation_InputMethod);
		//overridePendingTransition(0,0);
		
		ActivityUtils.getInstance()
			.setContext(this);
		
		// �α��ΰ� ����� ����� �ƴҶ��� �׻� �α��� ������ �ҷ��´�
		if(this.getClass()!=RegisterActivity.class && this.getClass()!=LoginActivity.class)
			loadBapgoPref();
		
		/*
		// ������ȯ�� ������
		WindowManager wm = this.getWindowManager();
		if(ActivityUtils.getInstance().getRotation() == -1 || ActivityUtils.getInstance().getRotation() == wm.getDefaultDisplay().getRotation()){
			ActivityUtils.getInstance().setRotation(wm.getDefaultDisplay().getRotation());
		}
		
		ActivityUtils.getInstance().setRotation(wm.getDefaultDisplay().getRotation());
		*/
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
	}
	
	@Override
	protected void onDestroy(){
		if(ActivityUtils.getInstance().isDialogShowing())
			ActivityUtils.getInstance().dismissDialog();		
		super.onDestroy();
	}
	
	public void onPostResponse(BaseModel objModel){
		Class thisActivityClass = this.getClass();
		
		// splash activity
		if(thisActivityClass.equals(SplashActivity.class)){
			Class targetClass = LoginActivity.class;
			
			if(!objModel.isError()){
				// �α�������
				if(objModel.getClass().equals(ModelDeviceLogin.class)){
					((ApplicationClass)getApplicationContext()).cookieUpdate();
					
					if(((ModelDeviceLogin)objModel).getIsRegistered()){
						// �������� �������� ��ũ
						onPreSyncPhoneBook();
						return ;
					}else{
						targetClass = RegisterActivity.class;
					}
				}else if(objModel.getClass().equals(ModelPhoneBook.class)){
					onPostSyncPhoneBook((ModelPhoneBook)objModel);
					
					targetClass = MainActivity.class;
				}
			}
			
			Handler handler = new Handler();
			handler.postDelayed(new SplashHandler(targetClass), 1000);
		}
		// login activity
		else if(thisActivityClass.equals(LoginActivity.class)){
			if(objModel.isError())
				return;
			
			if(objModel.getClass().equals(ModelDeviceLogin.class)){
				((ApplicationClass)getApplicationContext()).cookieUpdate();
				
				// ���� �����
				if(((ModelDeviceLogin)objModel).getIsRegistered()){
					((ApplicationClass)getApplicationContext()).cookieRegister();
					
					onPreSyncPhoneBook();
					return ;
				}
				// �ű� �����
				else
					moveToActivity(RegisterActivity.class);
			}else if(objModel.getClass().equals(ModelPhoneBook.class)){
				onPostSyncPhoneBook((ModelPhoneBook)objModel);
				moveToActivity(MainActivity.class);
			}
		}
		// register activity
		else if(thisActivityClass==RegisterActivity.class){
			if(objModel.isError())
				return;
			
			if(objModel.getClass().equals(ModelUserRegister.class)){
				((ApplicationClass)getApplicationContext()).cookieUpdate();
				((ApplicationClass)getApplicationContext()).cookieRegister();
				
				onPreSyncPhoneBook();
				return ;
			}else if(objModel.getClass().equals(ModelPhoneBook.class)){
				onPostSyncPhoneBook((ModelPhoneBook)objModel);
				moveToActivity(MainActivity.class);
			}
		}
	}
	
	public void moveToActivity(Class activityClass){
		Intent intentActivity = new Intent(this, activityClass);
		startActivity(intentActivity);
		this.finish();
	}
	
	public void loadBapgoPref(){
		// ����ó �ε�
		//((ApplicationClass)getApplicationContext()).loadMyPhoneBook();
		
		// �ڵ� �α���
		if(((ApplicationClass)getApplicationContext()).getUserRegistered()){
			((ApplicationClass)getApplicationContext()).setUserInfoByCookie();
			
			ActivityUtils.getInstance().sendRequest(ModelDeviceLogin.getInstance());
		}else{
			ModelDeviceLogin.getInstance().init();
			ActivityUtils.getInstance().onPostResponse(ModelDeviceLogin.getInstance());
		}
	}
	
	protected void onPreSyncPhoneBook(){
		ActivityUtils.getInstance()
		.sendRequest(((ApplicationClass)getApplicationContext()).getMyLocalPhoneBook());
	}
	
	protected void onPostSyncPhoneBook(ModelPhoneBook phoneBook){
		((ApplicationClass)getApplicationContext()).setSyncedPhoneBook(phoneBook);
	}
	
	protected class SplashHandler implements Runnable{
		protected Class className;
		
		public SplashHandler(Class className){
			this.className = className;
		}
		public void run() {
			moveToActivity(className);
		}
	}
}
