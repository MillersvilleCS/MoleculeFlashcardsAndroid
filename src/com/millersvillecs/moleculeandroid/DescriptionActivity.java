package com.millersvillecs.moleculeandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.millersvillecs.moleculeandroid.data.FileHandler;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DescriptionActivity extends Activity {
	
	private String username, auth;
	private int position;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		this.username = intent.getStringExtra(MainActivity.USERNAME);
		this.auth = intent.getStringExtra(MainActivity.AUTH);
		this.position = intent.getIntExtra(MainActivity.GAME_INDEX, -1);
		
		setContentView(R.layout.activity_description);
		
		ActionBar actionBar = getActionBar();//no need to check, 4.0+ req on app
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		FileHandler fileHandler = new FileHandler(this);
		String[] gamesJSONTextArray = fileHandler.readTemp("games");
		String gamesJSONText = "";
		for(int i = 0; i < gamesJSONTextArray.length; i++) {
			gamesJSONText += gamesJSONTextArray[i];
		}
		try{
			JSONArray gamesJSON = new JSONArray(gamesJSONText);
			JSONObject game = (JSONObject)gamesJSON.get(this.position);
			actionBar.setTitle(game.getString("name"));
			TextView description = (TextView)findViewById(R.id.game_description);
			description.setText(game.getString("description"));
			TextView time = (TextView)findViewById(R.id.time_limit);
			time.setText(time.getText() + " " + formatTime(game.getString("time_limit")));
			TextView numberOfQuestions = (TextView)findViewById(R.id.number_of_questions);
            numberOfQuestions.setText(numberOfQuestions.getText() + " " + game.getString("q_count"));
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
	    intent.putExtra(MainActivity.USERNAME, this.username);
	    intent.putExtra(MainActivity.AUTH, this.auth);
	    intent.putExtra(MainActivity.GAME_INDEX, this.position);
	    startActivity(intent);
	}
	
	public void onHighScoresButton(View view) {
		Intent intent = new Intent(this, HighScoreActivity.class);
	    intent.putExtra(MainActivity.GAME_INDEX, this.position);
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
