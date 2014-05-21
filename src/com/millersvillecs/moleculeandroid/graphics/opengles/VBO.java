package com.millersvillecs.moleculeandroid.graphics.opengles;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.millersvillecs.moleculeandroid.util.DatatypeUtils;

public class VBO {

	private final int[] handleBuffer = new int[1];

	public VBO(FloatBuffer buffer, int usage) {

		// create the buffer and grab the handle
		GLES20.glGenBuffers(1, handleBuffer, 0);
		// bind the newly created buffer
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, getHandle());
		// add passed data to the buffer
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, buffer.limit()
				* DatatypeUtils.FLOAT_SIZE_BYTES, buffer, usage);
		// unbind the buffer
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public void bind() {
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, getHandle());
	}
	
	public void unbind() {
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
	}

	public void destroy() {
		GLES20.glDeleteBuffers(1, handleBuffer, 0);
	}

	public int getHandle() {
		return handleBuffer[0];
	}
}
