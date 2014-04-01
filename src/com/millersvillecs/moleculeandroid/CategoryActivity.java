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
import com.millersvillecs.moleculeandroid.data.OnCommunicationListener;
import com.millersvillecs.moleculeandroid.helper.CategoryBaseAdapter;
import com.millersvillecs.moleculeandroid.helper.CategoryItem;
import com.millersvillecs.moleculeandroid.helper.ErrorDialog;

public class CategoryActivity extends Activity implements OnItemClickListener, 
														  OnDismissListener, OnCommunicationListener {
	
	private FileHandler fileHandler;
	private CommunicationManager comm;
	private CategoryItem[] categories;
	private ProgressDialog progress;
	private String username, auth;
	private ArrayList<String> ids;
	private ArrayList<String> urls;
	private boolean wantedDismiss = false;
	private JSONObject fullGameJSON;
	private JSONArray gamesJSON, categoriesJSON;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		this.username = intent.getStringExtra(MainActivity.USERNAME);
		this.auth = intent.getStringExtra(MainActivity.AUTH);
		
		setContentView(R.layout.activity_category);
		
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
				this.categoriesJSON = this.fullGameJSON.getJSONArray("categories");
				this.gamesJSON = this.fullGameJSON.getJSONArray("available_games");
				
				this.urls = new ArrayList<String>();
				this.ids = new ArrayList<String>();
				for(int i = 0; i < this.gamesJSON.length(); i++) {
					this.urls.add("https://exscitech.org/" + 
								   this.gamesJSON.getJSONObject(i).getString("image").substring(1));
					this.ids.add(this.gamesJSON.getJSONObject(i).getString("id"));//.13
				}
				
				createCategories();
			
			} catch (JSONException e) {
				e.printStackTrace();
				new ErrorDialog(getFragmentManager(), "Could not restore state.").show();
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long clickId) {
		int id = this.categories[position].getID();
		JSONArray newGamesJSON = new JSONArray();
		try{
			int index = 0;
			for(int i = 0; i < this.gamesJSON.length(); i++) {
				if(id == this.gamesJSON.getJSONObject(i).getInt("category")) {
					newGamesJSON.put(index, this.gamesJSON.getJSONObject(i));
					index++;
				}
			}
		} catch(JSONException e) {
			e.printStackTrace();
			new ErrorDialog(getFragmentManager(), "Could not parse games.").show();
		}
		
		
		Intent intent = new Intent(this, SelectionActivity.class);
	    intent.putExtra(MainActivity.USERNAME, this.username);
	    intent.putExtra(MainActivity.AUTH, this.auth);
	    intent.putExtra(MainActivity.GAME_JSON, newGamesJSON.toString());
	    startActivity(intent);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		//should make a way to cancel loading
		if(this.wantedDismiss) {
			this.wantedDismiss = false;
		} else {
			finish();
		}
	}

	@Override
	public void onRequestResponse(JSONObject response) {
		this.fileHandler = new FileHandler(this);
		try{
			if(response.getBoolean("success")) {
				this.fullGameJSON = response;
				this.categoriesJSON = response.getJSONArray("categories");
				this.gamesJSON = response.getJSONArray("available_games");
				//this.fileHandler.writeTemp("games", avail.toString().split("\n"));
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
				//this.fileHandler.deleteTemp("games");
				new ErrorDialog(getFragmentManager(), response.getString("error")).show();
				this.wantedDismiss = true;
		        this.progress.dismiss();
			}
		} catch(JSONException e) {
			e.printStackTrace();
			//this.fileHandler.deleteTemp("games");
			new ErrorDialog(getFragmentManager(), "Invalid Server Response").show();
			this.wantedDismiss = true;
	        this.progress.dismiss();
		} catch(NullPointerException e) {
		    e.printStackTrace();
            new ErrorDialog(getFragmentManager(), "Could not connect to network").show();
            this.wantedDismiss = true;
            this.progress.dismiss();
		}
	}

	@Override
	public void onMoleculeResponse(Molecule molecule) {}

	@Override
	public void onImageResponse(Bitmap bitmap, boolean error) {
		String id = this.ids.remove(0);
		
		if(bitmap == null) {
		    if(error) {
		        this.ids.clear();
		        this.urls.clear();
		        this.wantedDismiss = true;
                this.progress.dismiss();
                new ErrorDialog(getFragmentManager(), "Could not connect to network!").show();
                return;
		    }
		} else {
		    this.fileHandler.writeTempImage(bitmap, id + ".jpg");  
		}
		
		if(this.urls.size() == 0) {
            createCategories();
        } else {
            this.comm.downloadImage(this.urls.remove(0), 
                                    this.fileHandler.createFileTemp(ids.get(0) + ".png"));
        }
	}
	
	private void createCategories() {
        this.categories = new CategoryItem[this.categoriesJSON.length()];
        try{
        	for(int i = 0; i < this.categoriesJSON.length(); i++) {
            	JSONObject category = this.categoriesJSON.getJSONObject(i);
            	this.categories[i] = new CategoryItem(category.getString("name"), 
            										  category.getString("description"),
            										  category.getInt("ID"));
            }
        } catch(JSONException e) {
        	new ErrorDialog(getFragmentManager(), "Invalid Categories").show();
        	if(this.progress != null) {
        		this.wantedDismiss = true;
    	        this.progress.dismiss();
        	}
        }
        
        
        ListView categoryListView = (ListView) findViewById(R.id.category_list);
        CategoryBaseAdapter listHandler = new CategoryBaseAdapter(this, categories);
        categoryListView.setAdapter(listHandler);
        categoryListView.setOnItemClickListener(this);
        
        if(this.progress != null) {
        	this.wantedDismiss = true;
            this.progress.dismiss();
        }
	}
	
	@Override
	protected void onSaveInstanceState (Bundle outState) {
		outState.putString("fullGameJSON", this.fullGameJSON.toString());
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
