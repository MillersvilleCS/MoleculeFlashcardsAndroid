package com.millersvillecs.moleculeandroid.helper;

public class CategoryItem {
	
	private String title, description;
	private int id;
	
	public CategoryItem(String title, String description, int id) {
		this.title = title;
		this.description = description;
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getID() {
		return this.id;
	}

}
