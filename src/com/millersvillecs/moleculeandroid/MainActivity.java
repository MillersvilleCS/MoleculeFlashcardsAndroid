package com.millersvillecs.moleculeandroid;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.millersvillecs.moleculeandroid.graphics.opengles.BufferedObjectUsage;
import com.millersvillecs.moleculeandroid.graphics.opengles.Descriptor;
import com.millersvillecs.moleculeandroid.graphics.opengles.IBO;
import com.millersvillecs.moleculeandroid.graphics.opengles.VAO;
import com.millersvillecs.moleculeandroid.graphics.opengles.VBO;
import com.millersvillecs.moleculeandroid.util.BufferUtils;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.view.Menu;

public class MainActivity extends Activity {
	private GLSurfaceView mGLSurfaceView;
	private VAO vao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mGLSurfaceView = new GLSurfaceView(this);
		
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
	    final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
	 
	    if (supportsEs2)
	    {
	        // Request an OpenGL ES 2.0 compatible context.
	        mGLSurfaceView.setEGLContextClientVersion(2);
	 
	        // Set the renderer to our demo renderer, defined below.
	        //mGLSurfaceView.setRenderer(new LessonOneRenderer());
	    }
	    else
	    {
	        // This is where you could create an OpenGL ES 1.x compatible
	        // renderer if you wanted to support both ES 1 and ES 2.
	        return;
	    }
		
		setContentView(R.layout.activity_main);
	}
	
	public void load() {
		float[] vertices = {
				// Left bottom triangle
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				// Right top triangle
				
				0.5f, 0.5f, 0f,
				
		};
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
		verticesBuffer.put(vertices);
		verticesBuffer.flip();
		VBO vbo = new VBO(verticesBuffer, BufferedObjectUsage.STATIC_DRAW);
		
		int[] indices = {
				0,1,2,0,2,3,
		};
		
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
		indicesBuffer.put(indices);
		indicesBuffer.flip();
		
		IBO ibo = new IBO(indicesBuffer, BufferedObjectUsage.STATIC_DRAW);
		
		vao = new VAO(vbo, ibo, 6);
		Descriptor des = new Descriptor(3, GLES20.GL_FLOAT, false, 0, 0);
		vao.addVertexAttribute(0, des);
		vao.init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
