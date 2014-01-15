package com.millersvillecs.moleculeandroid.util.math;

/**
 * 
 * @author william gervasio
 *
 */
public class Vector2 implements Cloneable {

	public float x, y;

	public Vector2() {
		set(0, 0);
	}
	
	public Vector2(float x, float y) {
		set(x, y);
	}
	
	public Vector2 set(float x, float y) {
		this.x = x;
		this.y = y;

		return this;
	}
	
	public Vector2 set(Vector2 other) {
		return set(other.x, other.y);
	}

	public Vector2 add(float x, float y) {
		this.x += x;
		this.y += y;
		
		return this;
	}

	public Vector2 add(Vector2 other) {
		return add(other.x, other.y);
	}

	public Vector2 sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		
		return this;
	}
	
	public Vector2 sub(Vector2 other) {
		return sub(other.x, other.y);
	}

	public Vector2 mul(float x, float y) {
		this.x *= x;
		this.y *= y;
		
		return this;
	}

	public Vector2 mul(Vector2 other) {
		return mul(other.x, other.y);
	}

	public Vector2 div(float x, float y) {
		this.x /= x;
		this.y /= y;
		
		return this;
	}

	public Vector2 div(Vector2 other) {
		return div(other.x, other.y);
	}

	public Vector2 scale(float scalar) {
		return mul(scalar, scalar);
	}

	public float dot(Vector2 other) {
		return (x * other.x) + (y * other.y);
	}

	public float length() {
		return (float) Math.sqrt(length2());
	}

	public float length2() {
		return x * x + y * y;
	}

	public Vector2 invert() {	
		return (Vector2) set(-x, -y);
	}
	
	public Vector2 normalize() {
		float length = length();
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vector2 rotate(Vector2 axis, double angle) {
		this.sub(axis);
		this.rotate(angle);
		this.add(axis);

		return this;
	}
	
	public Vector2 rotate(double angle) {
		float xh = x;
		float yh = y;
		
		x = (float) (xh * Math.cos(angle) - (yh * Math.sin(angle)));
		y = (float) (xh * Math.sin(angle) + (yh * Math.cos(angle)));

		return this;
	}
	
	public Vector2 clone() {
		return new Vector2(x, y);
	}
	
	public Float[] getElements(){
		return new Float[] {x, y};
	}
}

