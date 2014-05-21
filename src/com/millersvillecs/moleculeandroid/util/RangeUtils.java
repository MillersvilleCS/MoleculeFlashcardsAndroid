package com.millersvillecs.moleculeandroid.util;

/**
 * @author William Gervasio
 */

public class RangeUtils {

    /**
     * Used to force a value is within a given range
     * @param value
     * @param min
     * @param max
     * @return The new limited value
     */
    public static float forceIntoRange(float value, float min, float max) {
    	if(value > max ) {
    		return max;
    	} else if(value < min) {
    		return min;
    	}
    	
    	return value;
    }

    /**
     * Used to make sure a value is within a given range
     * @param value
     * @param min
     * @param max
     * @return True if the value is within range.
     */
    public static boolean isWithinRange(float value, float min, float max) {
        return value <= max ? value >= min ? true : false : false;
    }

}