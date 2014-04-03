package com.millersvillecs.moleculeandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.millersvillecs.moleculeandroid.data.FileHandler;
import com.millersvillecs.moleculeandroid.data.MoleculeGamePreferences;
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
	
	private MoleculeGamePreferences preferences;
	private SelectionItem[] games;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.preferences = new MoleculeGamePreferences(this);
		String gamesJSONText = this.preferences.getCategoricalGamesJSON();
		
		FileHandler fileHandler = new FileHandler(this);
		
		setContentView(R.layout.activity_selection);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app
		 
		try{
		    JSONArray partialJSON = new JSONArray(gamesJSONText);
			
			games = new SelectionItem[partialJSON.length()];
			for(int i = 0; i < partialJSON.length(); i++) {
				JSONObject game = partialJSON.getJSONObject(i);
				String image = game.getString("id") + ".png";
				Bitmap bitmap = fileHandler.readTempImage(image);
				games[i] = new SelectionItem(bitmap, game.getString("name"), 
											 game.getString("description"), game.getInt("id") );
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
		try{
			JSONArray fullGameJSON = new JSONArray(this.preferences.getAllGamesJSON());
			int gameID = this.games[position].getGameID();
			position = -1;
			for(int i = 0; i < fullGameJSON.length(); i++) {
				if(fullGameJSON.getJSONObject(i).getInt("id") == gameID) {
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
	    
	    Intent intent = new Intent(this, DescriptionActivity.class);
	    startActivity(intent);
	}
}
