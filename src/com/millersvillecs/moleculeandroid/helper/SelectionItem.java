package com.millersvillecs.moleculeandroid.helper;

import android.graphics.Bitmap;

/**
 * 
 * @author connor
 * 
 * Convenience class for storing a Game Selection Item.
 * 
 */
public class SelectionItem {
	
	private Bitmap image;
	private String title, description;
	private int gameID;
	
	public SelectionItem(Bitmap image, String title, String description, int gameID) {
		this.image = image;
		this.title = title;
		this.description = description;
		this.gameID = gameID;
	}
	
	public Bitmap getImage() {
		return this.image;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getGameID() {
		return this.gameID;
	}
}
