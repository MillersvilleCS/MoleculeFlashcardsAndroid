package com.millersvillecs.moleculeandroid;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

public class ErrorDialog {
	
	private FragmentManager fragmentManager;
	private String message;
	
	public ErrorDialog(FragmentManager fragmentManager, String message) {
		this.fragmentManager = fragmentManager;
		this.message = message;
	}
	
	public void show() {
		DialogFragment error = new ErrorDialogFragment();
		Bundle args = new Bundle();
		args.putString("message", this.message);
		error.setArguments(args);
	    error.show(this.fragmentManager, "error");
	}
}


