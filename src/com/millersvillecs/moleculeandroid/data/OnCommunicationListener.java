package com.millersvillecs.moleculeandroid.data;

import org.json.JSONObject;

import android.graphics.Bitmap;

/**
 * 
 * @author connor
 * 
 * Any class that wants to use the CommunicationManager must implement
 * this interface so that the CommunicationManager can pass back the results
 * from the server.
 *
 */
public interface OnCommunicationListener {
	public void onRequestResponse(JSONObject response);
	public void onMoleculeResponse(Molecule molecule);
	public void onImageResponse(Bitmap bitmap, boolean error);
}

