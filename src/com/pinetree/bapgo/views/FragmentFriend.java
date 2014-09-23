package com.pinetree.bapgo.views;

import java.util.ArrayList;

import com.pinetree.bapgo.ApplicationClass;
import com.pinetree.bapgo.R;
import com.pinetree.bapgo.adapters.ListAdapterFriend;
import com.pinetree.bapgo.model.BaseFragmentInterface;
import com.pinetree.bapgo.model.BaseModel;
import com.pinetree.bapgo.model.ModelDishBook;
import com.pinetree.bapgo.model.ModelMember;
import com.pinetree.bapgo.model.ModelPhoneBook;
import com.pinetree.bapgo.util.PhoneBookUtils;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentFriend extends ListFragment implements BaseFragmentInterface{
	protected ListView listView;
	protected ModelPhoneBook phoneBook = new ModelPhoneBook();
	
	protected ListAdapterFriend listAdapter;
	
	protected static FragmentFriend fragment; 
	
	
	public static FragmentFriend getInstance(){
		if(fragment==null)
			fragment = new FragmentFriend();
		return fragment;
	}
	
	public void init(){
		fragment = null;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(container==null)
			return null;
		View view = inflater.inflate(R.layout.layout_listview, container, false);
		
		return view;
	}
	
	/**
	 * 현재 프래그먼트를 불러온 Activity가 생성될때(이떄 데이터 전송을 하도록 한다)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		this.setListAdapter(listAdapter);
		
	}

	@Override
	public void onStart(){
		super.onStart();

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		loadAdapter();
	}
	
	protected void loadAdapter(){
		if(listAdapter!=null)
			listAdapter.clear();
		
		ArrayList<ModelMember> itemList = PhoneBookUtils.getSortRegisteredMemberList(
				((ApplicationClass)getActivity().getApplicationContext()).getSyncedPhoneBook().getList()
				);
		
		phoneBook.setList(itemList);
		
		listAdapter = new ListAdapterFriend(
				this.getActivity(),
				R.layout.layout_friend_row,
				phoneBook.getList()
				);
		
		this.setListAdapter(null);
		
		if(this.getListAdapter()==null)
			this.setListAdapter(listAdapter);
	}
	
	public void doPostResponse(BaseModel objModel){
		if(objModel.getClass().equals(ModelPhoneBook.class)){
			ModelPhoneBook itemPhoneBook = (ModelPhoneBook)objModel;
			
			ListAdapterFriend listAdapter = (ListAdapterFriend)this.getListAdapter();
			
			ArrayList<ModelMember> itemList = PhoneBookUtils.getSortRegisteredMemberList(itemPhoneBook.getList());
			phoneBook.setList(itemList);

			listAdapter.addAll(itemList);
			listAdapter.notifyDataSetChanged();
		}
	}
}
