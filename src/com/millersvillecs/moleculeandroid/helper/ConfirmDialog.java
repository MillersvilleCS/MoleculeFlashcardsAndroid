package com.millersvillecs.moleculeandroid.helper;

import com.millersvillecs.moleculeandroid.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class ConfirmDialog implements OnClickListener{
	
	public static final int POSITIVE = AlertDialog.BUTTON_POSITIVE,
							NEGATIVE = AlertDialog.BUTTON_NEGATIVE;
	
	private AlertDialog confirm;
	private OnConfirmListener listener;
	
	public ConfirmDialog(Context context, String title, String message) {
		Builder builder = new AlertDialog.Builder(context);
		
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.confirm_positive, this);
        builder.setNegativeButton(R.string.confirm_negative, this);
        
        confirm = builder.create();
        confirm.show();
	}
	
	public ConfirmDialog(Context context, String title, String message,
						 int positiveMessageRes, int negativeMessageRes) {
		Builder builder = new AlertDialog.Builder(context);
		
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveMessageRes, this);
        builder.setNegativeButton(negativeMessageRes, this);
        
        confirm = builder.create();
        confirm.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(this.listener != null) {
			this.listener.onConfirmResponse(which);
		}
		confirm.dismiss();
	}
	
	public void show() {
		confirm.show();
	}
	
	public void dismiss() {
		confirm.dismiss();
	}
	
	public void setListener(OnConfirmListener listener) {
		this.listener = listener;
	}
}
