package com.millersvillecs.moleculeandroid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.millersvillecs.moleculeandroid.data.CommunicationManager;
import com.millersvillecs.moleculeandroid.data.FileHandler;
import com.millersvillecs.moleculeandroid.data.Molecule;
import com.millersvillecs.moleculeandroid.data.MoleculeGamePreferences;
import com.millersvillecs.moleculeandroid.data.OnCommunicationListener;
import com.millersvillecs.moleculeandroid.helper.AlertDialog;
import com.millersvillecs.moleculeandroid.helper.SelectionBaseAdapter;
import com.millersvillecs.moleculeandroid.helper.SelectionItem;

public class SelectionActivity extends Activity implements OnItemClickListener, 
														   OnDismissListener, OnCommunicationListener {
	
	private MoleculeGamePreferences preferences;
	private ProgressDialog progress;
	private FileHandler fileHandler;
	private CommunicationManager comm;
	private SelectionItem[] games;
	private String auth;
	private ArrayList<String> ids;
	private ArrayList<String> urls;
	private JSONObject fullGameJSON;
	private JSONArray gamesJSON;
	private boolean wantedDismiss = false;
	
	/**
	 * If we are seeing this page for the first time, pull the categories from the internet.
	 * Otherwise load them from the saved state
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.preferences = new MoleculeGamePreferences(this);
		this.auth = this.preferences.getAuth();
		
		this.fileHandler = new FileHandler(this);
		
		setContentView(R.layout.activity_selection);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app
		
		if(savedInstanceState == null) {
			
			this.comm = new CommunicationManager(this);
			this.comm.availableGames(this.auth);
			
			this.progress = new ProgressDialog(this);
			this.progress.setCanceledOnTouchOutside(false);
			this.progress.setMessage("Loading Games...");
			this.progress.setOnDismissListener(this);
			this.progress.show();
			
		} else {
			try {
				this.fullGameJSON = new JSONObject(savedInstanceState.getString("fullGameJSON"));
				this.gamesJSON = this.fullGameJSON.getJSONArray("available_games");
				
				createGameSelections();
				
			} catch (JSONException e) {
				e.printStackTrace();
				new AlertDialog(this,
								getString(R.string.error_title), 
								"Could not restore state.").show();
			}
		}
	}
	
	/*
	 * Handles 'Up' Menu press (does the same thing as the back button)
	 */
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	//NavUtils.navigateUpFromSameTask(this);
        	finish();//so "up" looks like "back"
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	/**
	 * Get the game clicked, and use that ID to set the index inside the JSON where
	 * that game resides. Set it in our preferences to pass to the next activity.
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		try{
			int gameID = this.games[position].getGameID();
			position = -1;
			for(int i = 0; i < this.gamesJSON.length(); i++) {
				if(this.gamesJSON.getJSONObject(i).getInt("id") == gameID) {
					position = i;
					break;
				}
			}
		} catch(JSONException e) {
			e.printStackTrace();
			finish();
			return;
		}
		
	    this.preferences.setPosition(position);
	    this.preferences.setAllGamesJSON(this.gamesJSON.toString());
	    Intent intent = new Intent(this, DescriptionActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * Get the JSON response that lists all the games and store it. Begin downloading images.
	 */
	@Override
	public void onRequestResponse(JSONObject response) {
		try{
			if(response.getBoolean("success")) {
				this.fullGameJSON = response;
				this.gamesJSON = response.getJSONArray("available_games");
				
				this.urls = new ArrayList<String>();
				this.ids = new ArrayList<String>();
				for(int i = 0; i < gamesJSON.length(); i++) {
					this.urls.add("https://exscitech.org/" + 
								gamesJSON.getJSONObject(i).getString("image").substring(1));
					this.ids.add(gamesJSON.getJSONObject(i).getString("id"));//.13
				}
				this.comm.downloadImage(this.urls.remove(0), 
				                        this.fileHandler.createFileTemp(ids.get(0) + ".png"));
			} else {
				new AlertDialog(this, getString(R.string.error_title),  response.getString("error")).show();
				this.wantedDismiss = true;
		        this.progress.dismiss();
			}
		} catch(JSONException e) {
			e.printStackTrace();
			new AlertDialog(this,
							getString(R.string.error_title),  
							"Invalid Server Response").show();
			this.wantedDismiss = true;
	        this.progress.dismiss();
		} catch(NullPointerException e) {
		    e.printStackTrace();
            new AlertDialog(this,
            				getString(R.string.error_title),  
            				"Could not connect to network").show();
            this.wantedDismiss = true;
            this.progress.dismiss();
		}
	}

	@Override
	public void onMoleculeResponse(Molecule molecule) {}
	
	/**
	 * Get the images downloaded and save them. If the bitmap is null, there was
	 * either an error (in which case display it) or the copy in our cache is the
	 * same as the server and it doesn't need re-downloaded.
	 * 
	 * Once we have our images we can create the listview
	 */
	@Override
	public void onImageResponse(Bitmap bitmap, boolean error) {
		String id = this.ids.remove(0);
		if(bitmap == null) {
		    if(error) {
		        this.ids.clear();
		        this.urls.clear();
		        this.wantedDismiss = true;
                this.progress.dismiss();
                new AlertDialog(this,
                				getString(R.string.error_title),  
                				"Could not connect to network!").show();
                return;
		    }
		} else {
		    this.fileHandler.writeTempImage(bitmap, id + ".png");  
		}
		
		if(this.urls.size() == 0) {
			createGameSelections();
        } else {
            this.comm.downloadImage(this.urls.remove(0), 
                                    this.fileHandler.createFileTemp(ids.get(0) + ".png"));
        }
	}
	/**
	 * Dialog dismiss handler.
	 */
	@Override
	public void onDismiss(DialogInterface dialog) {
		if(this.wantedDismiss) {
			this.wantedDismiss = false;
		} else {
			finish();
		}
	}
	
	/**
	 * ListViews in Android must be overridden by a custom class. Our custom class
	 * takes SelectionItem objects. Create these with the data from our JSON request
	 * and the images we just got done loading
	 * 
	 * Create our listview and set the adapter to be this class.
	 */
	private void createGameSelections() {
		this.games = new SelectionItem[this.gamesJSON.length()];
        try{
        	for(int i = 0; i < this.gamesJSON.length(); i++) {
            	JSONObject game = this.gamesJSON.getJSONObject(i);
            	String image = game.getString("id") + ".png";
				Bitmap bitmap = fileHandler.readTempImage(image);
            	this.games[i] = new SelectionItem(bitmap, 
            									  game.getString("name"),
            									  game.getString("description"),
            									  game.getInt("id"));
            }
        } catch(JSONException e) {
        	new AlertDialog(this,
        					getString(R.string.error_title),  
        					"Invalid Game List").show();
        	if(this.progress != null) {
        		this.wantedDismiss = true;
    	        this.progress.dismiss();
        	}
        }
        
        
        ListView gameListView = (ListView) findViewById(R.id.game_list);
		SelectionBaseAdapter listHandler = new SelectionBaseAdapter(this, games);
		gameListView.setAdapter(listHandler);
		gameListView.setOnItemClickListener(this);
        
        if(this.progress != null) {
        	this.wantedDismiss = true;
            this.progress.dismiss();
        }
	}
	
	/**
	 * If the is about to sleep, dismiss the progress dialog.
	 */
	@Override
	public void onPause() {
		super.onPause();
		if(this.progress != null) {
        	this.wantedDismiss = true;
            this.progress.dismiss();
        }
	}
	
	/**
	 * Put our JSON in the instance state to get back on rotations
	 */
	@Override
	protected void onSaveInstanceState (Bundle outState) {
		outState.putString("fullGameJSON", this.fullGameJSON.toString());
	}
}
