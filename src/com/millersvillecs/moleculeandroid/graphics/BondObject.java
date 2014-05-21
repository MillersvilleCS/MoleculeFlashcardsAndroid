package com.millersvillecs.moleculeandroid.graphics;

import android.opengl.GLES20;

import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.scene.SceneObject;

public class BondObject extends SceneObject{
	private Color color1, color2;
	public BondObject(Mesh mesh, ShaderProgram shader) {
		this(mesh, shader, new Color(1,1,1,1), new Color(1,1,1,1));
	}
	
	public BondObject(Mesh mesh, ShaderProgram shader, Color color1, Color color2) {
		super(mesh, shader);
		this.color1 = color1;
		this.color2 = color2;
	}

	@Override
	public void render(int delta, Camera camera) {
		shader.bind();
		int projectionHandle = shader.getUniformLocation("u_projection");
		GLES20.glUniformMatrix4fv(projectionHandle, 1, false, camera.getProjection(), 0);
		int viewHandle = shader.getUniformLocation("u_view");
		GLES20.glUniformMatrix4fv(viewHandle, 1, false, camera.getView(), 0);
		int modelHandle = shader.getUniformLocation("u_model");
		GLES20.glUniformMatrix4fv(modelHandle, 1, false, getModel().getArray(), 0);
		
		int color1Handle = shader.getUniformLocation("u_color1");
		GLES20.glUniform4f(color1Handle, color1.getRed(), color1.getGreen(), color1.getGreen(), color1.getAlpha());
		int color2Handle = shader.getUniformLocation("u_color2");
		GLES20.glUniform4f(color2Handle, color2.getRed(), color2.getGreen(), color2.getGreen(), color2.getAlpha());
		
		mesh.draw();
        shader.unbind();
	}
}
