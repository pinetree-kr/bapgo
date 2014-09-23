package com.pinetree.bapgo.views;

import com.pinetree.bapgo.model.ModelMember;

public class FriendDetailViewHolder {
	protected ModelMember modelMember;
	
	public FriendDetailViewHolder(ModelMember modelMember){
		this.modelMember = modelMember;
	}
	
	public ModelMember getMember(){
		return modelMember;
	}
}
