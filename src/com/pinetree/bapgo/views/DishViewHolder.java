package com.pinetree.bapgo.views;

import com.pinetree.bapgo.model.ModelDishPeriod;

import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DishViewHolder {
	protected ModelDishPeriod disherPeriod;
	
	public DishViewHolder(ModelDishPeriod disherPeriod){
		this.disherPeriod = disherPeriod;
	}
	
	public ModelDishPeriod getDishPeriod(){
		return disherPeriod;
	}
}
