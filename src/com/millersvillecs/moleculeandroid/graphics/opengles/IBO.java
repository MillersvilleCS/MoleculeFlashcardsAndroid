package com.millersvillecs.moleculeandroid.graphics.opengles;

import java.nio.IntBuffer;

import com.millersvillecs.moleculeandroid.util.DatatypeUtils;

import android.opengl.GLES20;

/**
 * 
 * @author William Gervasio
 *
 */
public class IBO {
	
	private final int[] handleBuffer = new int[1];
	
	public IBO(IntBuffer buffer, int usage) {
		//create the buffer and grab the handle
		GLES20.glGenBuffers(1, handleBuffer, 0); 
		bind();
		//add passed data to the buffer
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, buffer.limit() * DatatypeUtils.INTEGER_SIZE_BYTES, buffer, usage);
		unbind();
	}
	
	public final void bind() {
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, getHandle());
	}
	
	public final void unbind() {
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public final void destroy() {
		GLES20.glDeleteBuffers(1, handleBuffer, 0);
	}
	
	public final int getHandle() {
		return handleBuffer[0];
	}
}