package com.millersvillecs.moleculeandroid.graphics;

import java.util.ArrayList;
import java.util.List;

import com.millersvillecs.moleculeandroid.util.math.Vector3;

public class GeometryUtils {

	public static Mesh createCubeGeometry(final Color color) {
		final float vertices[] = { -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
				0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
				0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f,
				0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f,
				0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f,
				0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f,
				0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
				0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
				0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
				-0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f,
				-0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f,
				-0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f };

		// Vertex normals
		/*
		final float normals[] = { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
				0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0,
				0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, -1,
				0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, 0, 1,
				0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, -1, 0, 0,
				-1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0 };
		*/
		// Vertex texture coordinates
		/*
		final float texCoords[] = { 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0,
				0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1,
				1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0,
				0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1 };
		*/
		// Primitive indices
		final int indices[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
				14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
				30, 31, 32, 33, 34, 35 };

		float[] colors = new float[36 * 4];
		for (int i = 0; i < 36; ++i) {
			colors[i * 4] = color.getRed();
			colors[i * 4 + 1] = color.getGreen();
			colors[i * 4 + 2] = color.getBlue();
			colors[i * 4 + 3] = color.getAlpha();
		}

		List<VertexAttribute> meshDescriptor = new ArrayList<VertexAttribute>();
		meshDescriptor.add(new VertexAttribute(vertices, 3));
		meshDescriptor.add(new VertexAttribute(colors, 4));

		return new Mesh(indices, meshDescriptor);
	}

	public static Mesh createSphereGeometry(final float radius,
			final int hSegments, final int vSegments, final Color color) {

		List<Vector3> positions = new ArrayList<Vector3>();
		List<Color> colors = new ArrayList<Color>();

		//create vertices
		int numVerticesPerRow = hSegments + 1;
		int numVerticesPerColumn = vSegments + 1;

		//int numVertices = numVerticesPerRow * numVerticesPerColumn;

		float theta = 0.0f;
		float phi = 0.0f;

		float verticalAngularStride = (float) Math.PI / (float) vSegments;
		float horizontalAngularStride = ((float) Math.PI * 2)
				/ (float) hSegments;

		for (int verticalIt = 0; verticalIt < numVerticesPerColumn; verticalIt++) {
			// beginning on top of the sphere:
			theta = ((float) Math.PI / 2.0f) - verticalAngularStride
					* verticalIt;

			for (int horizontalIt = 0; horizontalIt < numVerticesPerRow; horizontalIt++) {
				phi = horizontalAngularStride * horizontalIt;

				// position
				float x = radius * (float) Math.cos(theta)
						* (float) Math.cos(phi);
				float y = radius * (float) Math.cos(theta)
						* (float) Math.sin(phi);
				float z = radius * (float) Math.sin(theta);

				Vector3 position = new Vector3(x, z, y);
				positions.add(position);
				colors.add(color.clone());
			}
		}
		
		//create indices
		//int numIndices = hSegments * vSegments * 6;
		List<Integer> indices = new ArrayList<Integer>();

		for (int verticalIt = 0; verticalIt < vSegments; verticalIt++)
		{
		    for (int horizontalIt = 0; horizontalIt < hSegments; horizontalIt++)
		    {
		    	int lt = (int)(horizontalIt + verticalIt * (numVerticesPerRow));
		    	int rt = (int)((horizontalIt + 1) + verticalIt * (numVerticesPerRow));

		    	int lb = (int)(horizontalIt + (verticalIt + 1) * (numVerticesPerRow));
		    	int rb = (int)((horizontalIt + 1) + (verticalIt + 1) * (numVerticesPerRow));
		     
		        indices.add(lt);
		        indices.add(rt);
		        indices.add(lb);

		        indices.add(rt);
		        indices.add(rb);
		        indices.add(lb);
		    }
		}
		
		List<VertexAttribute> meshDescriptor = new ArrayList<VertexAttribute>();
		meshDescriptor.add(new VertexAttribute(VertexUtils
				.vector3ListToFloatArray(positions), 3));
		meshDescriptor.add(new VertexAttribute(VertexUtils
				.ColorListToFloatArray(colors), 4));
		return new Mesh(VertexUtils.IntegerListToIntArray(indices),
				meshDescriptor);
	}
	
	public static Mesh createCylinderGeometry(final float radius, float height, int resolution,  final Color color1, Color color2) {
		
		float perR = (float) (2 * Math.PI / (resolution / 2 - 1));
		List<Vector3> positions = new ArrayList<Vector3>();
		List<Color> colors = new ArrayList<Color>();
		List<Integer> indices = new ArrayList<Integer>();
		
	    for (int i = 0; i < resolution; i += 2) {
	    	float vert1 = (float) (radius * Math.cos(i / 2 * perR));
	    	float vert2 = (float) (radius * Math.sin(i / 2 * perR));
	    	positions.add(new Vector3(vert1, height, vert2));
	    	positions.add(new Vector3(vert1, 0, vert2));
	    	colors.add(color2);
	    	colors.add(color1);
	    	
	    	
	    	if(i+2 < resolution) {
	    		indices.add(i);
	    		indices.add(i + 1);
	    		indices.add(i + 2);
	    		
	    		indices.add(i + 1);
	    		indices.add(i + 2);
	    		indices.add(i + 3);
	    	}
	    }
	    //indices.add(0);
    	//indices.add(1);
    	
    	List<VertexAttribute> meshDescriptor = new ArrayList<VertexAttribute>();
		meshDescriptor.add(new VertexAttribute(VertexUtils
				.vector3ListToFloatArray(positions), 3));
		meshDescriptor.add(new VertexAttribute(VertexUtils
				.ColorListToFloatArray(colors), 4));
		return new Mesh(VertexUtils.IntegerListToIntArray(indices),
				meshDescriptor);
		
	}
}
