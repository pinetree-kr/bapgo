package com.pinetree.bapgo.views;

import com.pinetree.bapgo.util.ActivityUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class CustomDialog{
	
	public static AlertDialog simpleDialog(String message, DialogInterface.OnClickListener posListener, DialogInterface.OnClickListener negListener, boolean isCancelable){
		AlertDialog.Builder builder = new AlertDialog.Builder(ActivityUtils.getInstance().getContext());
		builder.setMessage(message).setCancelable(isCancelable)
			.setPositiveButton("Yes", posListener)
			.setNegativeButton("No", negListener);
		
		AlertDialog dialog = builder.create();
		
		dialog.show();
		return dialog;
	}
}
