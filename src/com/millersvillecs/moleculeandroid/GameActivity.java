package com.millersvillecs.moleculeandroid;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;

import com.millersvillecs.moleculeandroid.graphics.opengles.BufferedObjectUsage;
import com.millersvillecs.moleculeandroid.graphics.opengles.Descriptor;
import com.millersvillecs.moleculeandroid.graphics.opengles.IBO;
import com.millersvillecs.moleculeandroid.graphics.opengles.VAO;
import com.millersvillecs.moleculeandroid.graphics.opengles.VBO;
import com.millersvillecs.moleculeandroid.util.BufferUtils;

public class GameActivity extends Activity{
	private GLSurfaceView mGLSurfaceView;
	private VAO vao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//SEE layouts over GL: http://www.java2s.com/Code/Android/UI/DemonstrationofoverlaysplacedontopofaSurfaceView.htm
		//SEE JSON parsing: http://stackoverflow.com/questions/9605913/how-to-parse-json-in-android
		
		setContentView(R.layout.activity_game);

		mGLSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);

		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

		if (supportsEs2) {
			mGLSurfaceView.setEGLContextClientVersion(2);
			mGLSurfaceView.setRenderer(new AndroidRenderer());
		} else {
			//error?
		}
	}

	public void load() { //not used?
		float[] vertices = {
				// Left bottom triangle
				-0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f,
				// Right top triangle

				0.5f, 0.5f, 0f,

		};

		FloatBuffer verticesBuffer = BufferUtils
				.createFloatBuffer(vertices.length);
		verticesBuffer.put(vertices);
		verticesBuffer.flip();
		VBO vbo = new VBO(verticesBuffer, BufferedObjectUsage.STATIC_DRAW);

		int[] indices = { 0, 1, 2, 0, 2, 3, };

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
