package com.millersvillecs.moleculeandroid;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.millersvillecs.moleculeandroid.data.Molecule;


public class GameActivity extends Activity {
    private GameLogic gameLogic;
	private GLSurfaceView gLSurfaceView;
	
	private AndroidRenderer renderer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
		getActionBar().setDisplayHomeAsUpEnabled(false);//no need to check, 4.0+ req on app

		this.gLSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);
		
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
		
		if (supportsEs2) {
			this.gLSurfaceView.setEGLContextClientVersion(2);
			renderer = new AndroidRenderer(this.getApplicationContext(), this);
			this.gLSurfaceView.setRenderer(renderer);
		} else {
			//error?
			finish();
		}
		
		this.gameLogic = new GameLogic(this);
		this.gameLogic.start();
    }
	
	@Override
	public void onBackPressed() {
	    finish();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	    finish();
	}
    
    public void onFinishBack(View view) {
        finish();
    }
    
    public void onAnswerButton(View view) {
    	this.gameLogic.onAnswerButton(view);
    }
    
    @Deprecated
    public Molecule getCurrentMolecule() {
    	//TODO - Remove and call GameLogic directly - leaving this for Will when he merges.
    	return this.gameLogic.getCurrentMolecule();
    }
}
