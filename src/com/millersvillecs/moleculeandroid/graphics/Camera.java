package com.millersvillecs.moleculeandroid.graphics;

import android.opengl.Matrix;

import com.millersvillecs.moleculeandroid.util.math.Vector3;

public class Camera {

    private float[] view = new float[16];
    private float[] projection = new float[16];
    private Vector3 translation;

    public Camera(float width, float height, float near, float far) {
        translation = new Vector3();
        
        float ratio = (float) width / height;
        
        Matrix.frustumM(projection, 0, -ratio, ratio, -1, 1, near, far);

    }
    
    public void lookAt(float x, float y, float z) {
    	Matrix.setLookAtM(view, 0, translation.x, translation.y, translation.z, x, y, z, 0f, 1.0f, 0.0f);
    }

    public Camera setTranslation(Vector3 translation) {
        this.translation = translation;

        return this;
    }

    public Camera setTranslation(float x, float y, float z) {
        translation = new Vector3(x, y, z);

        return this;
    }

    public Camera translate(Vector3 translation) {
        this.translation.add(translation);

        return this;
    }

    public Camera translate(float x, float y, float z) {
        translation.add(x, y, z);

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