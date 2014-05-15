package com.millersvillecs.moleculeandroid.scene;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Matrix;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.util.Node;
import com.millersvillecs.moleculeandroid.util.math.Vector2;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

/**
 * 
 * @author william
 * 
 */
public class SceneNode extends Node<SceneNode> {

	private List<IBehavior> behaviors = new ArrayList<IBehavior>();

	protected Vector3 translation, scale, rotation, localRotation;
	protected float sceneTime;

	public SceneNode() {
		this(new Vector3());
	}

	public SceneNode(Vector3 translation) {
		this(translation, new Vector3(1, 1, 1));
	}

	public SceneNode(Vector3 translation, Vector3 scale) {
		this.translation = translation;
		this.scale = scale;
		this.rotation = new Vector3();
		this.localRotation = new Vector3();
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
			Vector3 offset = ((SceneNode) node).getTranslation().sub(x, y, z);
			((SceneNode) node).setTranslation(offset.add(x, y, z));
		}
		translation.set(x, y, z);

		return this;
	}

	public SceneNode translate(Vector3 translation) {
		return translate(translation.x, translation.y, translation.z);
	}

	public SceneNode translate(float x, float y, float z) {
		for (Node<SceneNode> node : this.getSubnodes()) {
			((SceneNode) node).getTranslation().add(x, y, z);
		}
		translation.add(x, y, z);

		return this;
	}

	public SceneNode scale(float scalarX, float scalarY, float scalarZ) {
		for (Node<SceneNode> node : this.getSubnodes()) {
			((SceneNode) node).scale(scalarX, scalarY, scalarZ);
		}
		scale.mul(scalarX, scalarY, scalarZ);

		return this;
	}

	public SceneNode scale(float scalar) {
		scale.mul(scalar, scalar, scalar);

		return this;
	}
	
	public SceneNode rotate(float rx, float ry, float rz) {
		for (Node<SceneNode> node : this.getSubnodes()) {
			((SceneNode) node).rotate(rx, ry, rz);
		}
		this.rotation.add(rx, ry, rz);
		return this;
	}
	
	public SceneNode rotateLocal(float rx, float ry, float rz) {
		
		for (Node<SceneNode> node : this.getSubnodes()) {
			((SceneNode) node).rotateLocal(rx, ry, rz);
		}
		this.localRotation.add(rx, ry, rz);
		return this;
	}

	public Vector3 getTranslation() {
		return translation;
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
	
	public Vector3 getScale() {
		return scale;
	}
	
	public Vector3 getRotation() {
		return rotation;
	}
	public float getSceneTime() {
		return sceneTime;
	}
}
