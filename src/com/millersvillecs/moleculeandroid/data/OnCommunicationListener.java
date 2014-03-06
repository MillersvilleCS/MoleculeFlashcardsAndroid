package com.millersvillecs.moleculeandroid.data;

import org.json.JSONObject;

import android.graphics.Bitmap;

public interface OnCommunicationListener {
	public void onRequestResponse(JSONObject response);
	public void onMoleculeResponse(String[] data);
	public void onImageResponse(Bitmap bitmap, boolean error);
}

