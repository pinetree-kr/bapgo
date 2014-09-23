package com.pinetree.bapgo.model;

import java.util.List;

import org.apache.http.NameValuePair;

public class ModelVersion extends BaseModel{
	protected String currentVersion = "1.0";
	protected String recentlyVersion = "1.0";
	
	protected static ModelVersion modelVersion;
	
	protected ModelVersion(){}
	
	public static ModelVersion getInstance(){
		if(modelVersion == null)
			modelVersion = new ModelVersion();
		return modelVersion;
	}
	
	public ModelVersion setCurrentVersion(String version){
		this.currentVersion = version;
		return this;
	}
	public String getCurrentVersion(){
		return currentVersion;
	}
	public ModelVersion setRecentlyVersion(String version){
		this.recentlyVersion = version;
		return this;
	}
	public String getRecentlyVersion(){
		return recentlyVersion;
	}
	
	@Override
	public List<NameValuePair> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseModel getFields(String json) {
		// TODO Auto-generated method stub
		return null;
	}

}
