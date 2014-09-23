package com.pinetree.bapgo.util;

import android.util.Log;

public class Cookie {
	protected static Cookie cookieModel = new Cookie();
	protected String cookies;
	protected boolean has_session;
	protected final long sessionLimitTime = 1 * 60 * 60 * 1000; //1시간: 서버는 5시간.
	protected long sessionTime;
	
	protected Cookie(){
		cookies = "";
		has_session = false;
		sessionTime = 0;
	}
	
	public static Cookie getInstance(){
		return cookieModel;
	}
	
	public Cookie init(){
		cookieModel = new Cookie();
		return this;
	}
	
	public String getCookies(){
		return cookies;
	}
	
	public long getSessionTime(){
		return sessionTime;
	}
	
	public Cookie setCookies(String newCookies){
		cookies = newCookies;
		//sessionTime = System.currentTimeMillis();
		if(newCookies.equals(""))
			has_session = false;
		else
			has_session = true;
		
		return this;
	}

	public Cookie setCookies(String newCookies, long newSessionTime){
		setCookies(newCookies);
		sessionTime = newSessionTime;
		return this;
	}
	
	public Cookie removeCookies(){
		cookies = "";
		has_session = false;
		sessionTime = 0;
		return this;
	}
	
	// 세션 갱신
	public boolean checkSession(){
		if(!has_session)
			return false;
		//Log.i("DebugPrint",System.currentTimeMillis() + "\n"+ (sessionTime + sessionLimitTime));
		if(System.currentTimeMillis() < sessionTime + sessionLimitTime){
			sessionTime = System.currentTimeMillis();
			return true;
		}else{
			has_session = false;
			return false;
		}
	}
	
	public Cookie hasSession(boolean flag){
		has_session = flag;
		return this;
	}
}
