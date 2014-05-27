package com.millersvillecs.moleculeandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class RegisterActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_register);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            //NavUtils.navigateUpFromSameTask(this);
        	setResult(RESULT_CANCELED);
            finish();//so "up" looks like "back"
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();
	}
	
	public void onRegisterButton(View view) {
		//TODO
		//if successful, setResult(RESULT_OK)
	}
}
