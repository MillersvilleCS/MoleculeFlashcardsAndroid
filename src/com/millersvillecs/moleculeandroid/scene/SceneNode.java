package com.millersvillecs.moleculeandroid.scene;

import java.util.ArrayList;
import java.util.List;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.util.Node;
import com.millersvillecs.moleculeandroid.util.math.Vector2;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

/**
 * 
 * @author william
 * 
 */
public abstract class SceneNode extends Node<SceneNode> {

    private List<IBehavior> behaviors = new ArrayList<IBehavior>();

    protected Vector2 translation, scale;
    protected float sceneTime;

    public SceneNode() {
        this(new Vector2());
    }

    public SceneNode(Vector2 translation) {
        this(translation, new Vector2(1, 1));
    }

    public SceneNode(Vector2 translation, Vector2 scale) {
        this.translation = translation;
        this.scale = scale;
    }

    public abstract void render(int delta, Camera camera);

    public void renderChildren(int delta, Camera camera) {
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

    public SceneNode setTranslation(Vector2 translation) {
        return setTranslation(translation.x, translation.y);
    }

    public SceneNode setTranslation(float x, float y) {
        for (Node<SceneNode> node : this.getSubnodes()) {
            Vector2 offset = ((SceneNode) node).getTranslation().sub(x, y);
            ((SceneNode) node).setTranslation(offset.add(x, y));
        }
        translation.set(x, y);

        return this;
    }

    public SceneNode translate(Vector2 translation) {
        return translate(translation.x, translation.y);
    }

    public SceneNode translate(float x, float y) {
        for (Node<SceneNode> node : this.getSubnodes()) {
            ((SceneNode) node).getTranslation().add(x, y);
        }
        translation.add(x, y);

        return this;
    }

    public SceneNode scale(float scalarX, float scalarY) {
        scale.mul(scalarX, scalarY);

        return this;
    }

    public SceneNode scale(float scalar) {
        scale.mul(scalar, scalar);

        return this;
    }

    public Vector2 getTranslation() {
        return translation;
    }

    public float getX() {
        return translation.x;
    }

    public float getY() {
        return translation.y;
    }

    public float getSceneTime() {
        return sceneTime;
    }
}
