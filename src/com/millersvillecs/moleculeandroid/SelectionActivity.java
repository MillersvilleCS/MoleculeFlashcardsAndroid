package com.millersvillecs.moleculeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SelectionActivity extends Activity implements OnItemClickListener {
	
	private SelectionItem[] games;
	private String username, auth;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		this.username = intent.getStringExtra(MainActivity.USERNAME);
		this.auth = intent.getStringExtra(MainActivity.AUTH);
		
		setContentView(R.layout.activity_selection);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app
		
		//testing
		games = new SelectionItem[8];
		games[0] = new SelectionItem(null, "Game 1");
		games[1] = new SelectionItem(null, "Game 2");
		games[2] = new SelectionItem(null, "Game 3");
		games[3] = new SelectionItem(null, "Game 4");
		games[4] = new SelectionItem(null, "Game 5");
		games[5] = new SelectionItem(null, "Game 6");
		games[6] = new SelectionItem(null, "Game 7");
		games[7] = new SelectionItem(null, "Game 8");
		
		ListView gameListView = (ListView) findViewById(R.id.game_list);
		SelectionBaseAdapter listHandler = new SelectionBaseAdapter(this, games);
		gameListView.setAdapter(listHandler);
		gameListView.setOnItemClickListener(this);
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
		System.out.println(this.games[position].getDescription());
		/*
		Intent intent = new Intent(this, SelectionActivity.class);
	    intent.putExtra(MainActivity.USERNAME, this.username);
	    intent.putExtra(MainActivity.AUTH, this.auth);
	    startActivity(intent);
	    */
	}
}
