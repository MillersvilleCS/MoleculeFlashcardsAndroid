package com.millersvillecs.moleculeandroid;

import java.util.ArrayList;
import java.util.List;

import com.millersvillecs.moleculeandroid.graphics.Color;
import com.millersvillecs.moleculeandroid.graphics.Mesh;
import com.millersvillecs.moleculeandroid.graphics.VertexAttribute;

public class Cube extends Mesh{
	// Vertex positions
    private static final float VERTICES[] = { -0.5f, 0.5f, 0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f };

    // Vertex normals
    private static final float NORMALS[] = { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0,
            1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0,
            1, 0, 0, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0,
            -1, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, 0,
            1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, -1, 0, 0, -1,
            0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0 };

    // Vertex texture coordinates
    private static final float TEX_COORDS[] = { 0, 0, 0, 1, 1, 0, 1, 0, 0, 1,
            1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0,
            0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0,
            1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1 };

    // Primitive indices
    private static final int INDICES[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
            28, 29, 30, 31, 32, 33, 34, 35 };
    

    public Cube(Color color) {
    	super(INDICES, create(color));
    }
    
    private static List<VertexAttribute> create(Color color) {
    	List<VertexAttribute> meshDescriptor = new ArrayList<VertexAttribute>();
    	meshDescriptor.add(new VertexAttribute(VERTICES, 3));
    	meshDescriptor.add(new VertexAttribute(createColorArray(color), 4));
    	return meshDescriptor;
    }
    
    private static float[] createColorArray(Color color) {
    	float[] colors = new float[36 * 4];
    	for(int i =0; i < 36; ++i) {
    		colors[i * 4] = color.getRed();
    		colors[i * 4 + 1] = color.getGreen();
    		colors[i * 4 + 2] = color.getBlue();
    		colors[i * 4 + 3] = color.getAlpha();
    	}
    	
    	return colors;
    }
    
}
