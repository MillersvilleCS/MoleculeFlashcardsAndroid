package com.millersvillecs.moleculeandroid.graphics.opengles;

import android.opengl.GLES20;

import java.util.Map;

import com.millersvillecs.moleculeandroid.util.GraphicsException;

public class ShaderProgram {

	private int handle;

	public ShaderProgram(String vertexShader, String fragmentShader,
			Map<Integer, String> attributes) throws GraphicsException {
		int vertexHandle, fragmentHandle;

		vertexHandle = compileShader(vertexShader, GLES20.GL_VERTEX_SHADER);
		fragmentHandle = compileShader(fragmentShader,
				GLES20.GL_FRAGMENT_SHADER);

		handle = GLES20.glCreateProgram();

		GLES20.glAttachShader(handle, vertexHandle);
		GLES20.glAttachShader(handle, fragmentHandle);

		if (attributes != null) {
			for (Map.Entry<Integer, String> e : attributes.entrySet()) {
				GLES20.glBindAttribLocation(handle, e.getKey(), e.getValue());
			}
		}

		// link the program and check for errors
		GLES20.glLinkProgram(handle);

		if (checkForCompileError(handle)) {
			throw new GraphicsException("Failed to link shader");
		}

		// detach shaders that aren't needed
		GLES20.glDetachShader(handle, vertexHandle);
		GLES20.glDetachShader(handle, fragmentHandle);
		GLES20.glDeleteShader(vertexHandle);
		GLES20.glDeleteShader(fragmentHandle);
	}

	public void bind() {
		GLES20.glUseProgram(handle);
	}

	public void unbind() {
		GLES20.glUseProgram(0);
	}

	public void destroy() {
		GLES20.glDeleteProgram(handle);
	}

	public int getUniformLocation(String string) {
		return GLES20.glGetUniformLocation(handle, string);
	}

	private int compileShader(String shader, int type) throws GraphicsException {

		int handle = GLES20.glCreateShader(type);
		GLES20.glShaderSource(handle, shader);
		GLES20.glCompileShader(handle);

		if (checkForCompileError(handle)) {
			switch (type) {
			case GLES20.GL_VERTEX_SHADER:
				throw new GraphicsException(
						"Failed to compiling vertex shader: " + shader);
			case GLES20.GL_FRAGMENT_SHADER:
				throw new GraphicsException(
						"Failed to compiling fragment shader: " + shader);
			}
		}

		return handle;
	}

	/**
	 * @param handle
	 *            The handle of the shader to check
	 * @return True if there is a compile error
	 */
	private boolean checkForCompileError(int handle) {

		int[] linkStatus = new int[1];
		GLES20.glGetShaderiv(handle, GLES20.GL_COMPILE_STATUS, linkStatus, 0);

		return linkStatus[0] == GLES20.GL_FALSE;
	}
}