package com.millersvillecs.moleculeandroid.util.math;

/**
 * 
 * @author william gervasio
 * 
 */
public class Vector3 implements Cloneable {

	public float x, y, z;

	public Vector3() {
		set(0, 0, 0);
	}

	public Vector3(float x, float y, float z) {
		set(x, y, z);
	}

	public Vector3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;

		return this;
	}

	public Vector3 set(Vector3 other) {
		return set(other.x, other.y, other.z);
	}

	public Vector3 add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;

		return this;
	}

	public Vector3 add(Vector3 other) {
		return add(other.x, other.y, other.z);
	}

	public Vector3 sub(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;

		return this;
	}

	public Vector3 sub(Vector3 other) {
		return sub(other.x, other.y, other.z);
	}

	public Vector3 mul(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;

		return this;
	}

	public Vector3 mul(Vector3 other) {
		return mul(other.x, other.y, other.z);
	}

	public Vector3 div(float x, float y, float z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;

		return this;
	}

	public Vector3 div(Vector3 other) {
		return div(other.x, other.y, other.z);
	}

	public Vector3 scale(float scalar) {
		return mul(scalar, scalar, scalar);
	}

	public float dot(Vector3 other) {
		return (x * other.x) + (y * other.y + (z * other.z));
	}

	public float length() {
		return (float) Math.sqrt(length2());
	}

	public float length2() {
		return (float) (x * x + y * y + z * z);
	}

	public Vector3 invert() {
		return set(-x, -y, -z);
	}

	public Vector3 inverse() {
		return clone().invert();
	}
	
	public Vector3 normalize() {
		float length = length();
		x /= length;
		y /= length;
		z /= length;
		return this;
	}

	public Vector3 clone() {
		return new Vector3(x, y, z);
	}

	public Float[] getElements() {
		return new Float[] { x, y, z };
	}
}
