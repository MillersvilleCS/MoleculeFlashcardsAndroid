package com.millersvillecs.moleculeandroid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.millersvillecs.moleculeandroid.data.CommunicationManager;
import com.millersvillecs.moleculeandroid.data.FileHandler;
import com.millersvillecs.moleculeandroid.data.OnCommunicationListener;
import com.millersvillecs.moleculeandroid.helper.ErrorDialog;
import com.millersvillecs.moleculeandroid.helper.SelectionBaseAdapter;
import com.millersvillecs.moleculeandroid.helper.SelectionItem;

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

public class CategoryActivity extends Activity implements OnItemClickListener, 
														  OnDismissListener, OnCommunicationListener {
	
	private FileHandler fileHandler;
	private CommunicationManager comm;
	private SelectionItem[] categories;
	private ProgressDialog progress;
	private String username, auth;
	private ArrayList<String> ids;
	private ArrayList<String> urls;
	private boolean wantedDismiss = false;
	private JSONArray fullGameJSON;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		this.username = intent.getStringExtra(MainActivity.USERNAME);
		this.auth = intent.getStringExtra(MainActivity.AUTH);
		
		setContentView(R.layout.activity_category);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app
		
		this.comm = new CommunicationManager(this);
		this.comm.availableGames(this.auth);
		
		this.progress = new ProgressDialog(this);
		this.progress.setCanceledOnTouchOutside(false);
		this.progress.setMessage("Loading Games...");
		this.progress.setOnDismissListener(this);
		this.progress.show();
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//System.out.println(this.categories[position].getDescription());
		//spawn activity to pick game from category
		//no categories yet - just advance
		Intent intent = new Intent(this, SelectionActivity.class);
	    intent.putExtra(MainActivity.USERNAME, this.username);
	    intent.putExtra(MainActivity.AUTH, this.auth);
	    intent.putExtra(MainActivity.GAME_JSON, this.fullGameJSON.toString());
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
				JSONArray avail = response.getJSONArray("available_games");
				this.fullGameJSON = avail;
				//this.fileHandler.writeTemp("games", avail.toString().split("\n"));
				this.urls = new ArrayList<String>();
				this.ids = new ArrayList<String>();
				for(int i = 0; i < avail.length(); i++) {
					this.urls.add("http://exscitech.gcl.cis.udel.edu" + 
								   avail.getJSONObject(i).getString("image").substring(1));
					this.ids.add(avail.getJSONObject(i).getString("id"));//.13
				}
				this.comm.downloadImage(this.urls.remove(0), 
				                        this.fileHandler.createFileTemp(ids.get(0) + ".jpg"));
			} else {
				this.fileHandler.deleteTemp("games");
				new ErrorDialog(getFragmentManager(), response.getString("error")).show();
				this.wantedDismiss = true;
		        this.progress.dismiss();
			}
		} catch(JSONException e) {
			e.printStackTrace();
			this.fileHandler.deleteTemp("games");
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
	public void onMoleculeResponse(String[] data) {}

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
                                    this.fileHandler.createFileTemp(ids.get(0) + ".jpg"));
        }
	}
	
	private void createCategories() {
    	//Until API has categories, we create a fake one
        this.categories = new SelectionItem[1];
        this.categories[0] = new SelectionItem(null, "No Categories");
        
        ListView categoryListView = (ListView) findViewById(R.id.category_list);
        SelectionBaseAdapter listHandler = new SelectionBaseAdapter(this, categories);
        categoryListView.setAdapter(listHandler);
        categoryListView.setOnItemClickListener(this);
        
        this.wantedDismiss = true;
        this.progress.dismiss();
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
