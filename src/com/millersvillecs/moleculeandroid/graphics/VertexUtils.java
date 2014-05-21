package com.millersvillecs.moleculeandroid.graphics;

import java.util.ArrayList;
import java.util.List;

import com.millersvillecs.moleculeandroid.util.math.Vector3;

public class VertexUtils {
	public static float[] vector3ListToFloatArray(List<Vector3> vectorList) {
		List<Float> floatList = new ArrayList<Float>();
		
		for(Vector3 vec3 : vectorList) {
			floatList.add(vec3.x);
			floatList.add(vec3.y);
			floatList.add(vec3.z);
		}
		float[] results = new float[floatList.size()];
		for(int i = 0; i < floatList.size(); ++i) {
			results[i] = floatList.get(i);
		}
		
		return results;
	}
	
	public static float[] ColorListToFloatArray(List<Color> colorList) {
		List<Float> floatList = new ArrayList<Float>();
		
		for(Color color : colorList) {
			floatList.add(color.getRed());
			floatList.add(color.getGreen());
			floatList.add(color.getBlue());
			floatList.add(color.getAlpha());
		}
		float[] results = new float[floatList.size()];
		for(int i = 0; i < floatList.size(); ++i) {
			results[i] = floatList.get(i);
		}
		
		return results;
	}
	
	public static int[] IntegerListToIntArray(List<Integer> integerList) {
		
		int[] results = new int[integerList.size()];
		for(int i = 0; i < integerList.size(); ++i) {
			results[i] = integerList.get(i);
		}
		
		return results;
	}
}
