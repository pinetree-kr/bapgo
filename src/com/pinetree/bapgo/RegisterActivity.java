package com.pinetree.bapgo;

import com.pinetree.bapgo.model.BaseModel;
import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.model.ModelUserRegister;
import com.pinetree.bapgo.util.ActivityUtils;
import com.pinetree.bapgo.util.PhoneBookUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {
	protected EditText inputName, inputPhoneNo;
	protected Button buttonRegister;
	protected String userName, phoneNo;
	protected CountDownTimer timer;
	
	protected ModelUserRegister modelUserRegister;
	
	protected final int SEC = 1000;

	protected SharedPreferences privatePref;

	protected void onResume(){
		super.onResume();
		if(PhoneBookUtils.isUsimRight()){
			inputPhoneNo.setHint(PhoneBookUtils.getMyPhoneNumber());
			inputPhoneNo.setFocusable(false);
		}else{
			inputPhoneNo.setHint("연락처");
			inputPhoneNo.setFocusable(true);
		}
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_register);
		
		// private preferences 불러오기
		privatePref = getPreferences(Activity.MODE_PRIVATE);
		
		inputName = (EditText) findViewById(R.id.inputName);
		inputPhoneNo = (EditText) findViewById(R.id.inputPhoneNo);
		buttonRegister = (Button) findViewById(R.id.buttonRegister);
		
		buttonRegister.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(PhoneBookUtils.isUsimRight()){
					phoneNo = inputPhoneNo.getHint().toString();
				}else{
					phoneNo = inputPhoneNo.getText().toString();					
				}
				//phoneNo = PhoneBookUtils.getGlobalPhoneNumber(phoneNo);
				
				modelUserRegister = new ModelUserRegister();
				
				modelUserRegister
					.setPhoneNo(phoneNo)
					.setName(inputName.getText().toString());
				
				if(!modelUserRegister.isValid()){
					Toast.makeText(ActivityUtils.getInstance().getContext(), R.string.empty_registerForm, Toast.LENGTH_SHORT).show();
					return;
				}
				
				ActivityUtils.getInstance()
					.sendRequest(modelUserRegister);
			}
		});
	}
	
}
