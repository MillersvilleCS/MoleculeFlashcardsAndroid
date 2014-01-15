package com.millersvillecs.moleculeandroid.util;

import java.util.ArrayList;
import java.util.List;


public class Node<T> implements Cloneable{
	
	private List<Node<T>> subnodes;
	
	public Node(){
		this(new ArrayList<Node<T>>());
	}
	
	public Node(List<Node<T>> subnodes){
		this.subnodes = subnodes;
	}
	
	public void attach(Node<T> node){
		subnodes.add(node);
	}

	public void clearNodes(){
		subnodes.clear();
	}

	public void detach(Node<T> node){
		subnodes.remove(node);
	}

	public void deepDetach(T node){
		subnodes.remove(node);
		for(Node<T> i : subnodes){
			i.deepDetach(node);
		}
	}
	
	public boolean hasNode(Node<T> node) {
		return subnodes.contains(node);
	}
	
	public List<Node<T>> getSubnodes() {
		return subnodes;
	}
	
	public void setSubnodes(List<Node<T>> subnodes) {
		this.subnodes = subnodes;
	}
	
	public Node<T> clone(){
		Node<T> coppiedNode = new Node<T>();
		for(Node<T> subnode : this.getSubnodes()){
			coppiedNode.attach(subnode.clone());
		}
		return coppiedNode;
	}
}