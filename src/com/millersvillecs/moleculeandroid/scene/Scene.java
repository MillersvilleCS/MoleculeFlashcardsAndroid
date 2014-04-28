package com.millersvillecs.moleculeandroid.scene;

import java.util.ArrayList;
import java.util.List;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.Light;
import com.millersvillecs.moleculeandroid.util.Node;

public class Scene extends Node<SceneNode> {
	
	private List<Light> lights = new ArrayList<Light>();
	
    public Scene() {


    }

    public void render(int delta, Camera camera) {
        for (Node<SceneNode> node : this.getSubnodes()) {
            ((SceneNode) node).render(delta, camera);
        }
    }

    public void update(int delta) {

        for (Node<SceneNode> node : this.getSubnodes()) {
            ((SceneNode) node).update(delta);
        }
    }
    
    public void add(Light light) {
    	lights.add(light);
    }
    
    public void remove(Light light) {
    	lights.remove(light);
    }
    
}