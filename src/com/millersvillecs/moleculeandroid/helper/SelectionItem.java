package com.millersvillecs.moleculeandroid.helper;

import android.graphics.Bitmap;

public class SelectionItem {
	
	private Bitmap image;
	private String title;
	private int gameID;
	
	public SelectionItem(Bitmap image, String title, int gameID) {
		this.image = image;
		this.title = title;
		this.gameID = gameID;
	}
	
	public Bitmap getImage() {
		return this.image;
	}
	
	public String getDescription() {
		return this.title;
	}
	
	public int getGameID() {
		return this.gameID;
	}
}
