package com.millersvillecs.moleculeandroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.SparseArray;

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
import com.millersvillecs.moleculeandroid.util.FileUtil;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

public class AndroidRenderer implements GLSurfaceView.Renderer {
	
	private Scene scene;
	private Camera camera;
	private Context context;
	private long lastTime, currTime;
	private boolean manuallyRotating, changingMolecule;
	
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
        private GameLogic gameLogic;
        private Molecule currentMolecule;
        SceneNode moleculeNode;
        
	public AndroidRenderer(Context context) {
		camera = new Camera(5, 5, 1, 100);
		camera.setTranslation(0, 0, -10);
		camera.lookAt(0, 0, 1);
		scene = new Scene();
		this.context = context;
	}
	
    @Override
    public void onDrawFrame(GL10 gl) {
    	this.currTime = System.currentTimeMillis();
    	float deltaTime = (float)(this.currTime - this.lastTime);
    	if(deltaTime > 30f) {
    		deltaTime = 30f;
    	}
    	
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Molecule mol = null;
        if(this.gameLogic != null) {
        	mol = this.gameLogic.getCurrentMolecule();
        }
        
        if(mol != null && !mol.equals(currentMolecule)) {
        	this.changingMolecule = true;
        	if(moleculeNode != null) {
            	scene.detach(moleculeNode);
            }
        	moleculeNode = new SceneNode();
        	
        	for(Atom atom: mol.getAtoms()) {
        		//Color workingColor = new Color(1.0f, 0.05f, 0.05f, 1.0f);
        		Color color = new Color(atom.color.getRed(), atom.color.getGreen(), atom.color.getBlue(), atom.color.getAlpha());
        		//Cube cube = new Cube(workingColor);
        		Mesh sphere = GeometryUtils.createSphereGeometry(((float)atom.radius / 4),16, 16, color);
        		SceneObject atomObject = new SceneObject(sphere, shader);
        		atomObject.translate((float) atom.x, (float) atom.y, (float) atom.z);
        		//scene.attach(atomObject);
        		moleculeNode.attach(atomObject);
        	}
        	
        	//final Vector3 right = new Vector3(-1, 0, 0);

        	for(Bond bond : mol.getBonds()) {
        		Atom from = mol.getAtoms().get(bond.from);
        		Atom to = mol.getAtoms().get(bond.to);
        		Vector3 distance = new Vector3((float)from.x,(float) from.y,(float) from.z).sub((float)to.x,(float) to.y,(float) to.z);
        		final Vector3 directionA = new Vector3(0, -1, 0).normalize();
        		final Vector3 directionB = distance.clone().normalize();
        		
        		float angle = (float) Math.acos(directionA.dot(directionB));
        		final Vector3 rotationAxis = directionA.clone().cross(directionB).normalize();
        		Mesh cylinder = GeometryUtils.createCylinderGeometry(0.1f, distance.length(), 10, from.color,to.color);
        		SceneObject bondObject = new SceneObject(cylinder, shader);
        		//bondObject.translate(0,0.5f,0);
        		angle = (float) Math.toDegrees(angle);
        		//Vector3 rotation = crossProd.scale(angle);
        		bondObject.rotateLocal(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
        		bondObject.setTranslation(new Vector3((float)from.x, (float)from.y, (float)from.z));
        		//bondObject.lookAt((float)to.x, (float)to.y, (float)to.z, 0, 0, -1);
        		
        		moleculeNode.attach(bondObject);
        	}
        	
        	this.manuallyRotating = false;
        	currentMolecule = mol;
        	scene.attach(moleculeNode);
        	this.changingMolecule = false;
        }
        if(moleculeNode != null && !this.manuallyRotating) {
        	float rotChange = deltaTime / 20f;
        	moleculeNode.rotate(rotChange, rotChange, rotChange, 0);
        }
        scene.render(0, camera); // Delta Time Always 0 ???
        
        this.lastTime = this.currTime;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.82f, 0.82f, 0.82f, 1.0f);
        AssetManager assetManager = context.getAssets();
        
        
        try {
            SparseArray<String> attributes = new SparseArray<String>();
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
        
        this.lastTime = System.currentTimeMillis();
        this.currTime = System.currentTimeMillis();
       
    }
    
    public void setGameLogic(GameLogic g) {
    	this.gameLogic = g;
    }
    
    public void zoomMolecule(float amount) {
    	if(this.moleculeNode != null && !this.changingMolecule) {
    		this.moleculeNode.translate(0, 0, amount);
    	}
    }
    
    public void manuallyRotateMolecule(float amount) {
    	if(this.moleculeNode != null && !this.changingMolecule) {
    		this.manuallyRotating = true;
    		this.moleculeNode.rotate(amount, amount, 0, 0);
    	}
    }
}
