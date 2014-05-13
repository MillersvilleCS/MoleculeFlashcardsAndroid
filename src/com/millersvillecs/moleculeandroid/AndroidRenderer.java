package com.millersvillecs.moleculeandroid;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.millersvillecs.moleculeandroid.data.Atom;
import com.millersvillecs.moleculeandroid.data.Bond;
import com.millersvillecs.moleculeandroid.data.Molecule;
import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.Color;
import com.millersvillecs.moleculeandroid.graphics.GeometryUtils;
import com.millersvillecs.moleculeandroid.graphics.Mesh;
import com.millersvillecs.moleculeandroid.graphics.VertexAttribute;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.scene.Scene;
import com.millersvillecs.moleculeandroid.scene.SceneNode;
import com.millersvillecs.moleculeandroid.scene.SceneObject;
import com.millersvillecs.moleculeandroid.util.BufferUtils;
import com.millersvillecs.moleculeandroid.util.FileUtil;
import com.millersvillecs.moleculeandroid.util.RangeUtils;
import com.millersvillecs.moleculeandroid.util.math.Quaternion;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

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
        ShaderProgram shader = null;
        private GameActivity activity;
        private Molecule currentMolecule;
        SceneNode moleculeNode;
	public AndroidRenderer(Context context, GameActivity activity) {
		camera = new Camera(5, 5, 1, 100);
		camera.setTranslation(0, 0, -10);
		camera.lookAt(0, 0, 1);
		scene = new Scene();
		this.context = context;
		this.activity = activity;
	}
	
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Molecule mol = activity.getCurrentMolecule();
        if(mol != null && !mol.equals(currentMolecule)) {
        	if(moleculeNode != null) {
            	scene.detach(moleculeNode);
            }
        	moleculeNode = new SceneNode();
        	
        	for(Atom atom: mol.getAtoms()) {
        		Color workingColor = new Color(1.0f, 0.05f, 0.05f, 1.0f);
        		Color tempColor = new Color(atom.color.getRed(), atom.color.getGreen(), atom.color.getBlue(), atom.color.getAlpha());
        		//Cube cube = new Cube(workingColor);
        		Mesh sphere = GeometryUtils.createSphereGeometry(((float)atom.radius / 4),12, 12, tempColor);
        		SceneObject atomObject = new SceneObject(sphere, shader);
        		atomObject.translate((float) atom.x, (float) atom.y, (float) atom.z);
        		//scene.attach(atomObject);
        		moleculeNode.attach(atomObject);
        	}
        	
        	for(Bond bond : mol.getBonds()) {
        		Atom from = mol.getAtoms().get(bond.from);
        		Atom to = mol.getAtoms().get(bond.to);
        		Mesh cylinder = GeometryUtils.createCylinderGeometry(0.1f, 1, 40, from.color,to.color);
        		bondObject = new SceneObject(cylinder, shader);
        		final Vector3 direction = new Vector3((float)from.x,(float) from.y,(float) from.z).sub((float)to.x,(float) to.y,(float) to.z).normalize();
        		final Vector3 crossProd = direction.cross(new Vector3(0,1,0));
        		float angle = (float) Math.acos(crossProd.dot(direction));
        		angle = (float) Math.toDegrees(angle);
        		bondObject.translate(new Vector3((float)from.x, (float)from.y, (float)from.z));
        		Vector3 rotation = crossProd.scale(angle);
        		bondObject.rotate(rotation.x,rotation.y, rotation.z);
        		/*
        		Vector3 direction = new Vector3((float)from.x,(float) from.y,(float) from.z).sub((float)to.x,(float) to.y,(float) to.z);
        		Vector3 nDirection = (new Vector3((float)from.x,(float) from.y,(float) from.z).sub((float)to.x,(float) to.y,(float) to.z)).normalize();
        		
        		//rotate
        		//create quaternion
        		float radians = 0 ;
        		Vector3 axis = new Vector3();
        		Quaternion q = new Quaternion();
        		if ( nDirection.y > 0.99999 ) {

        			q.set( 0, 0, 0, 1 );
        		} else if ( nDirection.y < - 0.99999 ) {
        			q.set( 1, 0, 0, 0 );
        		} else {
        			axis.set( nDirection.z, 0, - nDirection.x ).normalize();
        			radians = (float) Math.acos( nDirection.y );
        			q.setFromAxisAngle( axis, radians );
        		}
        		
        		//turn quaternion into Euler
        		float sqx = q.getX() * q.getX();
        		float sqy = q.getY() * q.getY();
        		float sqz = q.getZ() * q.getZ();
        		float sqw = q.getW() * q.getW();

        		float ex = (float) Math.atan2( 2 * ( q.getX() * q.getW() - q.getY() * q.getZ() ), ( sqw - sqx - sqy + sqz ) );
        		float ey = (float) Math.asin(  RangeUtils.forceIntoRange( 2 * ( q.getX() * q.getZ() + q.getY() * q.getW() ), -1, 1 ) );
        		float ez = (float) Math.atan2( 2 * ( q.getZ() * q.getW() - q.getX() * q.getY() ), ( sqw + sqx - sqy - sqz ) );

        		bondObject.translate(new Vector3((float)from.x, (float)from.y, (float)from.z).add(direction.scale(0.5f)));
        		bondObject.rotate((float)Math.toDegrees(ex),(float) Math.toDegrees(ey), (float)Math.toDegrees(ez));*/
        		moleculeNode.attach(bondObject);
        	}
        	
        	currentMolecule = mol;
        	scene.attach(moleculeNode);
        }
        if(moleculeNode != null) {
        	moleculeNode.rotate(1f, 1, 0);
        	bondObject.rotate(1, 1, 0);
        }
        scene.render(0, camera);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        
        
    }
    SceneObject bondObject;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        AssetManager assetManager = context.getAssets();
        
        
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
        Color c1 = new Color(0.0f, 0.5f, 15f, 1.0f);
        Color c2 = new Color(1.0f, 1f, 1f, 1.0f);
        Mesh cube2 = GeometryUtils.createSphereGeometry(1, 12, 12, c2);
        Mesh cube1 = GeometryUtils.createSphereGeometry(1, 12, 12, c1);
        
        SceneObject c1Object = new SceneObject(cube1, shader);
        c1Object.translate(-1, 0, 0);
        
        SceneObject c2Object = new SceneObject(cube2, shader);
        c2Object.translate(1, 0, 0);
        //scene.attach(c2Object);
        //scene.attach(c1Object);
       
       
    }
    
    public void setMolecule(Molecule molecule) {
    	
    }
}
