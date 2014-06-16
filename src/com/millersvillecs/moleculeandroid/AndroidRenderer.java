package com.millersvillecs.moleculeandroid;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.SparseArray;

import com.millersvillecs.moleculeandroid.data.Molecule;
import com.millersvillecs.moleculeandroid.data.MoleculeGeometryConstructor;
import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.Color;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.scene.Scene;
import com.millersvillecs.moleculeandroid.scene.SceneNode;
import com.millersvillecs.moleculeandroid.util.FileUtil;
import com.millersvillecs.moleculeandroid.util.math.Vector2;

public class AndroidRenderer implements GLSurfaceView.Renderer {
	
	private Scene scene;
	private Camera camera;
	private Context context;
	private long lastTime, currTime;
	private boolean changingMolecule;
	private int autoRotateDirection = 1;
	private Map<String, Color> colorMap = new HashMap<String,Color>();
	private Map<String, Float> radiusMap = new HashMap<String,Float>();

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
        	moleculeNode = MoleculeGeometryConstructor.construct(mol.getAtoms(), mol.getBonds(), 16, 40, shader, bondShader, colorMap, radiusMap);
        	
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
       
        AssetManager assetManager = context.getAssets();
        
        String data;
		try {
			data = FileUtil.convertStreamToString(assetManager.open("moleculeData.txt"));
			StringTokenizer lineTokenizer = new StringTokenizer(data, "\n");
	        while (lineTokenizer.hasMoreElements()) {
	            StringTokenizer dataTokenizer = new StringTokenizer((String) lineTokenizer.nextElement(), ":");
	            String key = (String) dataTokenizer.nextElement();
	            String value = (String) dataTokenizer.nextElement();
	            value = value.replace(" ", "").replace("\r\n", "").replace("\n", "");
	            String[] tokens = value.split(",",-1);
	            Color color = new Color(Float.parseFloat(tokens[0]) / 255, Float.parseFloat(tokens[1]) / 255, Float.parseFloat(tokens[2]) / 255);
	            colorMap.put(key, color);
	            
	            radiusMap.put(key, Float.parseFloat(tokens[3]));
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
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
    		if(amountX < 0) {
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
