package com.millersvillecs.moleculeandroid.scene;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.util.Node;

public class Scene extends Node<SceneNode> {

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
}