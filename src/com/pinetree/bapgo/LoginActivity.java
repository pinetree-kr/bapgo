package com.pinetree.bapgo;

import com.pinetree.bapgo.model.ModelDeviceLogin;
import com.pinetree.bapgo.util.ActivityUtils;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
	protected EditText inputId, inputPw;
	protected Button buttonLogin;
	protected String user_id, password;
	protected CountDownTimer timer;
	
	protected final int SEC = 1000;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		inputId = (EditText) findViewById(R.id.inputId);
		inputPw = (EditText) findViewById(R.id.inputPw);
		buttonLogin = (Button) findViewById(R.id.buttonLogin);
		
		buttonLogin.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				
				
				ModelDeviceLogin.getInstance()
				.setUserId(inputId.getText().toString())
				.setPassword(inputPw.getText().toString());
				
				if(!ModelDeviceLogin.getInstance().isValid()){
					Toast.makeText(ActivityUtils.getInstance().getContext(), R.string.empty_loginForm, Toast.LENGTH_SHORT).show();
					return;
				}
				
				ActivityUtils.getInstance()
					.sendRequest(ModelDeviceLogin.getInstance());
			}
		});
	}

	protected void onResume(){
		super.onResume();
	}
}