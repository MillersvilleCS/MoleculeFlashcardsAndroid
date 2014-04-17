package com.millersvillecs.moleculeandroid;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

public class CreditsActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
		
		TextView body = (TextView) findViewById(R.id.credits_body_view);
		Spanned formattedText = Html.fromHtml(getString(R.string.credits_body));
		body.setText(formattedText);
	}
}
