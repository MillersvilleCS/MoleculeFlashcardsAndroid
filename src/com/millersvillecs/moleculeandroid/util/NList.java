package com.millersvillecs.moleculeandroid.util;

import java.util.LinkedList;

@SuppressWarnings("serial")
public class NList<E> extends LinkedList<E> {
	
	public NList() {

	}

	public void pushDown(int index) {
		if (super.size() > 0) {
			if (index >= super.size()) {
				throw new IndexOutOfBoundsException(
						"index exceeds list bounds.");
			} else {
				if (super.get(index).equals(super.getFirst())) {

				} else {
					E placeHolder = super.get(index);
					super.set(index, super.get(index - 1));
					super.set(index - 1, placeHolder);
				}
			}
		}
	}

	public void pullUp(int index) {
		if (super.size() > 0) {
			if (index >= super.size()) {
				throw new IndexOutOfBoundsException(
						"index exceeds list bounds.");
			} else {
				if (super.get(index).equals(super.getLast())) {

				} else {
					E placeHolder = super.get(index);
					super.set(index, super.get(index + 1));
					super.set(index + 1, placeHolder);
				}
			}
		}
	}
}
