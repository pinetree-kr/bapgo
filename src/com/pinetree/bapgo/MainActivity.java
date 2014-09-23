package com.pinetree.bapgo;

import java.util.Calendar;

import com.pinetree.bapgo.adapters.SectionsPagerAdapter;
import com.pinetree.bapgo.model.BaseModel;
import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.model.ModelDishBook;
import com.pinetree.bapgo.model.ModelDishPeriod;
import com.pinetree.bapgo.model.ModelPhoneBook;
import com.pinetree.bapgo.util.ActivityUtils;
import com.pinetree.bapgo.util.PhoneBookUtils;
import com.pinetree.bapgo.views.DummySectionFragment;
import com.pinetree.bapgo.views.FragmentFriend;
import com.pinetree.bapgo.views.FragmentDish;
import com.pinetree.bapgo.views.FragmentSetting;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;

public class MainActivity extends BaseFragmentActivity implements
		ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		final ActionBar actionBar = getActionBar();
		
		//RelativeLayout titleView = (RelativeLayout) this.getLayoutInflater().inflate(R.layout.layout_title, null);
		
		//actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM|ActionBar.DISPLAY_SHOW_TITLE);
		//actionBar.setDisplayHomeAsUpEnabled(false);
		//actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		//actionBar.setDisplayShowHomeEnabled(false);
		//actionBar.setDisplayUseLogoEnabled(false);
		//actionBar.setDisplayShowTitleEnabled(false);
		//actionBar.setDisplayShowCustomEnabled(true);
		//actionBar.setCustomView(titleView);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		//window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				this.getBaseContext(),
				getSupportFragmentManager());
		
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	protected void onResume(){
		super.onResume();
		
		onPreSyncDishBook();
	}
	
	@Override
	protected void onStart(){
		super.onStart();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	@Override
	public void onPostResponse(BaseModel objModel){
		super.onPostResponse(objModel);
		
		if(objModel.isError())
			return;
		
		if(objModel.getClass().equals(ModelPhoneBook.class)){
			FragmentFriend fragment = (FragmentFriend)mSectionsPagerAdapter.getItem(1);
			fragment.doPostResponse(objModel);
		}else if(objModel.getClass().equals(ModelDishBook.class)){
			ModelDishBook dishBook = (ModelDishBook)objModel;
			
			if(dishBook.isFirst()){
				//Log.i("DebugPrint","dishFirst, Size:"+dishBook.getList().size());
				onPostSyncDishBook(dishBook);
			}else{
				//Log.i("DebugPrint","dishSecond");
				FragmentDish fragment = FragmentDish.getInstance(ModelDeviceLogin.getInstance().getMemberSrl());
				fragment.doPostResponse(objModel);				
			}
		}else if(objModel.getClass().equals(ModelDishPeriod.class)){
			//FragmentDish fragment = (FragmentDish)mSectionsPagerAdapter.getItem(0);
			FragmentDish fragment = FragmentDish.getInstance(ModelDeviceLogin.getInstance().getMemberSrl());
			fragment.doPostResponse(objModel);
		}
	}
	
	@Override
	public void moveToActivity(Class activityClass){
		super.moveToActivity(activityClass);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		fragmentInit(ModelDeviceLogin.getInstance().getMemberSrl());
	}
}
