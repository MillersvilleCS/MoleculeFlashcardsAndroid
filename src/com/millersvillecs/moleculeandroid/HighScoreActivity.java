package com.millersvillecs.moleculeandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.millersvillecs.moleculeandroid.data.MoleculeGamePreferences;
import com.millersvillecs.moleculeandroid.helper.ScoreBaseAdapter;
import com.millersvillecs.moleculeandroid.helper.ScoreItem;

public class HighScoreActivity extends Activity {
	
	/**
	 * Initialize our list view from the JSON stored in our Game Preferences object.
	 */
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        MoleculeGamePreferences preferences = new MoleculeGamePreferences(this);
        int position = preferences.getPosition();
        String gamesJSONText = preferences.getAllGamesJSON();
        
        setContentView(R.layout.activity_high_score);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app
        
        ScoreItem[] scores;
        try{
            JSONArray gamesJSON = new JSONArray(gamesJSONText);
            JSONObject game = gamesJSON.getJSONObject(position);
            JSONArray highScoresJSON = game.getJSONArray("high_scores");
            scores = new ScoreItem[highScoresJSON.length()]; 
            for(int i = 0; i < highScoresJSON.length(); i++) {
                scores[i] = new ScoreItem("#" + highScoresJSON.getJSONObject(i).getString("rank"),
                                          highScoresJSON.getJSONObject(i).getString("username"),
                                          highScoresJSON.getJSONObject(i).getString("score"));
            }
            
        } catch(JSONException e) {
            e.printStackTrace();
            finish();
            return;
        }
        
        ListView highScores = (ListView) findViewById(R.id.high_score_list);
        ScoreBaseAdapter listHandler = new ScoreBaseAdapter(this, scores);
        highScores.setAdapter(listHandler);
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
}
