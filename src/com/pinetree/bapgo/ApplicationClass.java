package com.pinetree.bapgo;

import java.util.ArrayList;

import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.model.ModelDishBook;
import com.pinetree.bapgo.model.ModelDishDate;
import com.pinetree.bapgo.model.ModelMember;
import com.pinetree.bapgo.model.ModelPhoneBook;
import com.pinetree.bapgo.util.ActivityUtils;
import com.pinetree.bapgo.util.Cookie;
import com.pinetree.bapgo.util.PhoneBookUtils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

public class ApplicationClass extends Application{
	protected final String PACKAGE_NAME = "bapgo_settings";
	protected SharedPreferences sharedPref;
	
	//protected ArrayList<ModelMember> memberList;
	//protected ArrayList<ModelDishDate> dishDateList;
	
	protected ModelPhoneBook phoneBook;
	protected ModelDishBook dishBook;
	
	// 액티비티, 리시버, 서비스가 시작되기 전에 실행
	@Override
	public void onCreate(){
		super.onCreate();
		sharedPref = getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}
	
	public boolean getUserRegistered(){
		return sharedPref.getBoolean("is_registered", false);
	}
	
	public ModelPhoneBook getMyLocalPhoneBook(){
		return new ModelPhoneBook(PhoneBookUtils.getPhoneBook(getApplicationContext()));
	}
	
	public void setSyncedPhoneBook(ModelPhoneBook itemPhoneBook){
		phoneBook = itemPhoneBook;
	}
	
	public ModelPhoneBook getSyncedPhoneBook(){
		if(phoneBook==null)
			phoneBook = getMyLocalPhoneBook();
		return phoneBook;
	}
	
	public void initMyLocalPhoneBook(){
		phoneBook = null;
	}
	
	public void setSyncedDishBok(ModelDishBook itemDishBook){
		dishBook = itemDishBook;
	}
	
	public ModelDishBook getSyncedDishBook(){
		if(dishBook==null)
			dishBook = new ModelDishBook();
		return dishBook;
	}
	
	public void initDishBook(){
		dishBook = null;
	}
	
	public void setUserInfoByCookie(){
		// Cookie
		String session_key = sharedPref.getString("session_key", "");
		long session_time = sharedPref.getLong("session_time", 0);
		Cookie.getInstance().setCookies(session_key, session_time);

		// 로그인 정보
		String user_id = sharedPref.getString("user_id", "");
		String password = sharedPref.getString("user_pw", "");
		String member_name = sharedPref.getString("member_name", "");
					
		ModelDeviceLogin.getInstance().init();
		ModelDeviceLogin.getInstance()
		.setMemberName(member_name)
		.setUserId(user_id)
		.setPassword(password);
	}
	
	public void cookieUpdate(){
		SharedPreferences.Editor sharedEditor = sharedPref.edit();
		
		sharedEditor.putString("session_key", Cookie.getInstance().getCookies());
		sharedEditor.putLong("session_time", Cookie.getInstance().getSessionTime());
		sharedEditor.commit();
	}
	
	public void cookieRegister(){
		SharedPreferences.Editor sharedEditor = sharedPref.edit();
		
		sharedEditor.putString("user_id", ModelDeviceLogin.getInstance().getUserId());
		sharedEditor.putString("user_pw", ModelDeviceLogin.getInstance().getPassword());
		sharedEditor.putInt("member_srl", ModelDeviceLogin.getInstance().getMemberSrl());
		sharedEditor.putString("member_name", ModelDeviceLogin.getInstance().getMemberName());
		sharedEditor.putBoolean("is_registered", true);
		sharedEditor.commit();
	}
	
	public void cookieClear(){
		SharedPreferences.Editor sharedEditor = sharedPref.edit();
		
		sharedEditor.clear();
		sharedEditor.commit();
	}
}
