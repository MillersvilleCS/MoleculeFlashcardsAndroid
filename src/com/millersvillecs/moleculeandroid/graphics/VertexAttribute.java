package com.millersvillecs.moleculeandroid.graphics;

public class VertexAttribute {
    private float[] data;
    private int elementsPerVertex;

    public VertexAttribute(float[] data, int elementsPerVertex) {
        this.data = data;
        this.elementsPerVertex = elementsPerVertex;
    }

    public float[] getData() {
        return data;
    }

    public int getElementsPerVertex() {
        return elementsPerVertex;
    }
}