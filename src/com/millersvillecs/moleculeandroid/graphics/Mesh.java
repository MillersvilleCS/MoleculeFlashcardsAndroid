package com.millersvillecs.moleculeandroid.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import android.opengl.GLES20;

import com.millersvillecs.moleculeandroid.graphics.opengles.BufferedObjectUsage;
import com.millersvillecs.moleculeandroid.graphics.opengles.Descriptor;
import com.millersvillecs.moleculeandroid.graphics.opengles.IBO;
import com.millersvillecs.moleculeandroid.graphics.opengles.VAO;
import com.millersvillecs.moleculeandroid.graphics.opengles.VBO;
import com.millersvillecs.moleculeandroid.util.BufferUtils;
import com.millersvillecs.moleculeandroid.util.DatatypeUtils;

/**
 * @author william gervasio
 */

public class Mesh {

    protected VAO vao;

    private List<VertexAttribute> vertexElements;

    public Mesh(int[] indices, List<VertexAttribute> vertexElements) {
        this.vertexElements = vertexElements;

        //create Vertex Buffer
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(countTotalElements());
        for (VertexAttribute element : vertexElements) {
            verticesBuffer.put(element.getData());
        }
        verticesBuffer.flip();
        VBO vbo = new VBO(verticesBuffer, BufferedObjectUsage.STATIC_DRAW);

        //create indexBuffer
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
        IBO ibo = new IBO(indicesBuffer, BufferedObjectUsage.STATIC_DRAW);

        //create the VAO
        vao = new VAO(vbo, ibo, indices.length);
        int offset = 0;
        int i = 0;
        for (VertexAttribute element : vertexElements) {
            int sizeInBytes = element.getElementsPerVertex() * DatatypeUtils.FLOAT_SIZE_BYTES;
            int numberOfVertices = element.getData().length / element.getElementsPerVertex();
            vao.addVertexAttribute(i, new Descriptor(element.getElementsPerVertex(), GLES20.GL_FLOAT, false, sizeInBytes, offset));
            offset += sizeInBytes * numberOfVertices;
            ++i;
        }
        vao.init();
    }

    public void draw() {
        vao.draw();
    }

    private int countTotalElements() {
        int totalElementsCount = 0;
        for (VertexAttribute element : vertexElements) {
            totalElementsCount += element.getData().length;
        }
        return totalElementsCount;
    }
}
