package com.millersvillecs.moleculeandroid.scene;

import java.util.ArrayList;
import java.util.List;

import android.opengl.Matrix;
import android.renderscript.Matrix4f;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.util.Node;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

/**
 * 
 * @author william
 * 
 */
public class SceneNode extends Node<SceneNode> {

	private List<IBehavior> behaviors = new ArrayList<IBehavior>();
	private SceneNode parent = null;
	private Matrix4f model = new Matrix4f();
	protected Matrix4f translationMatrix = new Matrix4f();
	protected Matrix4f rotationMatrix = new Matrix4f();
	protected Vector3 translation = new Vector3();
	
	protected float sceneTime;

	public SceneNode() {
		this(new Vector3());
	}

	public SceneNode(Vector3 translation) {
		this(translation, new Vector3(1, 1, 1));
	}

	public SceneNode(Vector3 translation, Vector3 scale) {
		translationMatrix.loadIdentity();
		rotationMatrix.loadIdentity();
		setTranslation(translation);
	}

	public void render(int delta, Camera camera) {
		for (int i = 0; i < getSubnodes().size(); ++i) {
			((SceneNode)getSubnodes().get(i)).render(delta, camera);
		}
	}

	public void update(int delta) {
		this.sceneTime += delta;

		for (IBehavior behavior : behaviors) {
			behavior.act(this);
		}

		for (Node<SceneNode> node : this.getSubnodes()) {
			((SceneNode) node).update(delta);
		}
	}

	public SceneNode setTranslation(Vector3 translation) {
		return setTranslation(translation.x, translation.y, translation.z);
	}

	public SceneNode setTranslation(float x, float y, float z) {
		translationMatrix.translate(-getX(), -getY(), -getZ());
		translationMatrix.translate(x, y, z);
		translation.set(x, y, z);
		return this;
	}
	
	public SceneNode translate(float x, float y, float z) {
		translationMatrix.translate(x, y, z);
		translation.add(x, y, z);
		return this;
	}

	public SceneNode translate(Vector3 translation) {
		return translate(translation.x, translation.y, translation.z);
	}


	
	public SceneNode rotate(float rot, float rx, float ry, float rz) {
		rotationMatrix.rotate(rot, rx, ry, rz);
		return this;
	}
	
	public SceneNode rotateLocal(float rot, float rx, float ry, float rz) {
		
		float x = getX();
		float y = getY();
		float z = getZ();
		
		translationMatrix.translate(-x, -y, -z);
		rotationMatrix.rotate(rot, rx, ry, rz);
		translationMatrix.translate(x, y, z);
		return this;
	} 
	
	@Override
	public void attach(Node<SceneNode> node) {
		super.attach(node);
		((SceneNode)node).parent = this;
	}

	public float getX() {
		return translation.x;
	}

	public float getY() {
		return translation.y;
	}

	public float getZ() {
		return translation.z;
	}
	
	public float getSceneTime() {
		return sceneTime;
	}
	
	public Matrix4f getModel() {
		model.load(translationMatrix);
		model.multiply(rotationMatrix);
		if(parent == null) {
			return model;
		} else {
			
			Matrix4f parentMatrix = parent.getModel();
			parentMatrix.multiply(model);
			return parentMatrix;
		}
	}
}
