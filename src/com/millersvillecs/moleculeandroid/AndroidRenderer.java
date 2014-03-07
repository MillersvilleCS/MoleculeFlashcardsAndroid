package com.millersvillecs.moleculeandroid;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.scene.Scene;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class AndroidRenderer implements GLSurfaceView.Renderer {
	
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
	
	public AndroidRenderer(Scene scene) {
		this.scene = scene;
		camera = new Camera(5, 5, 1, 100);
	}
	
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        scene.render(0, camera);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }
}
