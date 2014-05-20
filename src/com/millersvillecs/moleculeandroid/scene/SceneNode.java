package com.millersvillecs.moleculeandroid.scene;

import java.util.ArrayList;
import java.util.List;

import android.renderscript.Matrix4f;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.util.Node;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

public class SceneNode extends Node<SceneNode> {

	protected float sceneTime;
	protected Matrix4f modelMatrix, translationMatrix, rotationMatrix;
	protected Vector3 translation;
	
	private List<IBehavior> behaviors = new ArrayList<IBehavior>();
	private SceneNode parent = null;

	public SceneNode() {
		this(new Vector3());
	}

	public SceneNode(Vector3 translation) {
		modelMatrix = new Matrix4f();
		translationMatrix = new Matrix4f();
		rotationMatrix = new Matrix4f();
		this.translation = translation;
		setTranslation(translation);
	}
	
	@Override
	public void attach(Node<SceneNode> node) {
		super.attach(node);
		((SceneNode)node).parent = this;
	}

	public void render(int delta, Camera camera) {
		for (Node<SceneNode> node : this.getSubnodes()) {
			((SceneNode)node).render(delta, camera);
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
	
	public SceneNode translate(Vector3 translation) {
		return translate(translation.x, translation.y, translation.z);
	}
	
	public SceneNode translate(float x, float y, float z) {
		translationMatrix.translate(x, y, z);
		translation.add(x, y, z);
		return this;
	}

	public SceneNode rotate(float angle, float x, float y, float z) {
		rotationMatrix.rotate(angle, x, y, z);
		return this;
	}
	
	public Matrix4f getModel() {
		modelMatrix.load(translationMatrix);
		modelMatrix.multiply(rotationMatrix);
		if(parent == null) {
			return modelMatrix;
		} else {	
			Matrix4f parentMatrix = parent.getModel();
			parentMatrix.multiply(modelMatrix);
			return parentMatrix;
		}
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
}
