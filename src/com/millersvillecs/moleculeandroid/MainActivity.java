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
import com.millersvillecs.moleculeandroid.helper.AlertDialog;
import com.millersvillecs.moleculeandroid.helper.ConfirmDialog;
import com.millersvillecs.moleculeandroid.helper.OnConfirmListener;

public class MainActivity extends Activity implements OnConfirmListener {
	
	private String username = "", auth = "";
	private boolean loggedIn = false, askingLogout = false;
	private Menu mainMenu;
	private ConfirmDialog confirmPlay, confirmLogout;
	private FileHandler fileHandler;
	
	/**
	 * If we aren't logged in, and the welcome denoting file is missing, display
	 * our one-time welcome message (after writing the welcome file so it never)
	 * displays again.
	 * 
	 * The first "if" statement prevents us from reading from the file system twice if
	 * we don't need to. Someone that has logged in is guaranteed to have already seen
	 * the welcome message.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		if(!this.loggedIn) {
			String[] welcome = this.fileHandler.read("welcome");
			if(welcome == null) {
				String[] empty = {""};
				this.fileHandler.write("welcome", empty);
				new AlertDialog(this,
								getString(R.string.welcome_title),
								getString(R.string.welcome_message)).show();
			}
		}
		setContentView(R.layout.activity_main);
	}
	
	/**
	 * See if we have an auth code saved.
	 */
	private void init() {
		this.fileHandler = new FileHandler(this);
		String[] credentials = this.fileHandler.read("credentials");
		this.loggedIn = credentials != null;
		if(loggedIn) {
			this.username = credentials[0];
			this.auth = credentials[1];
		} else {
			this.username = "Guest (Not Saved)";
			this.auth = null;
		}
	}
	
	/**
	 * Put the proper icon in the upper right - either login or logout
	 */
	private void setIcon() {
		this.mainMenu.clear();
		MenuInflater inflater = getMenuInflater();
	    if(this.loggedIn) {
	    	inflater.inflate(R.menu.logout, this.mainMenu);
	    } else {
	    	inflater.inflate(R.menu.login, this.mainMenu);
	    }
	}
	
	/**
	 * Android API call that creates the options menu - override to
	 * add the correct icon.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    this.mainMenu = menu;
	    setIcon();
	    
	    return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Do the correct action depending on which icon button is pressed.
	 */
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
	
	/**
	 * Logging in or registering returns a result to this activity. This means
	 * we are now logged in and need to set our icon.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		init();//reset icon
		setIcon();
	}
	
	/**
	 * Play unless we are not logged in, in which case warn the user.
	 * @param view
	 */
	public void onPlayButton(View view) {
		if(this.loggedIn == false && this.confirmPlay == null) {
			this.confirmPlay = new ConfirmDialog(this, 
												 getString(R.string.warning),
												 getString(R.string.confirm_no_score));
			this.confirmPlay.setListener(this);
		} else {
			startSelectionActivity();
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
	
	/**
	 * Handler for our confirm dialogs:
	 * Are you sure you want to log out?
	 * Are you sure you want to play without being logged in?
	 * 
	 * Both of these prompts do nothing if the user hits no or back.
	 */
	@Override
	public void onConfirmResponse(int which) {
		if(this.askingLogout) {
			this.askingLogout = false;
			if(which == ConfirmDialog.POSITIVE) {
				logout();
			}
		} else {
			if(which == ConfirmDialog.POSITIVE) {
				startSelectionActivity();
			}
		}
	}
	
	/**
	 * Get our preferences and set our credential information in them. Start the
	 * Selection Activity.
	 */
	private void startSelectionActivity() {
		Intent intent = new Intent(this, SelectionActivity.class);
		MoleculeGamePreferences preferences = new MoleculeGamePreferences(this);
		preferences.setUsername(this.username);
		preferences.setAuth(this.auth);
	    startActivity(intent);
	}
	
	/**
	 * Delete the credentials file and reset our icon.
	 */
	private void logout() {
		this.fileHandler = new FileHandler(this);
    	fileHandler.delete("credentials");
    	init();
    	setIcon();
	}
}
