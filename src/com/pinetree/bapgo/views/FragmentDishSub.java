package com.pinetree.bapgo.views;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pinetree.bapgo.R;
import com.pinetree.bapgo.adapters.ListAdapterDish;
import com.pinetree.bapgo.model.BaseModel;
import com.pinetree.bapgo.model.ModelDishBook;
import com.pinetree.bapgo.model.ModelDishDate;
import com.pinetree.bapgo.model.ModelDishPeriod;
import com.pinetree.bapgo.util.DishBookUtils;
import com.pinetree.bapgo.views.FragmentDish.DishScrollListener;

public class FragmentDishSub extends FragmentDish{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Log.i("DebugPrint","onCreateView");
		if(container==null)
			return null;
		
		View view = inflater.inflate(R.layout.layout_listview_sub, container, false);
		
		//Log.i("DebugPrint","onCreateViewDone");
		return view;
	}
	
	@Override
	public void doPostResponse(BaseModel objModel){
		if(objModel.getClass().equals(ModelDishBook.class)){
			//Log.i("DebugPrint","getDate");
			ModelDishBook itemDishBook = (ModelDishBook)objModel;
			boolean isFirst = isFirst();
			//ListAdapterDish listAdapter = (ListAdapterDish) this.getListAdapter();
			
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
	
	@Override
	protected void loadAdapter(){
		if(listAdapter!=null)
			listAdapter.clear();
		listAdapter = new ListAdapterDish(this.getActivity(), R.layout.layout_schedule_row,
				DishBookUtils.getSortedDishDateList(
						getDisherSrl(),
						dishBook.getList(),
						//((ApplicationClass)this.getActivity().getApplicationContext()).getDishDateList(),
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
		
	}
	
	@Override

	protected void refreshList(){
		ListAdapterDish listAdapter = (ListAdapterDish) this.getListAdapter();
		listAdapter.notifyDataSetChanged();
	}
	
}
