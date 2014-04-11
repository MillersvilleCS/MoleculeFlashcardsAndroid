package com.millersvillecs.moleculeandroid;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class GameUIPieces {
	
	public static final int STATE_INVISIBLE = 0, STATE_UNCHOSEN = 1, STATE_WRONG = 2;
    
    private GameActivity gameActivity;
    private Button[] buttons;
    private TextView questionText;
    private ScrollView scrollView;
    private Animation animation;
    private Drawable defaultBackground, correctBackground, wrongBackground;
    
    public GameUIPieces (GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.buttons = new Button[8];
        this.buttons[0] = (Button) gameActivity.findViewById(R.id.game_button_0);
        this.buttons[1] = (Button) gameActivity.findViewById(R.id.game_button_1);
        this.buttons[2] = (Button) gameActivity.findViewById(R.id.game_button_2);
        this.buttons[3] = (Button) gameActivity.findViewById(R.id.game_button_3);
        this.buttons[4] = (Button) gameActivity.findViewById(R.id.game_button_4);
        this.buttons[5] = (Button) gameActivity.findViewById(R.id.game_button_5);
        this.buttons[6] = (Button) gameActivity.findViewById(R.id.game_button_6);
        this.buttons[7] = (Button) gameActivity.findViewById(R.id.game_button_7);
        
        this.defaultBackground = gameActivity.getResources().getDrawable(R.drawable.button_gray);
        this.correctBackground = gameActivity.getResources().getDrawable(R.drawable.button_green);
        this.wrongBackground = gameActivity.getResources().getDrawable(R.drawable.button_wrong);
        
        this.questionText = (TextView) gameActivity.findViewById(R.id.question_text);
        this.scrollView = (ScrollView) gameActivity.findViewById(R.id.question_scrollbar);
        //this.scrollView.setScrollBarStyle(ScrollView.SCROLLBARS_INSIDE_INSET);
        this.animation = AnimationUtils.loadAnimation(gameActivity, R.anim.button_pulse);
        
        resetButtons();
        hideQuestionText();
    }
    
    @SuppressWarnings("deprecation")
    public void resetButtons(boolean force) {
    	for(int i = 0; i < this.buttons.length; i++) {
            if(this.buttons[i].isShown() || force) {
                this.buttons[i].setVisibility(View.GONE);
                this.buttons[i].setEnabled(true);
                this.buttons[i].setBackgroundDrawable(this.defaultBackground);
                this.buttons[i].setAlpha(1.0f);
                this.buttons[i].clearAnimation();
            }
        }
    }
    
    public void resetButtons() {
        resetButtons(false);
    }
    
    public void displayButton(int index, String message) {
        this.buttons[index].setText(message);
        this.buttons[index].setVisibility(View.VISIBLE);
        if(index >= 6) {
            this.scrollView.setScrollbarFadingEnabled(false);
        } else {
            this.scrollView.setScrollbarFadingEnabled(true);
        }
    }
    
    public void markWorking(int index) {
    	this.buttons[index].startAnimation(this.animation);
    }
    
    @SuppressWarnings("deprecation")
	public void markCorrect(int index) {
    	this.buttons[index].setBackgroundDrawable(this.correctBackground);//new version is above our min-level
    	for(int i = 0; i < this.buttons.length; i++) {
    		if(i != index && this.buttons[i].isShown()) {
    			this.buttons[i].setEnabled(false);
    	        this.buttons[i].setAlpha(0.5f);
    		}
    	}
    }
    
    @SuppressWarnings("deprecation")
    public void markWrong(int index) {
    	this.buttons[index].clearAnimation();
    	this.buttons[index].setBackgroundDrawable(this.wrongBackground);
        this.buttons[index].setEnabled(false);
        this.buttons[index].setAlpha(0.5f);
    }
    
    public void hideQuestionText() {
        this.questionText.setVisibility(View.GONE);
    }
    
    public void displayQuestionText() {
        this.questionText.setVisibility(View.VISIBLE);
    }
    
    public void displayQuestionText(String message) {
        if(!message.equals("")) {
            this.questionText.setText(message);
            this.questionText.setVisibility(View.VISIBLE);
        }
    }
    
    public void displayFinishScreen(double score, int rank) {
        this.gameActivity.setContentView(R.layout.activity_game_finish);
        
        TextView scoreView = (TextView) this.gameActivity.findViewById(R.id.game_finish_score);
        TextView rankView = (TextView) this.gameActivity.findViewById(R.id.game_finish_rank);
        
        scoreView.setText("Score: " + score);
        rankView.setText("Rank: #" + rank);
    }
    
    public int[] getButtonStates() {
    	int[] states = new int[this.buttons.length];
    	for(int i = 0; i < states.length; i++) {
    		if(this.buttons[i].isShown()) {
    			if(this.buttons[i].isEnabled()) {
    				states[i] = GameUIPieces.STATE_UNCHOSEN;
    			} else {
    				states[i] = GameUIPieces.STATE_WRONG;
    			}
    		}
    	}
    	
    	return states;
    }
}

