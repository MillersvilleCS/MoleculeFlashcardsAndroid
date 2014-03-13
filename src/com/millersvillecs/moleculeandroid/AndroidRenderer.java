package com.millersvillecs.moleculeandroid;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.Mesh;
import com.millersvillecs.moleculeandroid.graphics.VertexAttribute;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.scene.Scene;
import com.millersvillecs.moleculeandroid.scene.SceneNode;
import com.millersvillecs.moleculeandroid.scene.SceneObject;
import com.millersvillecs.moleculeandroid.util.BufferUtils;
import com.millersvillecs.moleculeandroid.util.FileUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class AndroidRenderer implements GLSurfaceView.Renderer {
	
	private Scene scene;
	private Camera camera;
	private Context context;
	
	final String vertexShader =
            "uniform mat4 u_view;      \n"     // A constant representing the combined model/view/projection matrix.
		  + "uniform mat4 u_projection; 	\n"
		
          + "attribute vec4 in_Position;     \n"     // Per-vertex position information we will pass in.
          + "attribute vec4 in_Color;        \n"     // Per-vertex color information we will pass in.              
          
          + "varying vec4 pass_Color;          \n"     // This will be passed into the fragment shader.
          
          + "void main()                    \n"     // The entry point for our vertex shader.
          + "{                              \n"
          + "   pass_Color = in_Color;          \n"     // Pass the color through to the fragment shader. 
                                                    // It will be interpolated across the triangle.
          + "   gl_Position = u_projection * u_view  \n"     // gl_Position is a special variable used to store the final position.
          + "               * in_Position;   \n"     // Multiply the vertex by the matrix to get the final point in                                                                   
          + "}                              \n";    // normalized screen coordinates.
        
        final String fragmentShader =
            "precision mediump float;       \n"     // Set the default precision to medium. We don't need as high of a 
                                                    // precision in the fragment shader.                
          + "varying vec4 pass_Color;          \n"     // This is the color from the vertex shader interpolated across the 
                                                    // triangle per fragment.             
          + "void main()                    \n"     // The entry point for our fragment shader.
          + "{                              \n"
          + "   gl_FragColor = pass_Color;     \n"     // Pass the color directly through the pipeline.          
          + "}                              \n";
        
        final float[] triangle1PositionData = {
                -0.5f, -0.25f, 0.0f, 
                0.5f, -0.25f, 0.0f,
                0.0f, 0.559016994f, 0.0f, };
        final float[] triangle1ColorData = {
                1.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f};
        final int[] triangle1IndicesData = {
                0,1,2};
        
	public AndroidRenderer(Context context) {
		camera = new Camera(5, 5, 1, 100);
		camera.setTranslation(0, 0, -3);
		camera.lookAt(0, 0, 1);
		scene = new Scene();
		this.context = context;
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
        AssetManager assetManager = context.getAssets();
        
        ShaderProgram shader = null;
        try {
            Map<Integer, String> attributes = new HashMap<Integer, String>();
            attributes.put(0, "in_position");
            attributes.put(1, "in_color");
            
            String sphereVertShader = FileUtil.convertStreamToString(assetManager.open("shaders/BasicShader.vert"));
            String sphereFragShader = FileUtil.convertStreamToString(assetManager.open("shaders/BasicShader.frag"));
            shader = new ShaderProgram(sphereVertShader, sphereFragShader, attributes);
              
          } catch (IOException e) {
              System.out.println("Could not create the shader");
          } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        List<VertexAttribute> meshDescriptor = new ArrayList<VertexAttribute>();
        meshDescriptor.add(new VertexAttribute(triangle1PositionData, 3));
        meshDescriptor.add(new VertexAttribute(triangle1ColorData, 4));
        
        Mesh mesh  =new Mesh(triangle1IndicesData, meshDescriptor);
        
        SceneObject so = new SceneObject(mesh, shader);
        so.translate(0f, 0, 0);
        //scene.attach(so);
        Quad quad = new Quad(5, 5, shader);
        Quad quad2 = new Quad(2, 2, shader);
        quad.translate(1f, 0.2f, 0);
        scene.attach(quad);
        scene.attach(quad2);
        //scene.attach(new SceneObject(mesh, shader));
        
    }
}
