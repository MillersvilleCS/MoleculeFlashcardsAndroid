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
        
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float tnear = 1.0f;
        final float tfar = 10.0f;
        Matrix.frustumM(projection, 0, left, right, bottom, top, tnear, tfar);
        
        // Position the eye behind the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 1.5f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        Matrix.setLookAtM(view, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
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