package com.millersvillecs.moleculeandroid;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class ExecuteImageDownload extends AsyncTask<String, Void, Bitmap>{
	
	private CommunicationManager commRef;
	
	public ExecuteImageDownload(CommunicationManager cm) {
		this.commRef = cm;
	}
	
	@Override
	protected Bitmap doInBackground(String... urls) {
		if(urls.length != 1) {
			return null;
		}
		
		Bitmap bitmap = null;
		
		try {
			URL url = new URL(urls[0]);
			bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		commRef.setImageResults(bitmap);
	}
}