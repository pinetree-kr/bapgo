package com.pinetree.bapgo;

import com.google.android.gcm.GCMRegistrar;
import com.pinetree.bapgo.util.ActivityUtils;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_splash);
		
		//GCM 사용가능여부 확인
		GCMRegistrar.checkDevice(this);
		//메니페스트 설정 확인
		GCMRegistrar.checkManifest(this);
		
		//등록
		registerId();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	protected void registerId(){
		final String regId = GCMRegistrar.getRegistrationId(this);
				
		// 디바이스 ID를 취득후 미등록된 경우 등록
		if("".equals(regId)){
			GCMRegistrar.register(this, GCMIntentService.PROJECT_ID);
		}
		else
			Log.i("DebugPrint","You already registered");		
	}
	
	protected void unregisterId(){
		if(GCMRegistrar.isRegistered(this))
			GCMRegistrar.unregister(this);
	}
}