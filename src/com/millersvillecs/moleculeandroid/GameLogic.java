package com.millersvillecs.moleculeandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;

import com.millersvillecs.moleculeandroid.data.CommunicationManager;
import com.millersvillecs.moleculeandroid.data.Molecule;
import com.millersvillecs.moleculeandroid.data.MoleculeGamePreferences;
import com.millersvillecs.moleculeandroid.data.OnCommunicationListener;
import com.millersvillecs.moleculeandroid.helper.AlertDialog;
import com.millersvillecs.moleculeandroid.helper.Answer;
import com.millersvillecs.moleculeandroid.helper.OnConfirmListener;
import com.millersvillecs.moleculeandroid.helper.Question;

/**
 * 
 * @author connor
 * 
 * This class handles all the logic of the game. The biggest and most repetitive section is saving
 * or resuming the state of the game. This is necessary because rotations in Android destroy the
 * current activity.
 * 
 * Android's rotations are locked while an item is animating, but any other time it can be rotated by
 * the user.
 * 
 */
public class GameLogic implements OnDismissListener, OnCommunicationListener, OnConfirmListener {
	
	private static int LOADING = 1, PLAYING = 2, FINISHING = 3;
	
	private GameActivity gameActivity;
	private MoleculeGamePreferences preferences;
	
	private ProgressDialog progress;
	private GameUIPieces gameUIPieces;
	private CommunicationManager comm;
	
	private Question[] questions;
	private Molecule[] molecules;
	private Handler timerHandler;
	private Runnable timerRunnable;
	private String auth, gameId, gameSessionId;
	private int currentQuestion = 0, lastAnswerIndex = -1, gameState, rank, score, scoreChange;
	private long timeLimit, timeStart;
	private boolean wantedDismiss = false, timerStop = false;
	
	/**
	 * Get our game preferences and save a reference to our parent.
	 * 
	 * @param gameActivity - parent activity - need to lock orientation, set title, etc.
	 */
	public GameLogic(GameActivity gameActivity) {
		this.gameActivity = gameActivity;
		this.preferences = new MoleculeGamePreferences(gameActivity);
	}
	
	/**
	 * Start our game for the first time - need to load our flashcard JSON.
	 */
	public void start() {
		this.gameActivity.lockOrientation();
		this.progress = new ProgressDialog(this.gameActivity);
        this.progress.setCanceledOnTouchOutside(false);
        this.progress.setMessage("Loading questions...");
        this.progress.setOnDismissListener(this);
        this.progress.show();
        
        this.gameUIPieces = new GameUIPieces(this.gameActivity);
        
        init();
        
        this.scoreChange = 0;
        this.gameState = GameLogic.LOADING;
        
        this.comm = new CommunicationManager(this);
        this.comm.loadFlashcardGame(this.auth, this.gameId);
	}
	
	/**
	 * Reload our game from a saved state.
	 * 
	 * If we rotated while on the ending screen be sure to display that instead.
	 * 
	 * @param state - the state of key variables in our game
	 */
	public void reload(GameFragment state) {
		
		this.questions = state.getQuestions();
		this.currentQuestion = state.getCurrentIndex();
		this.score = state.getScore();
		this.scoreChange = state.getScoreChange();
		this.rank = state.getRank();
		this.gameUIPieces = new GameUIPieces(this.gameActivity);
		this.gameUIPieces.resetButtons(true);
		this.gameUIPieces.updateScore(this.score);
		this.gameUIPieces.updateScoreChange(this.scoreChange, false);
		
		this.progress = new ProgressDialog(this.gameActivity);
        this.progress.setCanceledOnTouchOutside(false);
        this.progress.setMessage("Loading questions...");
        this.progress.setOnDismissListener(this);
        this.comm = new CommunicationManager(this);
        
        if(this.currentQuestion < this.questions.length || this.rank == 0) {
        	this.gameState = GameLogic.PLAYING;
        	
        	this.molecules = state.getMolecules();
    		this.lastAnswerIndex = state.getLastIndex();
    		this.gameSessionId = state.getGameSessionId();
    		int[] buttonStates = state.getButtonStates();
    		
    		init();
        	
    		this.timeLimit = state.getTimeLimit();
    		this.timeStart = state.getTimeStart();
        	this.currentQuestion -= 1;
            this.nextQuestion();
            
            this.gameUIPieces.resetButtons();
    		for(int i = 0; i < buttonStates.length; i++) {
    			if(buttonStates[i] != GameUIPieces.STATE_INVISIBLE) {
    				if(buttonStates[i] == GameUIPieces.STATE_WRONG) {
    					this.gameUIPieces.markWrong(i);
    				}
    			} else {
    				break;
    			}
    		}
    		
    		startUpdatingTime();
        } else {
        	this.gameUIPieces.displayFinishScreen(this.score, this.rank);
        }
	}
	
