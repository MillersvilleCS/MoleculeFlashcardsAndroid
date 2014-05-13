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
		
		mesh.draw();
        shader.unbind();
	}
	
	private float[] createModelMatrix() {
		float[] resultMatrix = {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
		//local rotation
		Matrix.setIdentityM(resultMatrix, 0);
		//Matrix.rotateM(resultMatrix, 0, localRotation.x, 1, 0, 0);
		//Matrix.rotateM(resultMatrix, 0, localRotation.y, 0, 1, 0);
		//Matrix.rotateM(resultMatrix, 0, localRotation.z, 0, 0, 1);
		
		float[] localRotateXMatrix = {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
		Matrix.rotateM(localRotateXMatrix, 0, localRotation.x, 1, 0, 0);
		float[] localRotateYMatrix = {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
		Matrix.rotateM(localRotateYMatrix, 0, localRotation.y, 0, 1, 0);
		float[] localRotateZMatrix = {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
		Matrix.rotateM(localRotateZMatrix, 0, localRotation.z, 0, 0, 1);
		
		
		
		//world rotation
		float[] rotateXMatrix = {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
		Matrix.rotateM(rotateXMatrix, 0, rotation.x, 1, 0, 0);
		float[] rotateYMatrix = {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
		Matrix.rotateM(rotateYMatrix, 0, rotation.y, 0, 1, 0);
		float[] rotateZMatrix = {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
		Matrix.rotateM(rotateZMatrix, 0, rotation.z, 0, 0, 1);
		
		float[] translateMatrix = {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		};
		Matrix.translateM(translateMatrix,0, translation.x, translation.y, translation.z);
		//local
		Matrix.multiplyMM(resultMatrix, 0, localRotateXMatrix, 0, resultMatrix, 0);
		Matrix.multiplyMM(resultMatrix, 0, localRotateYMatrix, 0, resultMatrix, 0);
		Matrix.multiplyMM(resultMatrix, 0, localRotateZMatrix, 0, resultMatrix, 0);
		
		//translate
		Matrix.multiplyMM(resultMatrix, 0, translateMatrix, 0, resultMatrix, 0);
		
		//world
		Matrix.multiplyMM(resultMatrix, 0, rotateXMatrix, 0, resultMatrix, 0);
		Matrix.multiplyMM(resultMatrix, 0, rotateYMatrix, 0, resultMatrix, 0);
		Matrix.multiplyMM(resultMatrix, 0, rotateZMatrix, 0, resultMatrix, 0);
		return resultMatrix;
	}
}