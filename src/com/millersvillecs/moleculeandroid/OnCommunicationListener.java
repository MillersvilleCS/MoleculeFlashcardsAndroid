package com.millersvillecs.moleculeandroid;

import org.json.JSONObject;

import android.graphics.Bitmap;

public interface OnCommunicationListener {
	public void onRequestResponse(JSONObject response);
	public void onResourceResponse(Bitmap bitmap);
	public void onImageResponse(Bitmap bitmap);
}