	/**
	 * Save our state to reload after a rotation.
	 * 
	 * @param state - a reference to a state we can save to
	 */
	public void save(GameFragment state) {
		this.timerHandler.removeCallbacks(this.timerRunnable);
		state.setMolecules(this.molecules);
		state.setQuestions(this.questions);
		state.setCurrentIndex(this.currentQuestion);
		state.setLastIndex(this.lastAnswerIndex);
		state.setScore(this.score);
		state.setScoreChange(this.scoreChange);
		state.setTimeLimit(this.timeLimit);
		state.setTimeStart(this.timeStart);
		state.setSessionId(this.gameSessionId);
		state.setRank(this.rank);
		state.setButtonStates(this.gameUIPieces.getButtonStates());
	}
	
	/**
	 * Cancel the game/request/loading bar
	 */
	public void cancel() {
		this.progress.dismiss();
		this.comm.cancel();
	}
	
	/**
	 * Get some key information from our saved Game JSON such
	 * as the title of the game to display in the application bar.
	 */
	private void init() {
		this.auth = this.preferences.getAuth();
        int position = this.preferences.getPosition();
        String gamesJSONText = this.preferences.getAllGamesJSON();
        
        try{
            JSONArray gamesJSON = new JSONArray(gamesJSONText);
            JSONObject game = (JSONObject)gamesJSON.get(position);
            this.gameActivity.getActionBar().setTitle(game.getString("name"));
            this.gameId = game.getString("id");
            this.timeLimit = Long.parseLong(game.getString("time_limit"));
        } catch(JSONException e) {
            e.printStackTrace();
            this.gameActivity.finish();
            return;
        }
	}
	
	/**
	 * Handler for dismissing the loading (progress) bar
	 */
	@Override
	public void onDismiss(DialogInterface dialog) {
        if(this.wantedDismiss) {
            this.wantedDismiss = false;
        } else {
            this.gameActivity.finish();
        }
	}
	
