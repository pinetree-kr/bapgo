package com.pinetree.bapgo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.pinetree.bapgo.model.BaseModel;
import com.pinetree.bapgo.model.ModelServerState;

import android.util.Log;

public class HttpPostRequest{
	protected static HttpURLConnection connection;
	
	public static BaseModel httpPostRequest(BaseModel objInfo){
		
		UrlEncodedFormEntity entity;
		List<NameValuePair> parameters = objInfo.getParameters();	
		Log.i("DebugPrint","Request:"+parameters.toString());
		
		try {
			connection = (HttpURLConnection) new URL(ModelServerState.getInstance().getUrl()).openConnection();
			
			System.setProperty("http.keepAlive", "true");
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			/*
			// for JDK 1.5 or Higher
			if(objInfo.getClass().equals(BusStateModel.class)){
				connection.setConnectTimeout(2000);
				connection.setReadTimeout(2000);
			}else{
				connection.setConnectTimeout(8000);
				connection.setReadTimeout(8000);				
			}
			 */
			
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);				
			
			if(Cookie.getInstance().checkSession() && !objInfo.getClass().equals(ModelServerState.class)){
				Log.i("DebugPrint","Update Cookie:"+Cookie.getInstance().getCookies());
				// set cookie on request
				connection.setRequestProperty("cookie", Cookie.getInstance().getCookies());
			}
			
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			
			entity = new UrlEncodedFormEntity(parameters,HTTP.UTF_8);
			
			streamWriter.write(EntityUtils.toString(entity));
			streamWriter.flush();
			
			// Get Response Code
			objInfo.setResponseCode(connection.getResponseCode());
			
			// Check Session and Expired
			if(!Cookie.getInstance().checkSession() && !objInfo.getClass().equals(ModelServerState.class)){
				// Get Cookies by Header
				Map<String, List<String>> iMap = connection.getHeaderFields();
				if(iMap.containsKey("Set-Cookie")){
					List<String> lString = iMap.get("Set-Cookie");
					String newCookies = "";
					for(int i = 0; i<lString.size(); i++)
						newCookies += lString.get(i);
					
					// new cookie
					Cookie.getInstance()
						.setCookies(newCookies, System.currentTimeMillis());
					
					Log.i("DebugPrint","New Cookies:"+Cookie.getInstance().getCookies());
					
				}
			}
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			
			String lineBuffer = null;
			String response = "";
			
			while((lineBuffer = bufferedReader.readLine())!=null){
				response += lineBuffer;
			}
			
			Log.i("DebugPrint","response:"+response);
			objInfo.getFields("["+response+"]");
			
			streamWriter.close();
			bufferedReader.close();
			
			connection.disconnect();
			
			//objInfo.setFields(response);
			return objInfo;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			objInfo.setError(-1);
			objInfo.setMessage("MalformedURLException\n" + e.getMessage());
			return objInfo;
		} catch (IOException e) {
			e.printStackTrace();
			objInfo.setError(-1);
			objInfo.setMessage("IOException\n" + "네트워크 연결이 실패했습니다\n네트워크 상태를 확인후 다시 시도해주세요");
			
			return objInfo;
		}
	}
}
