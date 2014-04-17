package com.millersvillecs.moleculeandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class CreditsActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
		
		TextView body = (TextView) findViewById(R.id.credits_body_view);
		Spanned formattedText = Html.fromHtml(getString(R.string.credits_body));
		body.setText(formattedText);
	    body.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	public void onNSF(View view)
	{
		String url = "http://www.nsf.gov/awardsearch/showAward?AWD_ID=0968350";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
}
