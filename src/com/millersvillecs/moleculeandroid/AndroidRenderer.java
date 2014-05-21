package com.millersvillecs.moleculeandroid;

import java.io.IOException;

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
import com.millersvillecs.moleculeandroid.graphics.BondObject;
import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.Color;
import com.millersvillecs.moleculeandroid.graphics.GeometryUtils;
import com.millersvillecs.moleculeandroid.graphics.Mesh;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.scene.Scene;
import com.millersvillecs.moleculeandroid.scene.SceneNode;
import com.millersvillecs.moleculeandroid.scene.SceneObject;
import com.millersvillecs.moleculeandroid.util.FileUtil;
import com.millersvillecs.moleculeandroid.util.math.Vector2;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

public class AndroidRenderer implements GLSurfaceView.Renderer {
	
	private Scene scene;
	private Camera camera;
	private Context context;
	private long lastTime, currTime;
	private boolean changingMolecule;
	private int autoRotateDirection = 1;

    ShaderProgram shader = null;
    ShaderProgram bondShader = null;
    private GameLogic gameLogic;
    private Molecule currentMolecule;
    SceneNode moleculeNode;
    boolean manuallyEditing = false;
        
	public AndroidRenderer(Context context) {
		camera = new Camera(50, 50, 1, 100);
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
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
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
        		Color fromColor = new Color(from.color.getRed(), from.color.getGreen(), from.color.getBlue(), from.color.getAlpha());
        		Color toColor = new Color(to.color.getRed(), to.color.getGreen(), to.color.getBlue(), to.color.getAlpha());
        		
        		if(bond.type == Bond.SINGLE) {
        			Mesh cylinder = GeometryUtils.createCylinderGeometry(0.08f, distance.length(), 20, fromColor,toColor);
            		SceneObject bondObject = new BondObject(cylinder, bondShader, fromColor, toColor );
            		angle = (float) Math.toDegrees(angle);
            		bondObject.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
            		bondObject.setTranslation(new Vector3((float)from.x, (float)from.y, (float)from.z));
            		moleculeNode.attach(bondObject);
        		} else if(bond.type == Bond.DOUBLE) {
        			Mesh cylinder = GeometryUtils.createCylinderGeometry(0.08f, distance.length(), 20, from.color,to.color);
        			angle = (float) Math.toDegrees(angle);
        			
            		SceneObject bondObject1 = new BondObject(cylinder, bondShader, fromColor, toColor );
            		bondObject1.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
            		bondObject1.setTranslation(new Vector3((float)from.x+ 0.15f, (float)from.y + 0.15f, (float)from.z));
            		
            		SceneObject bondObject2 = new BondObject(cylinder, bondShader, fromColor, toColor );
            		bondObject2.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
            		bondObject2.setTranslation(new Vector3((float)from.x - 0.15f, (float)from.y - 0.15f, (float)from.z));
            		
            		moleculeNode.attach(bondObject1);
            		moleculeNode.attach(bondObject2);
        		} else if(bond.type == Bond.TRIPLE) {
        			Mesh cylinder = GeometryUtils.createCylinderGeometry(0.08f, distance.length(), 20, from.color,to.color);
        			angle = (float) Math.toDegrees(angle);
        			
            		SceneObject bondObject1 = new BondObject(cylinder, bondShader, fromColor, toColor );
            		bondObject1.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
            		bondObject1.setTranslation(new Vector3((float)from.x+ 0.25f, (float)from.y + 0.25f, (float)from.z));
            		
            		SceneObject bondObject2 = new BondObject(cylinder, bondShader, fromColor, toColor );
            		bondObject2.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
            		bondObject2.setTranslation(new Vector3((float)from.x - 0.25f, (float)from.y - 0.25f, (float)from.z));
            		
            		SceneObject bondObject3 = new BondObject(cylinder, bondShader, fromColor, toColor );
            		bondObject3.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
            		bondObject3.setTranslation(new Vector3((float)from.x, (float)from.y, (float)from.z));
            		
            		moleculeNode.attach(bondObject1);
            		moleculeNode.attach(bondObject2);
            		moleculeNode.attach(bondObject3);
        		}
        	}
        	
        	currentMolecule = mol;
        	scene.attach(moleculeNode);
        	this.changingMolecule = false;
        }
        if(moleculeNode != null && !manuallyEditing) {
        	float rotChange = deltaTime / 20f;
        	moleculeNode.rotate(rotChange * autoRotateDirection, 0, 1, 0);
        }
        scene.render(0, camera); // Delta Time Always 0 ???
        
        this.lastTime = this.currTime;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        camera = new Camera(width, height, 1, 100);
        camera.setTranslation(0, 0, -10);
		camera.lookAt(0, 0, 1);
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.82f, 0.82f, 0.82f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ZERO);
       
        //GLES20.glEnable(GLES20.GL_CULL_FACE);
        AssetManager assetManager = context.getAssets();
        
        try {
        	//create atom shader
            SparseArray<String> attributes = new SparseArray<String>();
            attributes.put(0, "in_position");
            attributes.put(1, "in_normal");
            attributes.put(2, "in_color");
            
            String sphereVertShader = FileUtil.convertStreamToString(assetManager.open("shaders/BasicShader.vert"));
            String sphereFragShader = FileUtil.convertStreamToString(assetManager.open("shaders/BasicShader.frag"));
            shader = new ShaderProgram(sphereVertShader, sphereFragShader, attributes);
            
            //create bond shader
            SparseArray<String> bondAttributes = new SparseArray<String>();
            bondAttributes.put(0, "in_position");
            bondAttributes.put(1, "in_normal");
            bondAttributes.put(2, "in_color");
            
            String bondVertShader = FileUtil.convertStreamToString(assetManager.open("shaders/BondShader.vert"));
            String bondFragShader = FileUtil.convertStreamToString(assetManager.open("shaders/BondShader.frag"));
            bondShader = new ShaderProgram(bondVertShader, bondFragShader, bondAttributes);
        } catch (IOException e) {
            System.out.println("Could not create the shader");
        } catch (Exception e) {
			e.printStackTrace();
		}     
      
        
        this.lastTime = System.currentTimeMillis();
        this.currTime = System.currentTimeMillis();
       
    }
    
    public void setGameLogic(GameLogic g) {
    	this.gameLogic = g;
    }
    
    public void zoomMolecule(float amount) {
    	if(this.moleculeNode != null && !this.changingMolecule) {
    		if(amount < 0 && this.moleculeNode.getZ() < -3f) {
    			//do nothing
    		} else if(amount > 0 && this.moleculeNode.getZ() > 10f) {
    			//do nothing
    		} else {
    			this.moleculeNode.translate(0, 0, amount);
    		}
    	}
    }
    
    public void manuallyRotateMolecule(float amountX, float amountY) {
    	if(this.moleculeNode != null && !this.changingMolecule) {
    		if(amountX < 1) {
    			autoRotateDirection = -1;
    		} else {
    			autoRotateDirection = 1;
    		}
    		Vector2 rotation = new Vector2(amountX,amountY);
    		rotation.normalize();
    		if( Math.abs(amountX) < 40)
    			this.moleculeNode.rotate(amountX, 0, 1, 0);
    		//this.moleculeNode.rotate(amountY, 1, 0, 0);
    	}
    }
}
