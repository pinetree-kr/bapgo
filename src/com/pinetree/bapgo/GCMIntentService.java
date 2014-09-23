package com.pinetree.bapgo;

import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;


public class GCMIntentService extends GCMBaseIntentService {
	protected static final String tag = "GCMIntentService";
	protected static final String PROJECT_ID = "142384269144";
	
	public GCMIntentService(){
		this(PROJECT_ID);
	}
	
	public GCMIntentService(String projectId) {
		super(projectId);
	}

	// 에러 발생시
	@Override
	protected void onError(Context context, String errorId) {
		Log.d(tag, "onError. errorId : " + errorId);
	}

	// 푸시받은 메시지
	@Override
	protected void onMessage(Context context, Intent intent) {
		Bundle b = intent.getExtras();
		
		Iterator<String> iterator = b.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String value = b.get(key).toString();
			Log.d(tag,"onMessage. " + key + " : " + value);
		}
	}

	// 등록되었을때
	@Override
	protected void onRegistered(Context context, String regId) {
		Log.d(tag, "onRegistered. regId : " + regId);
	}

	// 등록해지되었을때
	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.d(tag, "onUnregistered. regId : " + regId);
	}

}
