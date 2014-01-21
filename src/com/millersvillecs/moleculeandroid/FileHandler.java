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
	
	private Context context;
	private String directory;
	
	public FileHandler(Context context) {
		this.context = context;
		ContextWrapper wrapper = new ContextWrapper(context);
		this.directory = wrapper.getDir("media", Context.MODE_PRIVATE).getAbsolutePath() + File.separator;
	}
	
	public boolean write(String filename, String[] data) {
		File file = new File(this.directory + filename);
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
	
	public String[] read(String filename) {
		File file = new File(this.directory + filename);
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
	
	public boolean delete(String filename) {
		return new File(this.directory + filename).delete();
	}
	
	public boolean writeTemp(String filename, byte[] data) {
		return false;
	}
	
	public byte[] readTemp(String filename) {
		return null;
	}
}
