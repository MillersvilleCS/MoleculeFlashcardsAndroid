package com.millersvillecs.moleculeandroid.graphics;

import com.millersvillecs.moleculeandroid.util.RangeUtils;


/**
 * @author William Gervasio
 */

public class Color implements Cloneable {

    public static final float MINIMUM_CHANNEL_VALUE = 0.0f;
    public static final float MAXIMUM_CHANNEL_VALUE = 1.0f;

    private float r, g, b, a;

    public Color() {
        this(MINIMUM_CHANNEL_VALUE, MINIMUM_CHANNEL_VALUE, MINIMUM_CHANNEL_VALUE, MAXIMUM_CHANNEL_VALUE);
    }
    
    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }

    public Color(float r, float g, float b, float a) {
        set(r, g, b, a);
    }

    public Color brighten(float percentage) {
        percentage += 1;

        r *= percentage;
        g *= percentage;
        b *= percentage;

        return clamp();
    }

    public Color darken(float percentage) {
        percentage = 1 - percentage;

        r *= percentage;
        g *= percentage;
        b *= percentage;

        return clamp();
    }

    public final Color set(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;

        return clamp();
    }

    public Color setRed(float r) {
        this.r = RangeUtils.forceIntoRange(r, MINIMUM_CHANNEL_VALUE, MAXIMUM_CHANNEL_VALUE);
        return this;
    }

    public float getRed() {
        return r;
    }

    public Color setGreen(float g) {
        this.g = RangeUtils.forceIntoRange(g, MINIMUM_CHANNEL_VALUE, MAXIMUM_CHANNEL_VALUE);
        return this;
    }

    public float getGreen() {
        return g;
    }

    public Color setBlue(float b) {
        this.b = RangeUtils.forceIntoRange(b, MINIMUM_CHANNEL_VALUE, MAXIMUM_CHANNEL_VALUE);
        return this;
    }

    public float getBlue() {
        return b;
    }

    public Color setAlpha(float a) {
        this.a = RangeUtils.forceIntoRange(a, MINIMUM_CHANNEL_VALUE, MAXIMUM_CHANNEL_VALUE);
        return this;
    }

    public float getAlpha() {
        return a;
    }

    public float[] getElements() {
        return new float[]{r, g, b, a};
    }

    public Color clone() {
        return new Color(r, g, b, a);
    }

    private Color clamp() {

        r = RangeUtils.forceIntoRange(r, MINIMUM_CHANNEL_VALUE, MAXIMUM_CHANNEL_VALUE);
        g = RangeUtils.forceIntoRange(g, MINIMUM_CHANNEL_VALUE, MAXIMUM_CHANNEL_VALUE);
        b = RangeUtils.forceIntoRange(b, MINIMUM_CHANNEL_VALUE, MAXIMUM_CHANNEL_VALUE);
        a = RangeUtils.forceIntoRange(a, MINIMUM_CHANNEL_VALUE, MAXIMUM_CHANNEL_VALUE);

        return this;
    }
}