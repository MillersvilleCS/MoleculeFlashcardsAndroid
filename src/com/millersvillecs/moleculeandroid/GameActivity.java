package com.millersvillecs.moleculeandroid;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.millersvillecs.moleculeandroid.graphics.Camera;
import com.millersvillecs.moleculeandroid.graphics.opengles.ShaderProgram;

import com.millersvillecs.moleculeandroid.data.CommunicationManager;
import com.millersvillecs.moleculeandroid.data.Molecule;
import com.millersvillecs.moleculeandroid.data.OnCommunicationListener;
import com.millersvillecs.moleculeandroid.data.SDFParser;

import com.millersvillecs.moleculeandroid.graphics.opengles.VAO;

import com.millersvillecs.moleculeandroid.scene.Scene;
import com.millersvillecs.moleculeandroid.scene.SceneObject;
import com.millersvillecs.moleculeandroid.helper.Answer;
import com.millersvillecs.moleculeandroid.helper.Question;


public class GameActivity extends Activity implements OnDismissListener, OnCommunicationListener {
    private static int LOADING = 1, PLAYING = 2, FINISHING = 3;
    
    private GameUIPieces gameUIPieces;
	private GLSurfaceView gLSurfaceView;
	private VAO vao;
	
	private Scene scene;
	private Camera camera;
	private TestRenderer renderer;
	
	final String vertexShader =
		    "uniform mat4 u_MVPMatrix;      \n"     // A constant representing the combined model/view/projection matrix.
		 
		  + "attribute vec4 in_Position;     \n"     // Per-vertex position information we will pass in.
		  + "attribute vec4 in_Color;        \n"     // Per-vertex color information we will pass in.
		 
		  + "varying vec4 v_Color;          \n"     // This will be passed into the fragment shader.
		 
		  + "void main()                    \n"     // The entry point for our vertex shader.
		  + "{                              \n"
		  + "   v_Color = in_Color;          \n"     // Pass the color through to the fragment shader.
		                                            // It will be interpolated across the triangle.
		  + "   gl_Position = in_Position;   \n"     // gl_Position is a special variable used to store the final position
		  + "}                              \n";    // normalized screen coordinates.
						
	final String fragmentShader =
		    "precision mediump float;       \n"     // Set the default precision to medium. We don't need as high of a
		                                            // precision in the fragment shader.
		  + "varying vec4 v_Color;          \n"     // This is the color from the vertex shader interpolated across the
		                                            // triangle per fragment.
		  + "void main()                    \n"     // The entry point for our fragment shader.
		  + "{                              \n"
		  + "   gl_FragColor = v_Color;     \n"     // Pass the color directly through the pipeline.
		  + "}                              \n";

	private String username, auth, gameId, gameSessionId;
	private CommunicationManager comm;
	private ProgressDialog progress;
	private boolean wantedDismiss = false;
	private Question[] questions;
	private Molecule[] molecules;
	private int currentQuestion = 0, lastAnswerIndex = -1, gameState;
	private double score = 0.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
        this.username = intent.getStringExtra(MainActivity.USERNAME);
        this.auth = intent.getStringExtra(MainActivity.AUTH);
        int position = intent.getIntExtra(MainActivity.GAME_INDEX, -1);
        String gamesJSONText = intent.getStringExtra(MainActivity.GAME_JSON);
		
		setContentView(R.layout.activity_game);
		getActionBar().setDisplayHomeAsUpEnabled(false);//no need to check, 4.0+ req on app

