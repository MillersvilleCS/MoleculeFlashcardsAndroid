package com.millersvillecs.moleculeandroid;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.millersvillecs.moleculeandroid.data.CommunicationManager;
import com.millersvillecs.moleculeandroid.data.FileHandler;
import com.millersvillecs.moleculeandroid.data.Molecule;
import com.millersvillecs.moleculeandroid.data.OnCommunicationListener;
import com.millersvillecs.moleculeandroid.helper.ErrorDialog;

public class RegisterActivity extends Activity implements OnCommunicationListener {
	
	private CommunicationManager comm;
	private ProgressDialog progress;
	private EditText emailField, usernameField, passwordField, passwordConfirmField;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_register);
        
        this.comm = new CommunicationManager(this);
        
        this.emailField = (EditText)findViewById(R.id.register_email);
        this.usernameField = (EditText)findViewById(R.id.register_username);
        this.passwordField = (EditText)findViewById(R.id.register_password);
        this.passwordConfirmField = (EditText)findViewById(R.id.register_password_confirm);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            //NavUtils.navigateUpFromSameTask(this);
        	setResult(RESULT_CANCELED);
            finish();//so "up" looks like "back"
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();
	}
	
	public void onRegisterButton(View view) {
		String email = this.emailField.getText().toString();
		String username = this.usernameField.getText().toString();
		String password = this.passwordField.getText().toString();
		String passwordConfirm = this.passwordConfirmField.getText().toString();
		if(password.equals(passwordConfirm)) {
			this.progress = new ProgressDialog(this);
			this.progress.setCanceledOnTouchOutside(false);
			this.progress.setMessage(getString(R.string.register_message));
			this.progress.show();
			this.comm.register(email, password, username);
		} else {
			this.passwordField.setText("");
			this.passwordConfirmField.setText("");
			new ErrorDialog(getFragmentManager(), getString(R.string.password_error)).show();
		}
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
				setResult(RESULT_OK);
				finish();
			} else {
				new ErrorDialog(getFragmentManager(), response.getString("error")).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			new ErrorDialog(getFragmentManager(), "Invalid Server Response").show();
		} catch (NullPointerException e) {
			e.printStackTrace();
			new ErrorDialog(getFragmentManager(), "Invalid Server Response").show();
		}
	}

	@Override
	public void onMoleculeResponse(Molecule molecule) {}

	@Override
	public void onImageResponse(Bitmap bitmap, boolean error) {}
}