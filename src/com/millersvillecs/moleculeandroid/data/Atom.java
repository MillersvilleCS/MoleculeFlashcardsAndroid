package com.millersvillecs.moleculeandroid.data;

import com.millersvillecs.moleculeandroid.graphics.Color;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

public class Atom {

	private String type;
    private Vector3 position;
    private Color color;
    private float radius;
    
    public Atom(float x, float y, float z, String type) {
    	setType(type);
    	this.position = new Vector3(x, y, z);
    	this.color = new Color(1,1,1);
    	this.radius = 1.5f;
    }
    
    public Atom(float x, float y, float z, String type , float radius) {
    	setType(type);
    	this.position = new Vector3(x, y, z);
    	this.color = new Color(1,1,1);
    	this.radius = radius;
    }
    
	public void setType(String type) {
    	this.type = type.replace(" ", "").replace("\r\n", "").replace("\n", "").toUpperCase();
    }
    
    public String getType() {
    	return type;
    }
    
    public Vector3 getPosition() {
    	return position;
    }
    
    public Color getColor() {
    	return color;
    }
    
    public void setColor(Color color) {
    	this.color = color;
    }
    
    public float getRadius() {
    	return radius;
    }
    
    public void setRadius(float radius) {
    	this.radius = radius;
    }
    
    public void setPosition(Vector3 position) {
    	this.position = position;
    }
    
    public float getX() {
    	return position.x;
    }
    
    public float getY() {
    	return position.y;
    }
    
    public float getZ() {
    	return position.z;
    }
}