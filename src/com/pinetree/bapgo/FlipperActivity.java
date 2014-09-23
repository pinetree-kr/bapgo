package com.pinetree.bapgo;

import java.util.Calendar;

import com.pinetree.bapgo.model.BaseModel;
import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.model.ModelDishBook;
import com.pinetree.bapgo.model.ModelDishPeriod;
import com.pinetree.bapgo.model.ModelMember;
import com.pinetree.bapgo.model.ModelPhoneBook;
import com.pinetree.bapgo.util.ActivityUtils;
import com.pinetree.bapgo.views.DummySectionFragment;
import com.pinetree.bapgo.views.FragmentFriend;
import com.pinetree.bapgo.views.FragmentDish;
import com.pinetree.bapgo.views.FragmentSetting;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FlipperActivity extends FragmentActivity{
	Fragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_sub);
		/*
		final ActionBar actionBar = getActionBar();
		
		Intent intent = getIntent();
		ModelMember modelMember = null;
		
		if(intent!=null){
			modelMember = (ModelMember) intent.getSerializableExtra("ObjModel");
		}
		
		if(modelMember==null || modelMember.getMemberSrl()<1){
			Toast.makeText(getApplication(), "정보가 없습니다", 2000).show();
			this.finish();
		}	
		
		TextView textSubTitleView = (TextView) this.findViewById(R.id.textSubTitle);
		textSubTitleView.setText(modelMember.getName());
		
		
		if(savedInstanceState == null){
			fragment = new FragmentDish();
			((FragmentDish)fragment).setDisherSrl(modelMember.getMemberSrl());
			
			getSupportFragmentManager().beginTransaction()
			.add(R.id.fragmentDishSub, fragment).commit();
		}
		
		// 스케쥴 동기화
		ModelDishBook itemDishBook = new ModelDishBook();
		itemDishBook
		.setDisherSrl(modelMember.getMemberSrl())
		.setStartEndDate(Calendar.getInstance(), 20);
		
		ActivityUtils.getInstance()
		.sendRequest(itemDishBook);
		*/
	}

	@Override
	protected void onStart(){
		super.onStart();
	}
	
	//@Override
	public void onPostResponse(BaseModel objModel){
		//super.onPostResponse(objModel);
		
		if(objModel.getClass().equals(ModelDishBook.class) || objModel.getClass().equals(ModelDishPeriod.class)){
			if(fragment!=null){
				((FragmentDish)fragment).doPostResponse(objModel);
			}
		}
	}
	
}
