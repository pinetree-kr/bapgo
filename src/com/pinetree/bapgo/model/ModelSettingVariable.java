package com.pinetree.bapgo.model;

import java.util.List;

import org.apache.http.NameValuePair;

public class ModelSettingVariable extends BaseModel{
	protected String varName;
	protected boolean isSection = false;
	protected BaseModel model;
	
	public ModelSettingVariable(){}
	public ModelSettingVariable(String varName){
		setVarName(varName);
	}
	public ModelSettingVariable(String varName, boolean isSection){
		setVarName(varName);
		setSection(isSection);
	}
	
	public ModelSettingVariable setVarName(String varName){
		this.varName = varName;
		return this;
	}
	public String getVarName(){
		return varName;
	}
	
	public ModelSettingVariable setSection(boolean isSection){
		this.isSection = isSection;
		return this;
	}
	public boolean isSection(){
		return isSection;
	}
	
	public ModelSettingVariable setModel(BaseModel model){
		this.model = model;
		return this;
	}
	public BaseModel getModel(){
		return model;
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
