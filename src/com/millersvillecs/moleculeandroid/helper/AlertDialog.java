package com.millersvillecs.moleculeandroid.helper;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

public class AlertDialog {
	
	private FragmentManager fragmentManager;
	private String title, message;
	
	public AlertDialog(FragmentManager fragmentManager, String title, String message) {
		this.fragmentManager = fragmentManager;
		this.title = title;
		this.message = message;
	}
	
	public void show() {
		DialogFragment error = new AlertDialogFragment();
		Bundle args = new Bundle();
		args.putString("message", this.message);
		args.putString("title", this.title);
		error.setArguments(args);
	    error.show(this.fragmentManager, "error");
	}
}


