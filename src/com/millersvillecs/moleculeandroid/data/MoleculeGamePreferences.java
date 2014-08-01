package com.millersvillecs.moleculeandroid.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author connor
 * 
 * Stores game preferences across all activities in our class.
 * 
 */
public class MoleculeGamePreferences {
	private static final String USERNAME = "USERNAME";
	private static final String AUTH = "AUTH";
	private static final String ALL_GAMES_JSON = "ALL_GAMES_JSON";
	private static final String POSITION = "POSITION";
	
	private SharedPreferences preferences;
	private Editor editor;
	
	public MoleculeGamePreferences(Context context) {
		this.preferences = 
			context.getSharedPreferences(MoleculeGamePreferences.class.getSimpleName(), Activity.MODE_PRIVATE);
		this.editor = this.preferences.edit();
	}
	
	public String getUsername() {
		return this.preferences.getString(MoleculeGamePreferences.USERNAME, "");
	}
	
	public String getAuth() {
		return this.preferences.getString(MoleculeGamePreferences.AUTH, "");
	}
	
	public String getAllGamesJSON() {
		return this.preferences.getString(MoleculeGamePreferences.ALL_GAMES_JSON, "");
	}
	
	public int getPosition() {
		return this.preferences.getInt(MoleculeGamePreferences.POSITION, -1);
	}
	
	public void setUsername(String s) {
		editor.putString(MoleculeGamePreferences.USERNAME, s);
		editor.commit();
	}
	
	public void setAuth(String s) {
		editor.putString(MoleculeGamePreferences.AUTH, s);
		editor.commit();
	}
	
	public void setAllGamesJSON(String s) {
		editor.putString(MoleculeGamePreferences.ALL_GAMES_JSON, s);
		editor.commit();
	}
	
	public void setPosition(int i) {
		editor.putInt(MoleculeGamePreferences.POSITION, i);
		editor.commit();
	}
}
