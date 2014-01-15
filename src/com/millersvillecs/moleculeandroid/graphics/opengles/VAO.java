package com.millersvillecs.moleculeandroid.graphics.opengles;

import java.util.HashMap;
import java.util.Map;

import android.opengl.GLES20;

public class VAO {
	private final Map<Integer, Descriptor> descriptors = new HashMap<Integer, Descriptor>();

	private final VBO vbo;
	private final IBO ibo;
	private final int handle, size;

	public VAO(VBO vbo, IBO ibo, int size) {
		this.vbo = vbo;
		this.ibo = ibo;
		this.size = size;

		handle = 0;//GLES20.glGenVertexArrays();
	}

	public final void init() {
		//GLES20.glBindVertexArray(handle);

		vbo.bind();

		for (final int i : descriptors.keySet()) {

			final Descriptor descriptor = descriptors.get(i);

			GLES20.glEnableVertexAttribArray(i);
			GLES20.glVertexAttribPointer(i, descriptor.getSize(),
					descriptor.getType(), descriptor.isNormalized(),
					descriptor.getStride(), descriptor.getPointer());

		}
		
		ibo.bind();
		//GLES20.glBindVertexArray(0);
	}

	public final void addVertexAttribute(final int index,
			final Descriptor descriptor) {
		descriptors.put(index, descriptor);
	}

	public final void draw() {
		//GLES20.glBindVertexArray(handle);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, size, GLES20.GL_UNSIGNED_INT, 0);
	}
}
