package com.millersvillecs.moleculeandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.millersvillecs.moleculeandroid.data.FileHandler;
import com.millersvillecs.moleculeandroid.helper.SelectionBaseAdapter;
import com.millersvillecs.moleculeandroid.helper.SelectionItem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SelectionActivity extends Activity implements OnItemClickListener {
	
	private SelectionItem[] games;
	private String username, auth;
	private JSONArray fullGameJSON;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		this.username = intent.getStringExtra(MainActivity.USERNAME);
		this.auth = intent.getStringExtra(MainActivity.AUTH);
		String gamesJSONText = intent.getStringExtra(MainActivity.GAME_JSON);
		
		FileHandler fileHandler = new FileHandler(this);
		
		setContentView(R.layout.activity_selection);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app
		 
		try{
		    this.fullGameJSON = new JSONArray(gamesJSONText);
			
			games = new SelectionItem[this.fullGameJSON.length()];
			for(int i = 0; i < this.fullGameJSON.length(); i++) {
				String image = ((JSONObject)this.fullGameJSON.get(i)).getString("id") + ".jpg";
				Bitmap bitmap = fileHandler.readTempImage(image);
				games[i] = new SelectionItem(bitmap, ((JSONObject)this.fullGameJSON.get(i)).getString("name") );
			}
		} catch(JSONException e) {
			e.printStackTrace();
			finish();
			return;
		}
		
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
		Intent intent = new Intent(this, DescriptionActivity.class);
	    intent.putExtra(MainActivity.USERNAME, this.username);
	    intent.putExtra(MainActivity.AUTH, this.auth);
	    intent.putExtra(MainActivity.GAME_INDEX, position);
	    intent.putExtra(MainActivity.GAME_JSON, this.fullGameJSON.toString());
	    startActivity(intent);
	}
}
