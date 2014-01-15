package com.millersvillecs.moleculeandroid.scene;

import java.util.ArrayList;
import java.util.List;

import com.millersvillecs.moleculeandroid.scene.behaviors.IBehavior;
import com.millersvillecs.moleculeandroid.util.NList;
import com.millersvillecs.moleculeandroid.util.Node;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

/**
 * 
 * @author william
 *
 */
public abstract class SceneNode extends Node<SceneNode> {
	
	private Vector3 translation;
	private List<IBehavior> behaviors = new ArrayList<IBehavior>(); 
	private float sceneTime;
	
	public SceneNode(){
		this(new Vector3());
	}
	
	public SceneNode(Vector3 translation){
		this.translation = translation;
	}
	
	public void render(float delta){
		for(Node<SceneNode> node : this.getSubnodes()){
			((SceneNode) node).render(delta);
		}
	}
	
	public void update(float delta) {
		this.sceneTime += delta;
		
		for(IBehavior behavior : behaviors) {
			behavior.act(this);
		}
		
		for(Node<SceneNode> node : this.getSubnodes()){
			((SceneNode) node).update(delta);
		}
	}

	public SceneNode setTranslation(Vector3 translation){
		return setTranslation(translation.x, translation.y, translation.z);
	}

	public SceneNode setTranslation(float x, float y, float z){
		for(Node<SceneNode> node : this.getSubnodes()) {
			Vector3 offset = ((SceneNode) node).getTranslation().sub(x, y, z);
			((SceneNode) node).setTranslation(offset.add(x, y, z));
		}
		translation.set(x, y, z);
		
		return this;
	}

	public SceneNode translate(Vector3 translation){
		return translate(translation.x, translation.y, translation.z);
	}

	public SceneNode translate(float x, float y, float z){
		for(Node<SceneNode> node : this.getSubnodes()){
			((SceneNode) node).getTranslation().add(x, y, z);
		}
		translation.add(x, y, z);
		
		return this;
	}

	public Vector3 getTranslation(){
		return translation;
	}

	public float getX(){
		return translation.x;
	}

	public float getY(){
		return translation.y;
	}
	
	public float getZ(){
		return translation.z;
	}

	public float getSceneTime() {
		return sceneTime;
	}
}
