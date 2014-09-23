package com.pinetree.bapgo.views;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pinetree.bapgo.R;
import com.pinetree.bapgo.adapters.ListAdapterDish;
import com.pinetree.bapgo.adapters.ListAdapterSetting;
import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.model.ModelSettingVariable;
import com.pinetree.bapgo.model.ModelVersion;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FragmentSetting extends ListFragment {
	//protected Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//context = this.getActivity();
		//Log.i("DebugPrint","contextClass"+context.getClass());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(container==null)
			return null;
		
		View view = inflater.inflate(R.layout.layout_listview, container, false);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		
		ArrayList<ModelSettingVariable> itemList = getList();
		if(itemList.size()>0){
			ListAdapterSetting adapter = new ListAdapterSetting(this.getActivity(), R.layout.layout_setting_row, itemList);
			this.setListAdapter(adapter);			
		}
			
		/*
		for(int i=0; i<itemList.size(); i++){
			
		}
		*/
	}

	public ArrayList<ModelSettingVariable> getList(){
		ArrayList<ModelSettingVariable> itemList = new ArrayList<ModelSettingVariable>();
		
		itemList.add(new ModelSettingVariable("�⺻����", true));
		itemList.add(new ModelSettingVariable("��������").setModel(ModelVersion.getInstance()));
		itemList.add(new ModelSettingVariable("��������", true));
		itemList.add(new ModelSettingVariable("�α�������").setModel(ModelDeviceLogin.getInstance()));
		itemList.add(new ModelSettingVariable("���ܵ�ģ��"));
		
		return itemList;
	}
}
