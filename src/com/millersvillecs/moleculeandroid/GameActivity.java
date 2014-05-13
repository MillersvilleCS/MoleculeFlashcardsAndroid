package com.millersvillecs.moleculeandroid;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import com.millersvillecs.moleculeandroid.data.Molecule;


public class GameActivity extends Activity {
	
	private static final String FRAGMENT_TAG = "data";
	
	private GameFragment gameState;
	
    private GameLogic gameLogic;
	private GLSurfaceView gLSurfaceView;
	private AndroidRenderer renderer;
	
	private int orientation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
		getActionBar().setDisplayHomeAsUpEnabled(false);//no need to check, 4.0+ req on app
		this.orientation = getResources().getConfiguration().orientation;
		
		this.gLSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);
		
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
		
		System.out.println("Your OpenGL Version is: " + GLES20.glGetString(GLES20.GL_VERSION));
		System.out.println("Your Shader Language version is: " + 
							GLES20.glGetString(GLES20.GL_SHADING_LANGUAGE_VERSION));
		
		if (supportsEs2) {
			this.gLSurfaceView.setEGLContextClientVersion(2);
			renderer = new AndroidRenderer(this.getApplicationContext(), this);
			this.gLSurfaceView.setRenderer(renderer);
			
			FragmentManager manager = getFragmentManager();
	        this.gameState = (GameFragment) manager.findFragmentByTag(GameActivity.FRAGMENT_TAG);

	        if (this.gameState == null) {
	            gameState = new GameFragment();
	            manager.beginTransaction().add(gameState, GameActivity.FRAGMENT_TAG).commit();
	            
	            this.gameLogic = new GameLogic(this);
	    		this.gameLogic.start();
	        } else {
	        	this.gameLogic = new GameLogic(this);
	    		this.gameLogic.reload(this.gameState);
	        }
		} else {
			System.err.println("This device does not support Android OpenGL ES 2.0");
			finish();
		}
		
    }
	
	@Override
	public void onBackPressed() {
		this.gameLogic.cancel();
	    finish();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(this.orientation == getResources().getConfiguration().orientation) {
			this.gameLogic.cancel();
			finish();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!isFinishing()) {
			this.gameLogic.save(this.gameState);
		}
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
    
    public void lockOrientation() {
    	WindowManager w = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    	switch (w.getDefaultDisplay().getRotation()) {
    		case Surface.ROTATION_0:
    			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    			break;
    		case Surface.ROTATION_90:
    			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    			break;
    		case Surface.ROTATION_180:
    			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
    			break;
    		default:
    			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
    			break;
    	}    	
    }
    
    public void unlockOrientation() {
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }
}