	/**
	 * If we are in the loading state, the response given is the JSON
	 * for a specific game, such as the text of the questions, the answers
	 * and the molecule we'll need. Build data structures to store these,
	 * then load the molecule models.
	 * 
	 * If we are in the playing state, the response given is a change and score
	 * and whether our answer was right or wrong. After we change the button state
	 * we should unlock the orientation again.
	 * 
	 * Otherwise we are ending the game, and should update our local copy of this
	 * game's highscores, then display the final screen.
	 */
	@Override
	public void onRequestResponse(JSONObject response) {
		if(this.gameState == GameLogic.LOADING) {
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
            	showErrorMessage("Invalid server response.");
            	return;
            } catch(NullPointerException e) {
            	showErrorMessage("Could not connect to the internet.");
            	return;
            }
            
            this.currentQuestion = 0;
            this.progress.setMessage("Loading molecule models...");
            this.comm.getMedia(this.gameSessionId, 0, this.questions[0].getId());
            
        } else if(this.gameState == GameLogic.PLAYING) {
            try{
            	this.scoreChange = response.getInt("score") - this.score;
                this.score = response.getInt("score");
                this.gameUIPieces.updateScore(this.score);
                this.gameUIPieces.updateScoreChange(this.scoreChange, true);
                boolean correct = response.getBoolean("correct");
                
                if(correct) {
                	this.gameUIPieces.markCorrect(this.lastAnswerIndex);
                    Handler handler = new Handler(); 
                    handler.postDelayed(new Runnable() { 
                        public void run() {
                        	lastAnswerIndex = -1;
                            nextQuestion();  
                        } 
                    }, 3000);
                } else {
                    this.gameUIPieces.markWrong(this.lastAnswerIndex);
                    this.lastAnswerIndex = -1;
                    this.gameActivity.unlockOrientation();
                }
            } catch(JSONException e) {
            	showErrorMessage("Invalid server response.");
            	return;
            } catch(NullPointerException e) {
            	showErrorMessage("Could not connect to the internet.");
            	return;
            }
        } else {
            try{
                this.score = response.getInt("final_score");
                this.rank = response.getInt("rank");
                updateHighScores(this.rank);
                this.gameUIPieces.displayFinishScreen(this.score, this.rank);
                this.gameActivity.unlockOrientation();
            } catch(JSONException e) {
            	showErrorMessage("Invalid server response.");
            	return;
            } catch(NullPointerException e) {
            	showErrorMessage("Could not connect to the internet.");
            	return;
            }
        }
	}
	
	/**
	 * Store the molecules we downloaded and parsed.
	 * 
	 * If we are finished downloading them, we can dismiss
	 * the loading screen and start playing the game.
	 */
	@Override
	public void onMoleculeResponse(Molecule molecule) {
		this.molecules[this.currentQuestion] = molecule;
        this.currentQuestion++;
        if(this.currentQuestion < this.molecules.length) {
            this.comm.getMedia(this.gameSessionId, 0, this.questions[this.currentQuestion].getId());
        } else {
        	this.timeStart = System.currentTimeMillis();
        	this.timeLimit = timeStart + this.timeLimit;
        	startUpdatingTime();
        	
            this.wantedDismiss = true;
            this.progress.dismiss();
            this.currentQuestion = -1;
            this.gameState = GameLogic.PLAYING;
            nextQuestion();
        }
	}
	
	/**
	 * Get which button we pressed, then make an API call to see if it is right.
	 * Lock the orientation while the call is happening so our state is not reset.
	 * 
	 * @param view - button view
	 */
	public void onAnswerButton(View view) {
		if(this.lastAnswerIndex == -1 &&
		   this.currentQuestion < this.questions.length &&
		   this.gameState == GameLogic.PLAYING) {
			
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
            this.gameUIPieces.markWorking(index);
            Question question = this.questions[this.currentQuestion];
            String answerId = question.getAnswer(index).getId();
            
            this.gameActivity.lockOrientation();
            this.comm.submitFlashcardAnswer(this.auth, this.gameSessionId, question.getId(), answerId, 1000);
        }
	}
	
	@Override
	public void onImageResponse(Bitmap bitmap, boolean error) {}
	
	/**
	 * Let the renderer get the current molecule.
	 * 
	 * @return - the molecule for the current question
	 */
	public Molecule getCurrentMolecule() {
		if(this.gameState == GameLogic.PLAYING && this.currentQuestion != -1) {
            return this.molecules[this.currentQuestion];
        } else {
            return null;
        }
	}
	
	/**
	 * Update our local high scores data so we don't have to make another API call.
	 * 
	 * @param rank - which rank we are updating/inserting
	 */
	private void updateHighScores(int rank) {
    	try{
            JSONArray gamesJSON = new JSONArray(this.preferences.getAllGamesJSON());
            JSONObject game = (JSONObject)gamesJSON.get(this.preferences.getPosition());
            JSONArray highScoresJSON = game.getJSONArray("high_scores");
            if(rank > highScoresJSON.length() + 1) {
            	return;
            }
            for(int i = highScoresJSON.length() - 1; i >= rank - 1; i--) {
            	JSONObject highScore = highScoresJSON.getJSONObject(i);
            	highScore.put("rank", i + 2);
            	highScoresJSON.put(i + 1, highScore);
            }
            
            JSONObject newScore = new JSONObject(highScoresJSON.getJSONObject(0).toString());
            newScore.put("rank", rank);
            newScore.put("username", preferences.getUsername());
            newScore.put("score", this.score);
            
            highScoresJSON.put(rank - 1, newScore);
            
            game.put("high_scores", highScoresJSON);
            
            this.preferences.setAllGamesJSON(gamesJSON.toString());
            
        } catch(JSONException e) {
            e.printStackTrace();
            this.gameActivity.finish();
        }
    }
	
	/**
	 * Advance to the next question in the game or end the game if this
	 * was the last question.
	 */
	private void nextQuestion() {
        this.currentQuestion++;
        
        if(this.currentQuestion < this.questions.length) {
        	this.gameActivity.unlockOrientation();
            Question question = this.questions[this.currentQuestion];
            this.gameUIPieces.setProgress(((double)(this.currentQuestion + 1) / (double)this.questions.length) * 100);
            this.gameUIPieces.displayQuestionText(question.getQuestionText());
            this.gameUIPieces.resetButtons();
            Answer[] answers = question.getAnswers();
            for(int i = 0; i < answers.length; i++) {
                this.gameUIPieces.displayButton(i, answers[i].getText());
            }
        } else {
            this.gameState = GameLogic.FINISHING;
            this.comm.endFlashcardGame(this.auth, this.gameSessionId, getTimeElapsed());
        }
    }
	
	/**
	 * End the game because the time ran out.
	 */
	private void timeRanOut() {
		//Note: Current bug with time out causes high score to be invalid rank
		this.gameState = GameLogic.FINISHING;
        this.comm.endFlashcardGame(this.auth, this.gameSessionId, getTimeElapsed());
        this.wantedDismiss = true;
	}
	
	/**
	 * Start our timer - have it update once per second
	 */
	private void startUpdatingTime() {
		this.timerHandler = new Handler();
		
		this.timerRunnable = new Runnable() {
			@Override
			public void run() {
				if(timerStop) {
					return;
				}
				
				long time = timeLimit - System.currentTimeMillis();
				gameUIPieces.updateTime(time);
				if(time <= 0) {
					timeRanOut();
				} else {
					timerHandler.postDelayed(this, 1000);
				}
			}
		};
		
		this.timerHandler.postDelayed(timerRunnable, 0);
	}
	
	/**
	 * Calculated elapsed time.
	 * 
	 * @return milliseconds elapsed
	 */
	private long getTimeElapsed() {
		long currTime = System.currentTimeMillis();
		long time = currTime - this.timeStart;
		if(currTime > this.timeLimit) {
			time = this.timeLimit - this.timeStart;
		}
		return time;
	}
	
	private void showErrorMessage(String message) {
		this.timerStop = true;
		
		AlertDialog error = new AlertDialog(this.gameActivity, "Error!", message);
		error.setListener(this);
		error.show();
	}

	@Override
	public void onConfirmResponse(int which) {
		this.gameActivity.finish();
	}
}
