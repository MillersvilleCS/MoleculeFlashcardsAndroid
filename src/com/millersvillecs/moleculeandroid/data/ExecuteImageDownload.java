package com.millersvillecs.moleculeandroid.data;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class ExecuteImageDownload extends AsyncTask<String, Void, Bitmap>{
	
	private CommunicationManager commRef;
	private File imageFile;
	private boolean error;
	
	public ExecuteImageDownload(CommunicationManager cm, File imageFile) {
		this.commRef = cm;
		this.imageFile = imageFile;
	}
	
	@Override
	protected Bitmap doInBackground(String... urls) {
	    this.error = false;
		if(urls.length != 1) {
		    this.error = true;
			return null;
		}
		
		Bitmap bitmap = null;
		
		try {
			URL url = new URL(urls[0]);
			URLConnection connection = url.openConnection();
			long remoteModified = connection.getLastModified();
			long cacheModified = 0;
			if(this.imageFile.exists()) {
			    cacheModified = this.imageFile.lastModified();
			}
			if(cacheModified > remoteModified) {
			    bitmap = null;
			} else {
			    bitmap = BitmapFactory.decodeStream(connection.getInputStream());
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			this.error = true;
		} catch (IOException e) {
			e.printStackTrace();
			this.error = true;
		}
		
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		commRef.setImageResults(bitmap, this.error);
	}
}