package com.millersvillecs.moleculeandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class ErrorDialogFragment extends DialogFragment {
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		String message = getArguments().getString("message");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.error_title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);
        
        return builder.create();
    }
}