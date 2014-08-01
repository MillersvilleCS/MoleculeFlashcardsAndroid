package com.millersvillecs.moleculeandroid.helper;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.millersvillecs.moleculeandroid.R;

/**
 * 
 * @author connor
 * 
 * Convenience class for showing dialogs.
 * 
 */
public class AlertDialog implements OnClickListener {
	
	private android.app.AlertDialog alert;
	private OnConfirmListener listener;
	
	public AlertDialog(Context context, String title, String message) {
		Builder builder = new Builder(context);
		
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.alert_ok, this);
        
        alert = builder.create();
        alert.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(this.listener != null) {
			this.listener.onConfirmResponse(which);
		}
		alert.dismiss();
	}
	
	public void show() {
		alert.show();
	}
	
	public void dismiss() {
		alert.dismiss();
	}
	
	public void setListener(OnConfirmListener listener) {
		this.listener = listener;
	}
}


