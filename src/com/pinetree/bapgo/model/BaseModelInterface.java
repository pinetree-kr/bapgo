package com.pinetree.bapgo.model;

import java.util.List;

import org.apache.http.NameValuePair;


public interface BaseModelInterface {
	
	public List<NameValuePair> getParameters();
	public BaseModel setResponseCode(int code);
	public int getResponseCode();
	
	public BaseModel getFields(String jSon);
}
