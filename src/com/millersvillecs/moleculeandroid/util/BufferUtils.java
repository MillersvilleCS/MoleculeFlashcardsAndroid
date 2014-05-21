package com.millersvillecs.moleculeandroid.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {
	public static final FloatBuffer createFloatBuffer(int size) {
		return ByteBuffer.allocateDirect(size * DatatypeUtils.FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
	}

	public static final IntBuffer createIntBuffer(int size) {
		return ByteBuffer
				.allocateDirect(size * DatatypeUtils.INTEGER_SIZE_BYTES)
				.order(ByteOrder.nativeOrder()).asIntBuffer();
	}
}
