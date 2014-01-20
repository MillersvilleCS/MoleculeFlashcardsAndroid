package com.millersvillecs.moleculeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//if credentials not found on device / invalid login
		//possibly use splash screen to check validity of credentials before launching - has it expired?
		//setContentView(R.layout.activity_main_login);
		//otherwise
		setContentView(R.layout.activity_main);
	}
	
	public void onStartButton(View view) {
		Intent intent = new Intent(this, CategoryActivity.class);
		//pass info along - will need in other buttons
	    //EditText editText = (EditText) findViewById(R.id.edit_message);
	    //String message = editText.getText().toString();
	    //intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	
	public void onTutorialButton(View view) {
		
	}

	public void onCreditsButton(View view) {
		
	}
	
	public void onLoginButton(View view) {
		
	}
	
	/* 
	 * see: http://developer.android.com/reference/android/app/Activity.html#ActivityLifecycle
	 * 
	
	protected void onStart() {};
    
    protected void onRestart() {};

    protected void onResume() {};

    protected void onPause() {};

    protected void onStop() {};

    protected void onDestroy() {};
    */
}
