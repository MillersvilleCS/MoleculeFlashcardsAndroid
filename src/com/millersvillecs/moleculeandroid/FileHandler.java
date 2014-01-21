package com.millersvillecs.moleculeandroid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileHandler {
	
	public static final String IMAGE_PNG = Bitmap.CompressFormat.PNG.name(),
							   IMAGE_JPEG = Bitmap.CompressFormat.JPEG.name();
	
	private String directory, tempDirectory;
	
	public FileHandler(Context context) {
		ContextWrapper wrapper = new ContextWrapper(context);
		this.directory = wrapper.getDir("media", Context.MODE_PRIVATE).getAbsolutePath() + File.separator;
		this.tempDirectory = wrapper.getCacheDir().getAbsolutePath() + File.separator;
	}
	
	private boolean writeParent(File file, String[] data) {
		try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		    for(int i = 0; i < data.length; i++) {
		    	writer.write(data[i]);
		    	writer.newLine();
		    }
		    
		    writer.close();
		    
		    return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String[] readParent(File file) {
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(file));
		    String result = "", line;

		    while ((line = reader.readLine()) != null) {
		    	result += line + "\n";
		    }
		    
		    reader.close();
		    
		    return result.split("\n");
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean write(String filename, String[] data) {
		File file = new File(this.directory + filename);
		return writeParent(file, data);
	}
	
	public String[] read(String filename) {
		File file = new File(this.directory + filename);
		return readParent(file);
	}
	
	public boolean delete(String filename) {
		return new File(this.directory + filename).delete();
	}
	
	public boolean writeTemp(String filename, String[] data) {
		File file = new File(this.tempDirectory + filename);
		return writeParent(file, data);
	}
	
	public String[] readTemp(String filename) {
		File file = new File(this.tempDirectory + filename);
		return readParent(file);
	}
	
	public boolean deleteTemp(String filename) {
		return new File(this.tempDirectory + filename).delete();
	}
	
	/* Images */
	
	public boolean writeTempImage(Bitmap bitmap, String filename) {
		return writeTempImage(bitmap, filename, FileHandler.IMAGE_JPEG, 90);
	}
	
	public boolean writeTempImage(Bitmap bitmap, String filename, String type) {
		return writeTempImage(bitmap, filename, type, 90);
	}
	
	public boolean writeTempImage(Bitmap bitmap, String filename, String type, int compressionAmount) {
		Bitmap.CompressFormat format = Bitmap.CompressFormat.valueOf(type);
		try {
			FileOutputStream out = new FileOutputStream(this.tempDirectory + filename);
		    bitmap.compress(format, compressionAmount, out);
		    out.close();
		    return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Bitmap readTempImage(String filename) {
		//possibly have issues with clear PNG's?
		//BitmapFactory.Options options = new BitmapFactory.Options();
		//options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		//Bitmap bitmap = BitmapFactory.decodeFile(this.tempDirectory + filename, options);
		
		return BitmapFactory.decodeFile(this.tempDirectory + filename);
	}
}
