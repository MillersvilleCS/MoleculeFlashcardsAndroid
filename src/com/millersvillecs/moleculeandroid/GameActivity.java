package com.millersvillecs.moleculeandroid;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.Surface;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;


public class GameActivity extends Activity implements OnTouchListener {
	
	private static final String FRAGMENT_TAG = "data";
	
	private GameFragment gameState;
	
    private GameLogic gameLogic;
	private GLSurfaceView gLSurfaceView;
	private AndroidRenderer renderer;
	private ScaleGestureDetector scaleGestureDetector;
	private GestureDetector gestureDetector;
	
	private int orientation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
		getActionBar().setDisplayHomeAsUpEnabled(false);//no need to check, 4.0+ req on app
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		initGestureDetectors();
		
		this.orientation = getResources().getConfiguration().orientation;
		
		this.gLSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);
		this.gLSurfaceView.setOnTouchListener(this);
		
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
		
		//System.out.println("Your OpenGL Version is: " + GLES20.glGetString(GLES20.GL_VERSION));
		//System.out.println("Your Shader Language version is: " + GLES20.glGetString(GLES20.GL_SHADING_LANGUAGE_VERSION));
		
		if (supportsEs2) {
			this.gLSurfaceView.setEGLContextClientVersion(2);
			renderer = new AndroidRenderer(this.getApplicationContext());
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
	        this.renderer.setGameLogic(this.gameLogic);
		} else {
			System.err.println("This device does not support Android OpenGL ES 2.0");
			finish();
		}
		
    }
	
	private void initGestureDetectors() {
		this.scaleGestureDetector = new ScaleGestureDetector(this, new OnScaleGestureListener() {

			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				float amount = detector.getScaleFactor() - 1f;
				renderer.zoomMolecule(-amount);
				
				return false;
			}

			@Override
			public boolean onScaleBegin(ScaleGestureDetector detector) {
				return true;
			}

			@Override
			public void onScaleEnd(ScaleGestureDetector detector) {}
			
		});
		
		this.gestureDetector = new GestureDetector(this, new OnGestureListener() {

			@Override
			public boolean onDown(MotionEvent e) {
				renderer.manuallyEditing = true;
				return false;
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				distanceX /= -4f;
				distanceY /= -4f;
				
				renderer.manuallyRotateMolecule(distanceX, distanceY);
				return true;
			}

			@Override
			public void onShowPress(MotionEvent e) {}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}
			
		});
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getPointerCount() >= 2) {
			this.scaleGestureDetector.onTouchEvent(event);
		} else {
			this.gestureDetector.onTouchEvent(event);
		} 
		if(event.getAction() == MotionEvent.ACTION_UP) {
			renderer.manuallyEditing = false;
		}
		return true;
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
