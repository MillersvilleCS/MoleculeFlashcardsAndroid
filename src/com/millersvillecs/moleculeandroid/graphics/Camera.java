package com.millersvillecs.moleculeandroid.graphics;

import android.opengl.Matrix;
import android.renderscript.Matrix4f;

import com.millersvillecs.moleculeandroid.util.math.Vector2;

public class Camera {

    private float[] view = new float[16];
    private float[] projection = new float[16];
    private Vector2 translation;
    private float width, height, near, far;

    public Camera(float width, float height, float near, float far) {
        translation = new Vector2();
        this.width = width;
        this.height = height;
        this.near = near;
        this.far = far;
        
        float ratio = (float) width / height;
        
        Matrix.frustumM(projection, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.setLookAtM(view, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }

    public Camera setTranslation(Vector2 translation) {
        this.translation = translation;

        return this;
    }

    public Camera setTranslation(float x, float y) {
        translation = new Vector2(x, y);

        return this;
    }

    public Camera translate(Vector2 translation) {
        this.translation.add(translation);

        return this;
    }

    public Camera translate(float x, float y) {
        translation.add(x, y);

        return this;
    }

    public float[] getView() {
        return view;
    }

    public float[] getProjection() {
        return projection;
    }
    
    public float[] getViewProjection() {
    	float[] temp = new float[16];
    	Matrix.multiplyMM(temp, 0, projection, 0, view, 0);
    	return temp;
    }
}