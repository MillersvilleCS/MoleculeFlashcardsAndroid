package com.millersvillecs.moleculeandroid;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.millersvillecs.moleculeandroid.data.CommunicationManager;
import com.millersvillecs.moleculeandroid.data.FileHandler;
import com.millersvillecs.moleculeandroid.data.Molecule;
import com.millersvillecs.moleculeandroid.data.MoleculeGamePreferences;
import com.millersvillecs.moleculeandroid.data.OnCommunicationListener;
import com.millersvillecs.moleculeandroid.helper.ErrorDialog;

public class MainActivity extends Activity implements OnCommunicationListener {
	
	private String username = "", auth = "";
	private ProgressDialog progress;
	private MenuItem logout;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FileHandler fileHandler = new FileHandler(this);
		String[] credentials = fileHandler.read("credentials");
		if(credentials != null) {
			this.username = credentials[0];
			this.auth = credentials[1];
			setContentView(R.layout.activity_main);
		} else {
			//if credentials not found on device / invalid login
			//possibly use splash screen to check validity of credentials before launching - has it expired? Must check.
			setContentView(R.layout.activity_main_login);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    this.logout = menu.findItem(R.id.action_logout);
	    this.logout.setVisible(!this.username.equals(""));
	    return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_logout:
	        	FileHandler fileHandler = new FileHandler(this);
	        	fileHandler.delete("credentials");
	        	this.username = "";
	        	this.auth = "";
	        	this.logout.setVisible(false);
	        	setContentView(R.layout.activity_main_login);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void onPlayButton(View view) {
		Intent intent = new Intent(this, CategoryActivity.class);
		MoleculeGamePreferences preferences = new MoleculeGamePreferences(this);
		preferences.setUsername(this.username);
		preferences.setAuth(this.auth);
	    startActivity(intent);
	}
	
	public void onTutorialButton(View view) {
		
	}

	public void onCreditsButton(View view) {
		Intent intent = new Intent(this, CreditsActivity.class);
		startActivity(intent);
	}
	
	public void onLoginButton(View view) {
		this.progress = new ProgressDialog(this);
		this.progress.setCanceledOnTouchOutside(false);
		this.progress.setMessage("Logging in...");
		this.progress.show();
		
		EditText emailBox = (EditText)findViewById(R.id.email);
		EditText passwordBox = (EditText)findViewById(R.id.password);
		
		String email = emailBox.getText().toString();
		String password = passwordBox.getText().toString();
		
		CommunicationManager communication = new CommunicationManager(this);
		communication.login(email, password);
	}

	@Override
	public void onRequestResponse(JSONObject response) {
		this.progress.dismiss();
		try {
			if(response.getBoolean("success")) {
				String username = response.getString("username");
				String auth = response.getString("auth");
				FileHandler fileHandler = new FileHandler(this);
				String[] data = new String[2];
				data[0] = username;
				data[1] = auth;
				fileHandler.write("credentials", data);
				this.username = username;
				this.auth = auth;
				this.logout.setVisible(true);
				setContentView(R.layout.activity_main);
			} else {
				new ErrorDialog(getFragmentManager(), response.getString("error")).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			new ErrorDialog(getFragmentManager(), "Invalid Server Response").show();
		} catch (NullPointerException e) {
			e.printStackTrace();
			new ErrorDialog(getFragmentManager(), "Invalid Server Response").show();
		}
	}

	@Override
	public void onMoleculeResponse(Molecule molecule) {}

	@Override
	public void onImageResponse(Bitmap bitmap, boolean error) {}
	
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
