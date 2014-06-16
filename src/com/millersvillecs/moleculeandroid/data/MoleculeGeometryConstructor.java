package com.millersvillecs.moleculeandroid.data;

import java.util.ArrayList;
import java.util.Map;

import com.millersvillecs.moleculeandroid.graphics.BondObject;
import com.millersvillecs.moleculeandroid.graphics.Color;
import com.millersvillecs.moleculeandroid.graphics.GeometryUtils;
import com.millersvillecs.moleculeandroid.graphics.Mesh;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;
import com.millersvillecs.moleculeandroid.scene.SceneNode;
import com.millersvillecs.moleculeandroid.scene.SceneObject;
import com.millersvillecs.moleculeandroid.util.math.Vector3;

public class MoleculeGeometryConstructor {
	
	public static SceneNode construct(ArrayList<Atom> atoms, ArrayList<Bond> bonds,int atomQuality, int bondQuality, ShaderProgram atomShader,  ShaderProgram bondShader, Map<String, Color> colorAtlas,Map<String, Float> radiusMap) {
    	SceneNode moleculeNode = new SceneNode();
    	for(Atom atom: atoms) {
    		Color atomColor =colorAtlas.get(atom.getType());
    		Mesh sphere = GeometryUtils.createSphereGeometry(((float)radiusMap.get(atom.getType()) / 4), atomQuality, atomQuality, atomColor);
    		SceneObject atomObject = new SceneObject(sphere, atomShader);
    		atomObject.translate(atom.getPosition());
    		moleculeNode.attach(atomObject);
    	}

    	for(Bond bond : bonds) {
    		
    		Atom from = atoms.get(bond.getFrom());
    		Atom to = atoms.get(bond.getTo());
    		
    		Color fromColor = colorAtlas.get(from.getType());
    		Color toColor = colorAtlas.get(to.getType());
    		
    		Vector3 distance = from.getPosition().clone().sub(to.getPosition());
    		
    		final Vector3 directionA = Vector3.DOWN.clone().normalize();
    		final Vector3 directionB = distance.clone().normalize();
    		
    		//find the angle to rotate in radians and convert to degrees
    		float angle = (float) Math.acos(directionA.dot(directionB));
    		angle = (float) Math.toDegrees(angle);
    		
    		final Vector3 rotationAxis = directionA.clone().cross(directionB).normalize();
    		
    		//geometry used for the bonds
    		final Mesh cylinder = GeometryUtils.createCylinderGeometry(0.08f, distance.length(), 20, fromColor, toColor);
    		
    		switch(bond.getType()) {
    			case SINGLE:
    			
        		SceneObject bondObject = new BondObject(cylinder, bondShader, fromColor, toColor);
        		bondObject.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
        		bondObject.setTranslation(from.getPosition());
        		moleculeNode.attach(bondObject);
        		break;
    			case DOUBLE:
    			
        		SceneObject bondObject1 = new BondObject(cylinder, bondShader, fromColor, toColor);
        		bondObject1.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
        		bondObject1.setTranslation(new Vector3(from.getX() + 0.15f, from.getY() + 0.15f, from.getZ()));
        		
        		SceneObject bondObject2 = new BondObject(cylinder, bondShader, fromColor, toColor);
        		bondObject2.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
        		bondObject2.setTranslation(new Vector3(from.getX() - 0.15f, from.getY() - 0.15f, from.getZ()));
        		
        		moleculeNode.attach(bondObject1);
        		moleculeNode.attach(bondObject2);
        		break;
    			case TRIPLE:
    
        		SceneObject bondObject3 = new BondObject(cylinder, bondShader, fromColor, toColor);
        		bondObject3.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
        		bondObject3.setTranslation(new Vector3(from.getX()+ 0.25f, from.getY() + 0.25f, from.getZ()));
        		
        		SceneObject bondObject4 = new BondObject(cylinder, bondShader, fromColor, toColor);
        		bondObject4.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
        		bondObject4.setTranslation(new Vector3(from.getX() - 0.25f, from.getY() - 0.25f, from.getZ()));
        		
        		SceneObject bondObject5 = new BondObject(cylinder, bondShader, fromColor,toColor);
        		bondObject5.rotate(angle, rotationAxis.x ,rotationAxis.y, rotationAxis.z);
        		bondObject5.setTranslation(new Vector3(from.getX(), from.getY(), from.getZ()));
        		
        		moleculeNode.attach(bondObject3);
        		moleculeNode.attach(bondObject4);
        		moleculeNode.attach(bondObject5);
    		}
    	}
    	return moleculeNode;
    }
}
