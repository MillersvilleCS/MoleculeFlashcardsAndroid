package com.millersvillecs.moleculeandroid;

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

public class CategoryActivity extends Activity implements OnItemClickListener, OnDismissListener {
	
	private SelectionItem[] categories;
	private ProgressDialog progress;
	private String username, auth;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		this.username = intent.getStringExtra(MainActivity.USERNAME);
		this.auth = intent.getStringExtra(MainActivity.AUTH);
		
		setContentView(R.layout.activity_category);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app
		
		/*
		this.progress = new ProgressDialog(this);
		this.progress.setCanceledOnTouchOutside(false);
		this.progress.setMessage("Loading Categories...");
		this.progress.setOnDismissListener(this);
		this.progress.show();
		*/
		
		//testing with dummy data, can click off "loading" screen to play with it
		this.categories = new SelectionItem[8];
		this.categories[0] = new SelectionItem(null, "Category 1");
		this.categories[1] = new SelectionItem(null, "Category 2");
		this.categories[2] = new SelectionItem(null, "Category 3");
		this.categories[3] = new SelectionItem(null, "Category 4");
		this.categories[4] = new SelectionItem(null, "Category 5");
		this.categories[5] = new SelectionItem(null, "Category 6");
		this.categories[6] = new SelectionItem(null, "Category 7");
		this.categories[7] = new SelectionItem(null, "Category 8");
		
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
		//if use hits back on loading screen, go back up - removed for testing
		//finish();
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
