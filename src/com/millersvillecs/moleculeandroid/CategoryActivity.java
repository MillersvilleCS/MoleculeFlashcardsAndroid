package com.millersvillecs.moleculeandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CategoryActivity extends Activity implements OnItemClickListener, 
														  OnDismissListener, OnCommunicationListener {
	
	private SelectionItem[] categories;
	private ProgressDialog progress;
	private String username, auth;
	private boolean wantedDismiss = false;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		this.username = intent.getStringExtra(MainActivity.USERNAME);
		this.auth = intent.getStringExtra(MainActivity.AUTH);
		
		setContentView(R.layout.activity_category);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app
		
		CommunicationManager comm = new CommunicationManager(this);
		comm.availableGames(this.auth);
		
		this.progress = new ProgressDialog(this);
		this.progress.setCanceledOnTouchOutside(false);
		this.progress.setMessage("Loading Categories...");
		this.progress.setOnDismissListener(this);
		this.progress.show();
		
		//Until API has categories, we create a fake one
		this.categories = new SelectionItem[1];
		this.categories[0] = new SelectionItem(null, "No Categories");
		
		ListView categoryListView = (ListView) findViewById(R.id.category_list);
		SelectionBaseAdapter listHandler = new SelectionBaseAdapter(this, categories);
		categoryListView.setAdapter(listHandler);
		categoryListView.setOnItemClickListener(this);
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
		FileHandler fileHandler = new FileHandler(this);
		try{
			if(response.getBoolean("success")) {
				JSONArray avail = response.getJSONArray("available_games");
				fileHandler.writeTemp("games", avail.toString().split("\n"));
				this.wantedDismiss = true;
				this.progress.dismiss();
			} else {
				fileHandler.deleteTemp("games");
				new ErrorDialog(getFragmentManager(), response.getString("error")).show();
			}
		} catch(JSONException e) {
			e.printStackTrace();
			fileHandler.deleteTemp("games");
			new ErrorDialog(getFragmentManager(), "Invalid Server Response").show();
		}
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
