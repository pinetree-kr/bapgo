package com.pinetree.bapgo.views;

import java.util.ArrayList;
import java.util.Calendar;

import com.pinetree.bapgo.ApplicationClass;
import com.pinetree.bapgo.R;
import com.pinetree.bapgo.adapters.ListAdapterDish;
import com.pinetree.bapgo.model.BaseModel;
import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.model.ModelDishBook;
import com.pinetree.bapgo.model.ModelDishDate;
import com.pinetree.bapgo.model.ModelDishPeriod;
import com.pinetree.bapgo.util.ActivityUtils;
import com.pinetree.bapgo.util.DishBookUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentDish extends ListFragment{
	protected final int TOTAL_DAYS = 60;
	
	protected View headerView, footerView;
	protected ListAdapterDish listAdapter;
	
	protected ModelDishBook dishBook;
	protected static FragmentDish fragment;
	
	protected boolean refreshOk, onSync;
	protected int onDraging, disherSrl;
	
	protected boolean isDisher;
	
	public FragmentDish(){
		dishBook = new ModelDishBook();
		disherSrl = 0;
	}
	
	public FragmentDish(int disherSrl){
		dishBook = new ModelDishBook();
		setDisherSrl(disherSrl);
	}
	
	public void init(){
		//fragment.clear();
		fragment = null;
	}
	
	
	public static FragmentDish getInstance(int disherSrl){
		if(fragment==null)
			fragment = new FragmentDish(disherSrl);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.i("DebugPrint","onCreate");
		
		headerView = this.getActivity().getLayoutInflater().inflate(R.layout.layout_listview_header, null, false);
		footerView = this.getActivity().getLayoutInflater().inflate(R.layout.layout_listview_footer, null, false);
		
		//((ApplicationClass)this.getActivity().getApplicationContext()).clearDishDateList();
		//Log.i("DebugPrint","onCreateDone");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("DebugPrint","onCreateView");
		if(container==null)
			return null;
		
		View view = inflater.inflate(R.layout.layout_listview, container, false);
		
		//Log.i("DebugPrint","onCreateViewDone");
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Log.i("DebugPrint","onActivityCreated");
		loadAdapter();
		//resetList();
		//Log.i("DebugPrint","onActivityCreateDone");
	}
	
	@Override
	public void onStart(){
		super.onStart();
		Log.i("DebugPrint","onStart");
		//Log.i("DebugPrint","onStartDone");
	}
	@Override
	public void onResume(){
		Log.i("DebugPrint","onResume");
		super.onResume();
		
		//loadAdapter();
	}
	
	public FragmentDish setDisherSrl(int disherSrl){
		this.disherSrl = disherSrl;
		return this;
	}
	
	public int getDisherSrl(){
		return disherSrl;
	}
	
	public int getCountOfDishDate(){
		return dishBook.getList().size();
	}
	
	public boolean isFirst(){
		if(getCountOfDishDate()>0)
			return false;
		else
			return true;
	}
		
	protected void loadData(int n){
		int remainedDays = TOTAL_DAYS - getCountOfDishDate();
		//Log.i("DebugPrint","loaded By:"+n);
		if(remainedDays<1)
			return;
		
		ModelDishBook itemDishBook = new ModelDishBook();
		Calendar lastCalendar = (Calendar) Calendar.getInstance().clone();
		lastCalendar.add(Calendar.DATE, getCountOfDishDate());
		
		if(remainedDays>14)
			remainedDays=14;
		
		itemDishBook
		.setDisherSrl(getDisherSrl())
		.setStartEndDate(lastCalendar, remainedDays);
		
		ActivityUtils.getInstance()
		.sendRequest(itemDishBook);
	}
	
	protected void loadAdapter(){
		if(listAdapter!=null)
			listAdapter.clear();
		
		dishBook = new ModelDishBook();
		
		listAdapter = new ListAdapterDish(this.getActivity(), R.layout.layout_schedule_row,
				DishBookUtils.getSortedDishDateList(
						getDisherSrl(),
						dishBook.getList(),
						true
						)
				);
		
		ListView listView = this.getListView();
		
		this.setListAdapter(null);
		
		if(listView.getHeaderViewsCount()==0)
			listView.addHeaderView(headerView);
		if(listView.getFooterViewsCount()==0)
			listView.addFooterView(footerView);
		
		if(this.getListAdapter()==null)
			this.setListAdapter(listAdapter);
		
		listView.setSelection(listView.getHeaderViewsCount());
		listView.setScrollbarFadingEnabled(true);
		
		listView.setOnScrollListener(new DishScrollListener());
		
		doPostResponse(((ApplicationClass)getActivity().getApplicationContext()).getSyncedDishBook());
	}
	
	protected void onRefreshList(int globalPosY){
		ListView listView = this.getListView();
		View view = listView.getChildAt(0);
		int[] listViewPos = new int[2];
		listView.getLocationOnScreen(listViewPos);
		int listPosY = listViewPos[1];
		
		float ratio = (1-((float)listPosY - globalPosY)/view.getHeight())*180;
		
		changeRefreshHeaderText(ratio);
		if(onDraging==1){
			if(ratio==180){
				refreshOk = true;
			}else{
				refreshOk = false;
			}
		}
	}
	
	protected void changeRefreshHeaderText(float ratio){
		TextView textHeaderView = (TextView) headerView.findViewById(R.id.textListHeader);
		
		if(ratio==180){
			textHeaderView.setText(R.string.refresh_done_kr);			
		}else{
			textHeaderView.setText(R.string.refresh_kr);			
		}
	}
	
	protected void changeRefreshFooterText(){
		TextView textFooterView = (TextView) headerView.findViewById(R.id.textListHeader);
		
		if(getCountOfDishDate()<TOTAL_DAYS)
			textFooterView.setText(R.string.more_kr);
		else
			textFooterView.setText(R.string.more_done_kr);
	}
	
	protected void resetList(){
		//dishBook = new ModelDishBook();
		loadAdapter();
		//loadData(2);
	}
	
	protected void setSelectionFirstData(){
		ListView listView = this.getListView();
		listView.setSelectionFromTop(1, 0);
	}
	
	protected void refreshListView(){
		refreshOk = false;
		BackGroundTask thread = new BackGroundTask();
		thread.execute();
	}
	
	
	protected void refreshList(){
		ListAdapterDish listAdapter = (ListAdapterDish) this.getListAdapter();
		listAdapter.notifyDataSetChanged();
	}
	
	protected class BackGroundTask extends AsyncTask<String, Integer, Long>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
		}
		@Override
		protected Long doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		protected void onPostExecute(Long result){
			super.onPostExecute(result);
			resetList();
			setSelectionFirstData();
		}
		
	}
	
	public void doPostResponse(BaseModel objModel){
		if(objModel.getClass().equals(ModelDishBook.class)){
			ModelDishBook itemDishBook = (ModelDishBook)objModel;
			boolean isFirst = isFirst();
			
			ArrayList<ModelDishDate> itemList = DishBookUtils.getDishDateListSync(getDisherSrl(), itemDishBook);
			
			dishBook.addList(itemList);
			listAdapter.addAll(DishBookUtils.getSortedDishDateList(getDisherSrl(), itemList, isFirst));
			refreshList();
			changeRefreshFooterText();
			
		}else if(objModel.getClass().equals(ModelDishPeriod.class)){
			ModelDishPeriod itemDishPeriod = (ModelDishPeriod)objModel;
			
			if(itemDishPeriod.isError()){
				itemDishPeriod.setOpenCloseDish(!itemDishPeriod.isOpenDish());
				refreshList();
			}
		}
	}
	

	protected class DishScrollListener implements OnScrollListener{
		boolean visibleFirstItem;
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			
			visibleFirstItem = false;
			
			// on footer
			if( !isFirst() && (firstVisibleItem+visibleItemCount)==totalItemCount){
				if(!onSync && getCountOfDishDate()<60){
					onSync = true;
					loadData(1);
					changeRefreshFooterText();
				}
			}
			// on header
			else if(onDraging != 1 && firstVisibleItem == 0){
				// 드래그시 0번째 refreshView가 화면에 안보이게
				setSelectionFirstData();
			}
			else if(onDraging == 1 && firstVisibleItem == 0){
				int[] globalPos = new int[2];
				visibleFirstItem = true;
				view.getChildAt(0).getLocationOnScreen(globalPos);
				onRefreshList(globalPos[1]);
			}else{
				onSync = false;
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			onDraging = scrollState;
			//Log.i("DebugPrint","event:"+onDraging);
			if(scrollState==0 && refreshOk){
				if(!onSync){
					onSync = true;
					refreshListView();
				}
			}else if(visibleFirstItem){
				setSelectionFirstData();
			}
			return;
		}
		
	}

}
