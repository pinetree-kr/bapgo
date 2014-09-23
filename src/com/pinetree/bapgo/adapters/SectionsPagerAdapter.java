package com.pinetree.bapgo.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pinetree.bapgo.R;
import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.views.DummySectionFragment;
import com.pinetree.bapgo.views.FragmentDish;
import com.pinetree.bapgo.views.FragmentFriend;
import com.pinetree.bapgo.views.FragmentSetting;

public class SectionsPagerAdapter extends FragmentPagerAdapter{
	protected Context context;
	
	public SectionsPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		
		Bundle args = null;
		switch(position){
		case 0:
			fragment = new DummySectionFragment();
			args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		case 1:
			return FragmentFriend.getInstance();
		case 2:
			return FragmentDish.getInstance(ModelDeviceLogin.getInstance().getMemberSrl());
			//new FragmentDish(ModelDeviceLogin.getInstance().getMemberSrl());
			//FragmentDish.getInstance(ModelDeviceLogin.getInstance().getMemberSrl());//.setDisherSrl(ModelDeviceLogin.getInstance().getMemberSrl());
		case 3:
			return new FragmentSetting();	
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 4 total pages.
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return context.getString(R.string.page_timeline).toUpperCase();
		case 1:
			return context.getString(R.string.page_friend).toUpperCase();
		case 2:
			return context.getString(R.string.page_schedule).toUpperCase();
		case 3:
			return context.getString(R.string.page_more).toUpperCase();
		}
		return null;
	}
}
