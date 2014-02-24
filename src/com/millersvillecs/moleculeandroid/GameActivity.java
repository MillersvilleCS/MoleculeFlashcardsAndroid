package com.millersvillecs.moleculeandroid;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

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

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.opengles.BufferedObjectUsage;
import com.millersvillecs.moleculeandroid.graphics.opengles.Descriptor;
import com.millersvillecs.moleculeandroid.graphics.opengles.IBO;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.graphics.opengles.VAO;
import com.millersvillecs.moleculeandroid.graphics.opengles.VBO;
import com.millersvillecs.moleculeandroid.scene.Scene;
import com.millersvillecs.moleculeandroid.scene.SceneObject;
import com.millersvillecs.moleculeandroid.util.BufferUtils;
import com.millersvillecs.moleculeandroid.util.FileUtil;

public class GameActivity extends Activity{
	private GLSurfaceView gLSurfaceView;
	private VAO vao;
	private String username, auth;
	
	private Scene scene;
	private Camera camera;
	
	final String vertexShader =
		    "uniform mat4 u_MVPMatrix;      \n"     // A constant representing the combined model/view/projection matrix.
		 
		  + "attribute vec4 a_Position;     \n"     // Per-vertex position information we will pass in.
		  + "attribute vec4 a_Color;        \n"     // Per-vertex color information we will pass in.
		 
		  + "varying vec4 v_Color;          \n"     // This will be passed into the fragment shader.
		 
		  + "void main()                    \n"     // The entry point for our vertex shader.
		  + "{                              \n"
		  + "   v_Color = a_Color;          \n"     // Pass the color through to the fragment shader.
		                                            // It will be interpolated across the triangle.
		  + "   gl_Position = u_MVPMatrix   \n"     // gl_Position is a special variable used to store the final position.
		  + "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in
		  + "}                              \n";    // normalized screen coordinates.
						
	final String fragmentShader =
		    "precision mediump float;       \n"     // Set the default precision to medium. We don't need as high of a
		                                            // precision in the fragment shader.
		  + "varying vec4 v_Color;          \n"     // This is the color from the vertex shader interpolated across the
		                                            // triangle per fragment.
		  + "void main()                    \n"     // The entry point for our fragment shader.
		  + "{                              \n"
		  + "   gl_FragColor = v_Color;     \n"     // Pass the color directly through the pipeline.
		  + "}                              \n";

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
		
		
		
		/*
		 * Test render code
		 */
		Map<Integer, String> attributes = new HashMap<Integer, String>();
        attributes.put(0, "in_Position");
        attributes.put(1, "in_Color");
        attributes.put(2, "in_TextureCoord");
        ShaderProgram shader;
        SceneObject so;
        scene = new Scene();
        try {
            shader = new ShaderProgram(vertexShader, fragmentShader, attributes);
            so =new Quad(0.5f, 1, shader);
            scene.attach(so);
        } catch (Exception e) {
            System.out.println("ERRRRRRRRRROOOOOOOOOOORRRR");
        }
        
        camera = new Camera(5, 5, 1, 100);
		
		
		
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

		if (supportsEs2) {
			this.gLSurfaceView.setEGLContextClientVersion(2);
			this.gLSurfaceView.setRenderer(new AndroidRenderer(scene, camera));
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
