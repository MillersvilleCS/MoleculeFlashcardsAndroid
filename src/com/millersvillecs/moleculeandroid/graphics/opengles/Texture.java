package com.millersvillecs.moleculeandroid.graphics.opengles;

import java.nio.ByteBuffer;

import android.opengl.GLES20;

public class Texture {

	public static final int UNIT_DIFFUSE = GLES20.GL_TEXTURE0;
	public static final int BEHAVIOR_REPEAT = GLES20.GL_REPEAT;
	public static final int BEHAVIOR_CLAMP = GLES20.GL_CLAMP_TO_EDGE;

	private final int[] handleBuffer = new int[1];
	private final int unit, width, height;

	/**
	 * 
	 * @param width
	 *            The texture width in pixels
	 * @param height
	 *            The texture height in pixels
	 * @param buffer
	 *            The image data
	 * @param unit
	 * @param behavior
	 */
	public Texture(int width, int height, ByteBuffer buffer, int unit,
			int behavior) {
		this.unit = unit;
		this.width = width;
		this.height = height;

		// create the buffer
		GLES20.glGenTextures(1, handleBuffer, 0);
		// choose texture unit e.x. GLES20.GL_TEXTURE0
		GLES20.glActiveTexture(unit);
		// bind the newly created buffer as a 2D texture
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, getHandle());

		GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

		// set the x axis wrap behavior
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				behavior);
		// set the y axis wrap behavior
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				behavior);

		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, width,
				height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer);

		GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);

	}

	public void bind() {
		GLES20.glActiveTexture(unit);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, getHandle());
	}

	public void destroy() {
		GLES20.glDeleteTextures(1, handleBuffer, 0);
	}

	public int getHandle() {
		return handleBuffer[0];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}