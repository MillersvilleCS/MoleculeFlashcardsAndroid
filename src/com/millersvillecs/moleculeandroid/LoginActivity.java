package com.millersvillecs.moleculeandroid;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.millersvillecs.moleculeandroid.data.CommunicationManager;
import com.millersvillecs.moleculeandroid.data.FileHandler;
import com.millersvillecs.moleculeandroid.data.Molecule;
import com.millersvillecs.moleculeandroid.data.OnCommunicationListener;
import com.millersvillecs.moleculeandroid.helper.AlertDialog;

public class LoginActivity extends Activity implements OnCommunicationListener {
	
	private ProgressDialog progress;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            //NavUtils.navigateUpFromSameTask(this);
            finish();//so "up" looks like "back"
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != RESULT_CANCELED) {
			//register completed and not backed out of, go all the way back to main
			finish();
		}
	}
	
	public void onRegisterButton(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivityForResult(intent, 1);
	}
	
	public void onLoginButton(View view) {
		this.progress = new ProgressDialog(this);
		this.progress.setCanceledOnTouchOutside(false);
		this.progress.setMessage(getString(R.string.login_message));
		this.progress.show();
		
		EditText emailBox = (EditText)findViewById(R.id.email);
		EditText passwordBox = (EditText)findViewById(R.id.password);
		
		String email = emailBox.getText().toString();
		String password = passwordBox.getText().toString();
		
		CommunicationManager communication = new CommunicationManager(this);
		communication.login(email, password);
	}

	@Override
	public void onRequestResponse(JSONObject response) {
		this.progress.dismiss();
		try {
			if(response.getBoolean("success")) {
				String username = response.getString("username");
				String auth = response.getString("auth");
				FileHandler fileHandler = new FileHandler(this);
				String[] data = new String[2];
				data[0] = username;
				data[1] = auth;
				fileHandler.write("credentials", data);
				finish();
			} else {
				new AlertDialog(getFragmentManager(), getString(R.string.error_title), 
						response.getString("error")).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			new AlertDialog(getFragmentManager(), getString(R.string.error_title), 
					"Invalid Server Response").show();
		} catch (NullPointerException e) {
			e.printStackTrace();
			new AlertDialog(getFragmentManager(), getString(R.string.error_title), 
					"Invalid Server Response").show();
		}
	}

	@Override
	public void onMoleculeResponse(Molecule molecule) {}

	@Override
	public void onImageResponse(Bitmap bitmap, boolean error) {}
}
