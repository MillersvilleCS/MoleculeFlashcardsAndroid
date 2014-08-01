package com.millersvillecs.moleculeandroid.helper;

/**
 * 
 * @author connor
 * 
 * Convenience class for storing a single high score item.
 * 
 */
public class ScoreItem {
    
    private String rank, username, score;
    
    public ScoreItem(String rank, String username, String score) {
        this.rank = rank;
        this.username = username;
        this.score = score;
    }
    
    public String getRank() {
        return this.rank;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getScore() {
        return this.score;
    }

}
