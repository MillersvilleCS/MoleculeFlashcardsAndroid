package com.millersvillecs.moleculeandroid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.content.ContextWrapper;

public class FileHandler {
	
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
}
