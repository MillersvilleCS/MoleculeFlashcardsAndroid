package com.millersvillecs.moleculeandroid.graphics.opengles;

import android.opengl.GLES20;
import android.util.SparseArray;

import com.millersvillecs.moleculeandroid.util.GraphicsException;

public class ShaderProgram {

	private int handle;

	public ShaderProgram(String vertexShader, String fragmentShader,
			SparseArray<String> attributes) throws GraphicsException {

		int vertexHandle = compileShader(vertexShader, GLES20.GL_VERTEX_SHADER);
		int fragmentHandle = compileShader(fragmentShader, GLES20.GL_FRAGMENT_SHADER);

		handle = GLES20.glCreateProgram();

		GLES20.glAttachShader(handle, vertexHandle);
		GLES20.glAttachShader(handle, fragmentHandle);

		if (attributes != null) {
			for (int i = 0; i < attributes.size(); i++) {
				GLES20.glBindAttribLocation(handle, i, attributes.get(i));
			}
		}

		// link the program and check for errors
		GLES20.glLinkProgram(handle);

		if (checkForCompileError(handle)) {
			throw new GraphicsException("Failed to link shader");
		}

		// detach shaders that aren't needed
		//GLES20.glDetachShader(handle, vertexHandle);
		//GLES20.glDetachShader(handle, fragmentHandle);
		//GLES20.glDeleteShader(vertexHandle);
		//GLES20.glDeleteShader(fragmentHandle);
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
		if (handle == 0)
		{
			throw new GraphicsException(
					"Failed to create shader handle ");
		}
		GLES20.glShaderSource(handle, shader);
		GLES20.glCompileShader(handle);

		// Get the compilation status.
	    final int[] compileStatus = new int[1];
	    GLES20.glGetShaderiv(handle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
	 
	    // If the compilation failed, delete the shader.
	    if (compileStatus[0] == 0)
	    {
	        GLES20.glDeleteShader(handle);
	        throw new GraphicsException("Failed to create Shader");
	    }

		return handle;
	}

	/**
	 * @param handle
	 *            The handle of the shader to check
	 * @return True if there is a compile error
	 */
	private boolean checkForCompileError(int handle) {

		final int[] linkStatus = new int[1];
	    GLES20.glGetProgramiv(handle, GLES20.GL_LINK_STATUS, linkStatus, 0);
	 
	    // If the link failed, delete the program.
	    if (linkStatus[0] == 0)
	    {
	        GLES20.glDeleteProgram(handle);
	        handle = 0;
	        
	        return true;
	    }

		return false;
	}
}