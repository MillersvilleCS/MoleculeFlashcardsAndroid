package com.millersvillecs.moleculeandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.millersvillecs.moleculeandroid.data.FileHandler;
import com.millersvillecs.moleculeandroid.data.MoleculeGamePreferences;

public class DescriptionActivity extends Activity {
	
	private int position;
	private JSONArray fullGameJSON;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MoleculeGamePreferences preferences = new MoleculeGamePreferences(this);		
		this.position = preferences.getPosition();
		String gamesJSONText = preferences.getAllGamesJSON();
		
		setContentView(R.layout.activity_description);
		
		ActionBar actionBar = getActionBar();//no need to check, 4.0+ req on app
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		try{
			FileHandler fileHandler = new FileHandler(this);
			this.fullGameJSON = new JSONArray(gamesJSONText);
			JSONObject game = (JSONObject)this.fullGameJSON.get(this.position);
			actionBar.setTitle(game.getString("name"));
			
			String image = game.getString("id") + ".png";
			Bitmap bitmap = fileHandler.readTempImage(image);
			ImageView imageView = (ImageView) findViewById(R.id.description_image);
			imageView.setImageBitmap(bitmap);
			
			TextView description = (TextView)findViewById(R.id.game_description);
			description.setText(game.getString("description"));
			TextView time = (TextView)findViewById(R.id.time_limit);
			time.setText(time.getText() + " " + formatTime(game.getString("time_limit")));
			TextView numberOfQuestions = (TextView)findViewById(R.id.number_of_questions);
            numberOfQuestions.setText(numberOfQuestions.getText() + " " + game.getString("mol_count"));
		} catch(JSONException e) {
			e.printStackTrace();
			finish();
			return;
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
	
	public void onStartButton(View view) {
		Intent intent = new Intent(this, GameActivity.class);
	    startActivity(intent);
	}
	
	public void onHighScoresButton(View view) {		
		Intent intent = new Intent(this, HighScoreActivity.class);
	    startActivity(intent);
	}
	
	private String formatTime(String timeString) {
	    int time = Integer.parseInt(timeString);
	    time /= 1000;
	    int seconds = time % 60;
	    time /= 60;
	    String secondsString;
	    if(seconds < 10) {
	        secondsString = "0" + seconds;
	    } else {
	        secondsString = String.valueOf(seconds);
	    }
	    
	    return time + ":" + secondsString;
	}
}
