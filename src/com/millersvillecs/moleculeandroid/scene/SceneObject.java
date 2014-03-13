package com.millersvillecs.moleculeandroid.scene;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.Mesh;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.util.math.Vector2;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

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

    public SceneObject(Vector3 translation, Mesh mesh, ShaderProgram shader) {
        super(translation);
        this.mesh = mesh;
        this.shader = shader;
    }

    public SceneObject(Vector3 translation,Vector3 scale, Mesh mesh, ShaderProgram shader) {
        super(translation, scale);
        this.mesh = mesh;
        this.shader = shader;
    }

	@Override
	public void render(int delta, Camera camera) {
		shader.bind();
		int projectionHandle = shader.getUniformLocation("u_projection");
		GLES20.glUniformMatrix4fv(projectionHandle, 1, false, camera.getProjection(), 0);
		int viewHandle = shader.getUniformLocation("u_view");
		GLES20.glUniformMatrix4fv(viewHandle, 1, false, camera.getView(), 0);
		int modelHandle = shader.getUniformLocation("u_model");
		GLES20.glUniformMatrix4fv(modelHandle, 1, false, createModelMatrix(), 0);
		//float[] temp = new float[16];
		//Matrix.multiplyMM(temp, 0, createModelMatrix(), 0, camera.getView(), 0);
    	//Matrix.multiplyMM(temp, 0, camera.getProjection(),0, temp, 0);
		
		mesh.draw();
        shader.unbind();
	}
	
	private float[] createModelMatrix() {
		float[] matrix = {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
		Matrix.translateM(matrix, 0, translation.x, translation.y, translation.z);
		return matrix;
	}
}