package com.millersvillecs.moleculeandroid;

import java.util.ArrayList;
import java.util.List;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.Mesh;
import com.millersvillecs.moleculeandroid.graphics.VertexAttribute;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.scene.SceneObject;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

/**
 * Created by Will on 2/8/14.
 */
public class Quad extends SceneObject {

    private static final float[]  vertices = {
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,
    };


    private static final float[] colors = {
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f,
            1f, 1f, 1f, 1f,
    };

    private static final float[] texCoords = {
            0, 0, 0, 1, 1, 1, 1, 0
    };

    private static final int[] indices = {
            0, 1, 2, 0, 2, 3,
    };

    private static final List<VertexAttribute> meshDescriptor = new ArrayList<VertexAttribute>();

    static {
        meshDescriptor.add(new VertexAttribute(vertices, 3));
        meshDescriptor.add(new VertexAttribute(colors, 4));
        meshDescriptor.add(new VertexAttribute(texCoords, 2));
    }

    public Quad(ShaderProgram shader) {
        this(1, 1, shader);
    }

    public Quad(float width, float height, ShaderProgram shader) {
        this(0, 0, width, height, shader);
    }

    public Quad(float x, float y, float width, float height, ShaderProgram shader) {
        super(new Vector3(0, 0, 0), new Vector3(1, 1, 1), new Mesh(indices ,meshDescriptor), shader);
    }

    public void render(int delta, Camera camera) {
      //  super.beginRendering();
       // int viewUniform = shader.getUniformLocation("view");
       // int projectionUniform = shader.getUniformLocation("projection");
       // int scaleUniform = shader.getUniformLocation("scale");
      //  FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
       // camera.getView().store(matrix44Buffer);
       // matrix44Buffer.flip();
       // GLES20.glUniformMatrix4(viewUniform, false, matrix44Buffer);
        //camera.getProjection().store(matrix44Buffer);
       // matrix44Buffer.flip();
        //GLES20.glUniform4fv(projectionUniform, false, matrix44Buffer);
        //GLES20.glUniform2f(scaleUniform, scale.x, scale.y);
        //super.endRendering();
    	
        super.render(delta, camera);
    }
}