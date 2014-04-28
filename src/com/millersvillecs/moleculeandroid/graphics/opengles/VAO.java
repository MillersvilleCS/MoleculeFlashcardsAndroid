package com.millersvillecs.moleculeandroid.graphics.opengles;

import java.util.HashMap;
import java.util.Map;

import android.opengl.GLES20;


/**
* @author William Gervasio
*/

public class VAO {
   private final Map<Integer, Descriptor> descriptors = new HashMap<Integer, Descriptor>();

   private final VBO vbo;
   private final IBO ibo;
   private final int size;
   private int handle = -1;

   public VAO(VBO vbo, IBO ibo, int size) {
       this.vbo = vbo;
       this.ibo = ibo;
       this.size = size;
   }

   public void init() {
	   


   }

   public final void addVertexAttribute(final int index, final Descriptor descriptor) {
       descriptors.put(index, descriptor);
   }

   public void draw() {
       vbo.bind();
       
       for (int i : descriptors.keySet()) {

           final Descriptor descriptor = descriptors.get(i);

           GLES20.glEnableVertexAttribArray(i);
           GLES20.glVertexAttribPointer(i, descriptor.getSize(),
                   descriptor.getType(), descriptor.isNormalized(),
                   descriptor.getStride(), descriptor.getPointer());

       }
       
       
	   ibo.bind();
      // GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, size);
       GLES20.glDrawElements(GLES20.GL_TRIANGLES, size, GLES20.GL_UNSIGNED_INT, 0);
       vbo.unbind();
       ibo.unbind();
   }
}
