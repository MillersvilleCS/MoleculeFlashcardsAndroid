package com.millersvillecs.moleculeandroid.helper;

/**
 * 
 * @author connor
 * 
 * Convenience class for storing answers to the game
 * 
 */
public class Answer {
    
    private String id, text;
    
    public Answer(String id, String text) {
        this.id = id;
        this.text = text;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getText() {
        return this.text;
    }
}
