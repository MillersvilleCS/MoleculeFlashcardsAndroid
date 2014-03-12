package com.millersvillecs.moleculeandroid;

import android.opengl.GLES20;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.Mesh;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.scene.SceneObject;

public class Sphere extends SceneObject {

	private static final float[] vertices = { -0.5f, 0.5f, 0f, -0.5f, -0.5f,
			0f, 0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f, };

	private static final float[] texCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };

	private static final int[] indices = { 0, 1, 2, 0, 2, 3, };

	public Sphere(Mesh mesh, ShaderProgram shader) {
		super(mesh, shader);

	}

	@Override
	public void render(int delta, Camera camera) {
		
		shader.bind();
		int mvpMatrixHandle = shader.getUniformLocation("u_modelViewProjMatrix");
		GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, camera.getViewProjection(), 0);
		int radiusHandle = shader.getUniformLocation("u_radius");
		GLES20.glUniformMatrix4fv(radiusHandle, 1, false, camera.getProjection(), 0);
		int spherePositionHandle = shader.getUniformLocation("u_spherePosition");
		GLES20.glUniformMatrix4fv(spherePositionHandle, 1, false, camera.getProjection(), 0);
		mesh.draw();
		shader.unbind();
	}
}
