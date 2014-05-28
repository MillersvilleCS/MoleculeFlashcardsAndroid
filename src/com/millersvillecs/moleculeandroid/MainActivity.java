package com.millersvillecs.moleculeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.millersvillecs.moleculeandroid.data.FileHandler;
import com.millersvillecs.moleculeandroid.data.MoleculeGamePreferences;
import com.millersvillecs.moleculeandroid.helper.ConfirmDialog;
import com.millersvillecs.moleculeandroid.helper.OnConfirmListener;

public class MainActivity extends Activity implements OnConfirmListener {
	
	private String username = "", auth = "";
	private boolean loggedIn = false, askingLogout = false;
	private Menu mainMenu;
	private ConfirmDialog confirmPlay, confirmLogout;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		setContentView(R.layout.activity_main);
	}
	
	private void init() {
		FileHandler fileHandler = new FileHandler(this);
		String[] credentials = fileHandler.read("credentials");
		this.loggedIn = credentials != null;
		if(loggedIn) {
			this.username = credentials[0];
			this.auth = credentials[1];
		} else {
			this.username = "guest";
			this.auth = null;
		}
	}
	
	private void setIcon() {
		this.mainMenu.clear();
		MenuInflater inflater = getMenuInflater();
	    if(this.loggedIn) {
	    	inflater.inflate(R.menu.logout, this.mainMenu);
	    } else {
	    	inflater.inflate(R.menu.login, this.mainMenu);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    this.mainMenu = menu;
	    setIcon();
	    
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_logout:
	        	this.confirmLogout = 
	        		new ConfirmDialog(this, 
									  getString(R.string.action_logout),
									  this.username + getString(R.string.confirm_logout));
	        	this.confirmLogout.setListener(this);
	        	this.askingLogout = true;
	            return true;
	        case R.id.action_login:
	        	Intent intent = new Intent(this, LoginActivity.class);
	    		startActivityForResult(intent, 1);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		init();//reset icon
		setIcon();
	}
	
	public void onPlayButton(View view) {
		if(this.loggedIn == false && this.confirmPlay == null) {
			this.confirmPlay = new ConfirmDialog(this, 
												 getString(R.string.warning),
												 getString(R.string.confirm_no_score));
			this.confirmPlay.setListener(this);
		} else {
			startCategoryActivity();
		}
	}
	
	public void onTutorialButton(View view) {
		Intent intent = new Intent(this, TutorialActivity.class);
		startActivity(intent);
	}

	public void onCreditsButton(View view) {
		Intent intent = new Intent(this, CreditsActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onConfirmResponse(int which) {
		if(this.askingLogout) {
			this.askingLogout = false;
			if(which == ConfirmDialog.POSITIVE) {
				logout();
			}
		} else {
			if(which == ConfirmDialog.POSITIVE) {
				startCategoryActivity();
			}
		}
	}
	
	private void startCategoryActivity() {
		Intent intent = new Intent(this, CategoryActivity.class);
		MoleculeGamePreferences preferences = new MoleculeGamePreferences(this);
		preferences.setUsername(this.username);
		preferences.setAuth(this.auth);
	    startActivity(intent);
	}
	
	private void logout() {
		FileHandler fileHandler = new FileHandler(this);
    	fileHandler.delete("credentials");
    	init();
    	setIcon();
	}
}
