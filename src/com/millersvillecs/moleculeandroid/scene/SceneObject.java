package com.millersvillecs.moleculeandroid.scene;

import android.opengl.GLES20;
import android.renderscript.Matrix3f;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.Mesh;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

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

	@Override
	public void render(int delta, Camera camera) {
		shader.bind();
		int projectionHandle = shader.getUniformLocation("u_projection");
		GLES20.glUniformMatrix4fv(projectionHandle, 1, false, camera.getProjection(), 0);
		int viewHandle = shader.getUniformLocation("u_view");
		GLES20.glUniformMatrix4fv(viewHandle, 1, false, camera.getView(), 0);
		
		//convert Matrix4 to Matrix3
		float[] model = getModel().getArray();
		Matrix3f normalMatrix = new Matrix3f();
		for(int j = 0; j < 3; j++) {
			for(int i = 0; i < 3; i++) {
				normalMatrix.set(j, i, model[(j * 4) + i]);
			}
		}
		//normally, we'd need to inverse and transpose the normal matrix
		//but since we have no scaling, we can just use it as it is
		
		int modelHandle = shader.getUniformLocation("u_model");
		GLES20.glUniformMatrix4fv(modelHandle, 1, false, model, 0);
		int normalMatrixHandle = shader.getUniformLocation("u_normalMatrix");
		GLES20.glUniformMatrix3fv(normalMatrixHandle, 1, false, normalMatrix.getArray(), 0);
		
		mesh.draw();
        shader.unbind();
	}
}