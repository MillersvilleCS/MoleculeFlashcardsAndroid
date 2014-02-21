package com.millersvillecs.moleculeandroid;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class GameUIPieces {
    
    private Button[] buttons;
    private TextView questionText;
    private ScrollView scrollView;
    
    public GameUIPieces (GameActivity gameActivity) {        
        this.buttons = new Button[8];
        this.buttons[0] = (Button) gameActivity.findViewById(R.id.game_button_0);
        this.buttons[1] = (Button) gameActivity.findViewById(R.id.game_button_1);
        this.buttons[2] = (Button) gameActivity.findViewById(R.id.game_button_2);
        this.buttons[3] = (Button) gameActivity.findViewById(R.id.game_button_3);
        this.buttons[4] = (Button) gameActivity.findViewById(R.id.game_button_4);
        this.buttons[5] = (Button) gameActivity.findViewById(R.id.game_button_5);
        this.buttons[6] = (Button) gameActivity.findViewById(R.id.game_button_6);
        this.buttons[7] = (Button) gameActivity.findViewById(R.id.game_button_7);
        
        this.questionText = (TextView) gameActivity.findViewById(R.id.question_text);
        this.scrollView = (ScrollView) gameActivity.findViewById(R.id.question_scrollbar);
        //this.scrollView.setScrollBarStyle(ScrollView.SCROLLBARS_INSIDE_INSET);
        
        resetButtons();
        hideQuestionText();
    }
    
    public void resetButtons() {
        for(int i = 0; i < this.buttons.length; i++) {
            if(this.buttons[i].isShown()) {
                this.buttons[i].setVisibility(View.GONE);
                this.buttons[i].setTextColor(Color.BLACK);
                this.buttons[i].setEnabled(true);
                this.buttons[i].setAlpha(1.0f);
            }
        }
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
    
    public void markWrong(int index) {
        this.buttons[index].setTextColor(Color.RED);
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
}

