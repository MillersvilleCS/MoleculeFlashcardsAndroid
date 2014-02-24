package com.millersvillecs.moleculeandroid.graphics;

import android.renderscript.Matrix4f;

import com.millersvillecs.moleculeandroid.util.math.Vector2;

public class Camera {

    private Matrix4f view, projection;
    private Vector2 translation;
    private float width, height, near, far;

    public Camera(float width, float height, float near, float far) {
        view = new Matrix4f();
        projection = new Matrix4f();
        translation = new Vector2();
        this.width = width;
        this.height = height;
        this.near = near;
        this.far = far;
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

    public Matrix4f getView() {
        return view;
    }

    public Matrix4f getProjection() {
        return projection;
    }
}