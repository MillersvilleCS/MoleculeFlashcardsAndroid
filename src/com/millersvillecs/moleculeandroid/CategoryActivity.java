package com.millersvillecs.moleculeandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CategoryActivity extends Activity implements OnItemClickListener, OnDismissListener {
	
	private SelectionItem[] categories;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_category);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app
		
		ProgressDialog progress = new ProgressDialog(this);
		progress.setCanceledOnTouchOutside(true); //temp, set to false in production
		progress.setMessage("Loading Categories...");
		progress.setOnDismissListener(this);
		progress.show();
		
		//load categories over SSL, parse JSON, create SelectionItem's
		//HTTPS POST on Android? http://stackoverflow.com/questions/11504467/how-to-https-post-in-android
		
		//testing with dummy data, can click off "loading" screen to play with it
		categories = new SelectionItem[8];
		categories[0] = new SelectionItem(null, "Test 1");
		categories[1] = new SelectionItem(null, "Test 2");
		categories[2] = new SelectionItem(null, "Test 3");
		categories[3] = new SelectionItem(null, "Test 4");
		categories[4] = new SelectionItem(null, "Test 5");
		categories[5] = new SelectionItem(null, "Test 6");
		categories[6] = new SelectionItem(null, "Test 7");
		categories[7] = new SelectionItem(null, "Test 8");
		
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
		System.out.println(categories[position].getDescription());
		//spawn activity to pick game from category
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
