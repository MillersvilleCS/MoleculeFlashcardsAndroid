package com.millersvillecs.moleculeandroid.scene;

import android.opengl.GLES20;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.Mesh;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.util.math.Vector2;

/**
 * Created by Will on 2/7/14.
 */
public class SceneObject extends SceneNode {

    protected Mesh mesh;
    protected ShaderProgram shader;

    public SceneObject(Mesh mesh, ShaderProgram shader) {
        this.mesh = mesh;
        this.shader = shader;
    }

    public SceneObject(Vector2 translation, Mesh mesh, ShaderProgram shader) {
        super(translation);
        this.mesh = mesh;
        this.shader = shader;
    }

    public SceneObject(Vector2 translation,Vector2 scale, Mesh mesh, ShaderProgram shader) {
        super(translation, scale);
        this.mesh = mesh;
        this.shader = shader;
    }

    protected void beginRendering() {
        shader.bind();
    }

    protected void endRendering() {
        mesh.draw();
        shader.unbind();
    }

	@Override
	public void render(int delta, Camera camera) {
		shader.bind();
		int modelViewHandle = shader.getUniformLocation("u_MVPMatrix");
		GLES20.glUniformMatrix4fv(modelViewHandle, 1, false, camera.getViewProjection(), 0);
		mesh.draw();
        shader.unbind();
	}
}