		this.gLSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);
		
		
		
		/*
		 * Test render code
		 */
		Map<Integer, String> attributes = new HashMap<Integer, String>();
        attributes.put(0, "in_Position");
        attributes.put(1, "in_Color");
        ShaderProgram shader;
        SceneObject so;
        scene = new Scene();   
		
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
		
		if (supportsEs2) {
			this.gLSurfaceView.setEGLContextClientVersion(2);
			renderer = new TestRenderer();
			this.gLSurfaceView.setRenderer(renderer);
		} else {
			//error?
		}
		
        try {
            shader = new ShaderProgram(vertexShader, fragmentShader, attributes);
            so =new Quad(0.5f, 1, shader);
            scene.attach(so);
        } catch (Exception e) {
            System.out.println("ERRRRRRRRRROOOOOOOOOOORRRR");
        }
		
		this.progress = new ProgressDialog(this);
        this.progress.setCanceledOnTouchOutside(false);
        this.progress.setMessage("Loading questions...");
        this.progress.setOnDismissListener(this);
        this.progress.show();
		
		this.gameUIPieces = new GameUIPieces(this);
		/*temporarily inflate buttons here
		this.gameUIPieces.displayButton(0, "Mythohyponypopolyteen");
		this.gameUIPieces.displayButton(1, "Test.");
		this.gameUIPieces.displayButton(2, "3");
		this.gameUIPieces.displayButton(3, "4");
		this.gameUIPieces.displayButton(4, "4");
		this.gameUIPieces.displayButton(5, "4");
		this.gameUIPieces.displayButton(6, "4");
		this.gameUIPieces.displayButton(7, "4");
		
		this.gameUIPieces.markWrong(2);
		
		this.gameUIPieces.displayQuestionText("This is where a question goes.");
		*/
		
        try{
            JSONArray gamesJSON = new JSONArray(gamesJSONText);
            JSONObject game = (JSONObject)gamesJSON.get(position);
            getActionBar().setTitle(game.getString("name"));
            this.gameId = game.getString("id");
        } catch(JSONException e) {
            e.printStackTrace();
            finish();
            return;
        }
        
        this.gameState = GameActivity.LOADING;
        this.comm = new CommunicationManager(this);
        this.comm.loadFlashcardGame(this.auth, this.gameId);
    }
	
	@Override
	public void onBackPressed() {
	    System.err.println("Cheatin!");
	}
	
	@Override
	public void onPause() {
	    System.err.println("Cheatin!");
	    super.onPause();
	}

    @Override
    public void onRequestResponse(JSONObject response) {
        if(this.gameState == GameActivity.LOADING) {
            try{
                this.gameSessionId = response.getString("game_session_id");
                JSONArray jQuestions = response.getJSONArray("questions");
                this.questions = new Question[jQuestions.length()];
                this.molecules = new Molecule[jQuestions.length()];
                for(int i = 0; i < jQuestions.length(); i++) {
                    JSONObject currentQ = jQuestions.getJSONObject(i);
                    JSONArray currentAs = currentQ.getJSONArray("answers");
                    Answer[] answers = new Answer[currentAs.length()];
                    for(int j = 0; j < answers.length; j++) {
                        answers[j] = new Answer(currentAs.getJSONObject(j).getString("id"),
                                                currentAs.getJSONObject(j).getString("text"));
                    }
                    this.questions[i] = new Question(currentQ.getString("id"),
                                                     currentQ.getString("text"),
                                                     answers);
                }
            } catch(JSONException e) {
                e.printStackTrace();
                finish();
                return;
            }
            
            this.currentQuestion = 0;
            this.progress.setMessage("Loading molecule models...");
            this.comm.getMedia(this.gameSessionId, 0, this.questions[0].getId());
            
        } else if(this.gameState == GameActivity.PLAYING) {
            try{
                this.score = response.getDouble("score");
                boolean correct = response.getBoolean("correct");
                if(correct) {
                    this.lastAnswerIndex = -1;
                    nextQuestion();
                } else {
                    this.gameUIPieces.markWrong(this.lastAnswerIndex);
                    this.lastAnswerIndex = -1;
                }
            } catch(JSONException e) {
                e.printStackTrace();
                finish();
                return;
            }
        } else {
            try{
                this.score = response.getDouble("final_score");
                int rank = response.getInt("rank");
                System.out.println("#" + rank + " " + this.score);
                this.gameUIPieces.displayFinishScreen(this.score, rank);
            } catch(JSONException e) {
                e.printStackTrace();
                finish();
                return;
            }
        }
        
    }

    @Override
    public void onMoleculeResponse(String[] data) {
        this.molecules[this.currentQuestion] = SDFParser.parse(data);
        this.currentQuestion++;
        if(this.currentQuestion < this.molecules.length) {
            this.comm.getMedia(this.gameSessionId, 0, this.questions[0].getId());
        } else {
            this.gameState = GameActivity.PLAYING;
            this.wantedDismiss = true;
            this.progress.dismiss();
            this.currentQuestion = -1;
            nextQuestion();
        }
    }

    @Override
    public void onImageResponse(Bitmap bitmap, boolean error) {}

    @Override
    public void onDismiss(DialogInterface dialog) {
        //should make a way to cancel loading
        if(this.wantedDismiss) {
            this.wantedDismiss = false;
        } else {
            finish();
        }
    }
    
    public void onFinishBack(View view) {
        finish();
    }
    
    public void onAnswerButton(View view) {
        if(this.lastAnswerIndex == -1 && this.currentQuestion < this.questions.length) {
            int index = 0;
            switch(view.getId()) {
                case R.id.game_button_0:
                    index = 0;
                    break;
                case R.id.game_button_1:
                    index = 1;
                    break;
                case R.id.game_button_2:
                    index = 2;
                    break;
                case R.id.game_button_3:
                    index = 3;
                    break;
                case R.id.game_button_4:
                    index = 4;
                    break;
                case R.id.game_button_5:
                    index = 5;
                    break;
                case R.id.game_button_6:
                    index = 6;
                    break;
                case R.id.game_button_7:
                    index = 7;
                    break;
            }
            this.lastAnswerIndex = index;
            Question question = this.questions[this.currentQuestion];
            String answerId = question.getAnswer(index).getId();
            
            this.comm.submitFlashcardAnswer(this.auth, this.gameSessionId, question.getId(), answerId, 1000);//TODO, actual time
        }
        
    }
    
    private void nextQuestion() {
        this.currentQuestion++;
        if(this.currentQuestion < this.questions.length) {
            Question question = this.questions[this.currentQuestion];
            this.gameUIPieces.displayQuestionText(question.getQuestionText());
            this.gameUIPieces.resetButtons();
            Answer[] answers = question.getAnswers();
            for(int i = 0; i < answers.length; i++) {
                this.gameUIPieces.displayButton(i, answers[i].getText());
            }
        } else {
            this.gameState = GameActivity.FINISHING;
            this.comm.endFlashcardGame(this.auth, this.gameSessionId, 120000);//TODO, actual time
        }
    }
}
