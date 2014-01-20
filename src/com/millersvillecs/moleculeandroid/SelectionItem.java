package com.millersvillecs.moleculeandroid;

import android.graphics.Bitmap;

public class SelectionItem {
	
	private Bitmap image;
	private String title;
	
	public SelectionItem(Bitmap image, String title) {
		this.image = image;
		this.title = title;
	}
	
	public Bitmap getImage() {
		return this.image;
	}
	
	public String getDescription() {
		return this.title;
	}

}
