package com.millersvillecs.moleculeandroid;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.millersvillecs.moleculeandroid.graphics.opengles.BufferedObjectUsage;
import com.millersvillecs.moleculeandroid.graphics.opengles.Descriptor;
import com.millersvillecs.moleculeandroid.graphics.opengles.IBO;
import com.millersvillecs.moleculeandroid.graphics.opengles.VAO;
import com.millersvillecs.moleculeandroid.graphics.opengles.VBO;
import com.millersvillecs.moleculeandroid.util.BufferUtils;

public class GameActivity extends Activity{
	private GLSurfaceView gLSurfaceView;
	private VAO vao;
	private String username, auth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
        this.username = intent.getStringExtra(MainActivity.USERNAME);
        this.auth = intent.getStringExtra(MainActivity.AUTH);
        int position = intent.getIntExtra(MainActivity.GAME_INDEX, -1);
        String gamesJSONText = intent.getStringExtra(MainActivity.GAME_JSON);
		
		setContentView(R.layout.activity_game);
		getActionBar().setDisplayHomeAsUpEnabled(true);//no need to check, 4.0+ req on app

		this.gLSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);

		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

		if (supportsEs2) {
			this.gLSurfaceView.setEGLContextClientVersion(2);
			this.gLSurfaceView.setRenderer(new AndroidRenderer());
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
	
	/*
     * Handles 'Up' Menu press (does the same thing as the back button)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            //NavUtils.navigateUpFromSameTask(this);
            finish();//so "up" looks like "back"
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
