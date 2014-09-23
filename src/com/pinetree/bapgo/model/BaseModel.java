package com.pinetree.bapgo.model;

import java.io.Serializable;

public abstract class BaseModel implements BaseModelInterface, Serializable{
	protected String message;
	protected int error = -1;
	protected String jSonData;
	
	protected transient int responseCode;
	
	public boolean isError(){
		if(error!=0)
			return true;
		else return false;
	}
	
	public BaseModel setError(int isError){
		error = isError;
		return this;
	}
	
	public BaseModel setMessage(String getMessage){
		message = getMessage;
		return this;
	}
	
	public String getMessage(){
		return message;
	}
	
	public BaseModel setResponseCode(int code){
		responseCode = code;
		return this;
	}
	
	public int getResponseCode(){
		return responseCode;
	}
	
	public void setJsonData(String jSon){
		this.jSonData = jSon;
	}
	public String getJsonData(){
		return jSonData;
	}
	
	public BaseModel init(){
		message = "";
		error = -1;
		return this;
	}
}
