package com.millersvillecs.moleculeandroid.graphics.opengles;

/**
 * Descriptors are used to identify the different elements of a vertex in a VBO.
 * For example one might want position, normal, and color in the same buffer.
 * 
 * @author William Gervasio
 */
public class Descriptor {

	private final int size;
	private final int type;
	private final boolean normalized;
	private final int stride;
	private final int pointer;

	/**
	 * 
	 * @param size
	 *            The number of components (i.e. 3 for x,y,z coordinates)
	 * @param type
	 *            The type of data being read (i.e. GL_FLOAT)
	 * @param normalized
	 *            Should the data be normalized when read
	 * @param stride
	 *            The number of bytes between attributes
	 * @param pointer
	 *            The offset of the first component
	 */
	public Descriptor(final int size, final int type, final boolean normalized,
			final int stride, final int pointer) {
		this.size = size;
		this.type = type;
		this.normalized = normalized;
		this.stride = stride;
		this.pointer = pointer;
	}

	public int getSize() {
		return size;
	}

	public int getType() {
		return type;
	}

	public boolean isNormalized() {
		return normalized;
	}

	public int getStride() {
		return stride;
	}

	public int getPointer() {
		return pointer;
	}
}
