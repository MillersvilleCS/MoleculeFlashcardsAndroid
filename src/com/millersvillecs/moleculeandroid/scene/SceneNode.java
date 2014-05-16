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

	protected Matrix4f translation = new Matrix4f();
	protected Matrix4f rotation = new Matrix4f();
	protected Matrix4f localRotation = new Matrix4f();
	//protected Vector3 translation = new Vector3();
	
	protected float sceneTime;

	public SceneNode() {
		this(new Vector3());
	}

	public SceneNode(Vector3 translation) {
		this(translation, new Vector3(1, 1, 1));
	}

	public SceneNode(Vector3 translation, Vector3 scale) {
		localRotation.loadIdentity();
		rotation.loadIdentity();
		this.translation.loadIdentity();
		this.translation.translate(translation.x, translation.y, translation.z);
		//this.translation = translation;
	}

	public void render(int delta, Camera camera) {
		for (Node<SceneNode> node : this.getSubnodes()) {
			
			((SceneNode) node).render(delta, camera);
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
		for (Node<SceneNode> node : this.getSubnodes()) {
			SceneNode nodeCast = ((SceneNode) node);
			float xo = getX() - nodeCast.getX();
			float yo = getY() - nodeCast.getY();
			float zo = getZ() - nodeCast.getZ();
			((SceneNode) node).setTranslation(xo + x, yo + y, zo + z);
		}
		translation.translate(-getX(), -getY(), -getZ());
		translation.translate(x, y, z);
		//translation.set(x, y, z);
		return this;
	}
	
	public SceneNode translate(float x, float y, float z) {
		for (Node<SceneNode> node : this.getSubnodes()) {
			((SceneNode) node).translate(x, y, z);
		}
		translation.translate(x, y, z);
		//translation.set(x, y, z);
		return this;
	}

	public SceneNode translate(Vector3 translation) {
		return translate(translation.x, translation.y, translation.z);
	}


	
	public SceneNode rotate(float rot, float rx, float ry, float rz) {
		for (Node<SceneNode> node : this.getSubnodes()) {
			((SceneNode) node).rotate(rot, rx, ry, rz);
		}
		rotation.rotate(rot, rx, ry, rz);
		return this;
	}
	
	public SceneNode rotateLocal(float rot, float rx, float ry, float rz) {
		
		for (Node<SceneNode> node : this.getSubnodes()) {
			((SceneNode) node).rotateLocal(rot, rx, ry, rz);
		}
		//float x = getX();
		//float y = getY();
		//float z = getZ();
		
		//model.translate(-x, -y, -z);
		localRotation.rotate(rot, rx, ry, rz);
		//model.translate(x, y, z);
		return this;
	}
	
	public SceneNode lookAt(float x, float y , float z, float ax, float ay, float az) {
		float[] resultMatrix = {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
		Matrix.setLookAtM(resultMatrix, 0, getX(), getY(), getZ(), x, y,  z, ax, ay,  az );
		localRotation = new Matrix4f(resultMatrix);
	
		return this;
	}

	public float getX() {
		return translation.get(3, 0);
	}

	public float getY() {
		return translation.get(3, 1);
	}

	public float getZ() {
		return translation.get(3, 2);
	}
	
	public float getSceneTime() {
		return sceneTime;
	}
	
}
