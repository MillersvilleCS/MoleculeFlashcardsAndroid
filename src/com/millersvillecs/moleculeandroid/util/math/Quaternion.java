package com.millersvillecs.moleculeandroid.util.math;

public class Quaternion {

	private float x, y, z, w;

	public Quaternion() {
		set(0, 0, 0, 0);
	}

	public Quaternion(float x, float y, float z, float w) {
		set(x, y, z, w);
	}

	public Quaternion multiply(Quaternion other) {
		float w = this.getW() * other.getW() - this.getX() * other.getX()
				- this.getY() * other.getY() - this.getZ() * other.getZ();
		float x = this.getW() * other.getX() + this.getX() * other.getW()
				+ this.getY() * other.getZ() - this.getZ() * other.getY();
		float y = this.getW() * other.getY() - this.getX() * other.getZ()
				+ this.getY() * other.getW() + this.getZ() * other.getX();
		float z = this.getW() * other.getZ() + this.getX() * other.getY()
				- this.getY() * other.getX() + this.getZ() * other.getW();

		return set(x, y, z, w);
	}

	public Quaternion set(float x, float y, float z, float w) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setW(w);

		return this;
	}

	public Quaternion normalize() {
		float m = (float) Math.sqrt(x * x + y * y + z * z + w * w);

		x /= m;
		y /= m;
		z /= m;
		w /= m;

		return this;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}
}
