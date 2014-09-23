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
		
		//GCM ��밡�ɿ��� Ȯ��
		GCMRegistrar.checkDevice(this);
		//�޴��佺Ʈ ���� Ȯ��
		GCMRegistrar.checkManifest(this);
		
		//���
		registerId();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	protected void registerId(){
		final String regId = GCMRegistrar.getRegistrationId(this);
				
		// ����̽� ID�� ����� �̵�ϵ� ��� ���
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