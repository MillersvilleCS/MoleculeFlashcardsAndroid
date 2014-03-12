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

	@Override
	public void render(int delta, Camera camera) {
		shader.bind();
		int viewHandle = shader.getUniformLocation("u_view");
		int projectionHandle = shader.getUniformLocation("u_projection");
		GLES20.glUniformMatrix4fv(viewHandle, 1, false, camera.getView(), 0);
		GLES20.glUniformMatrix4fv(projectionHandle, 1, false, camera.getProjection(), 0);
		mesh.draw();
        shader.unbind();
	}
}