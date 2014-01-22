package com.millersvillecs.moleculeandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.millersvillecs.moleculeandroid.data.FileHandler;
import com.millersvillecs.moleculeandroid.helper.ScoreBaseAdapter;
import com.millersvillecs.moleculeandroid.helper.ScoreItem;

public class HighScoreActivity extends Activity {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        int position = intent.getIntExtra(MainActivity.GAME_INDEX, -1);
        
        setContentView(R.layout.activity_high_score);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app
        
        FileHandler fileHandler = new FileHandler(this);
        String[] gamesJSONTextArray = fileHandler.readTemp("games");
        String gamesJSONText = "";
        for(int i = 0; i < gamesJSONTextArray.length; i++) {
            gamesJSONText += gamesJSONTextArray[i];
        }
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
