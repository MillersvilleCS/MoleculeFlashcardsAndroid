package com.millersvillecs.moleculeandroid.data;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class CommunicationManager {
	
	private final String REQUEST_HANDLER_URL = "https://exscitech.org/request_handler.php",
						 GET_MEDIA_URL = "https://exscitech.org/get_media.php";
	private OnCommunicationListener callback;
	private boolean cancelled = false;
	
	public CommunicationManager(OnCommunicationListener callback) {
		this.callback = callback;
	}
	
	public void setJSONResults(JSONObject response) {
		if(!this.cancelled) {
			this.callback.onRequestResponse(response);
		}
	}
	
	public void setMoleculeResponse(Molecule molecule) {
		if(!this.cancelled) {
			this.callback.onMoleculeResponse(molecule);
		}
	}
	
	public void setImageResults(Bitmap bitmap, boolean error) {
		if(!this.cancelled) {
			this.callback.onImageResponse(bitmap, error);
		}
	}
	
	public void login(String login, String password) {
		Request request = new Request();
		request.url = this.REQUEST_HANDLER_URL;
		try {
			request.params.put("request_type", "login");
			request.params.put("login", login);
			request.params.put("pass", password);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		new ExecutePost(this).execute(request);
	}
	
	public void availableGames(String auth) {
		Request request = new Request();
		request.url = this.REQUEST_HANDLER_URL;
		try {
			request.params.put("request_type", "get_avail_flashcard_games");
			request.params.put("authenticator", auth);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		new ExecutePost(this).execute(request);
	}
	
	public void loadFlashcardGame(String auth, String gameId) {
		Request request = new Request();
		request.url = this.REQUEST_HANDLER_URL;
		try {
			request.params.put("request_type", "load_flashcard_game");
			request.params.put("authenticator", auth);
			request.params.put("game_id", gameId);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		new ExecutePost(this).execute(request);
	}
	
	public void endFlashcardGame(String auth, String gameSessionId, int gameTime) {
		Request request = new Request();
		request.url = this.REQUEST_HANDLER_URL;
		try {
			request.params.put("request_type", "end_flashcard_game");
			request.params.put("authenticator", auth);
			request.params.put("game_session_id", gameSessionId);
			request.params.put("game_time", gameTime);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		new ExecutePost(this).execute(request);
	}
	
	public void submitFlashcardAnswer(String auth, String gameSessionId, String questionId, String answer, int gameTime) {
		Request request = new Request();
		request.url = this.REQUEST_HANDLER_URL;
		try {
			request.params.put("request_type", "submit_flashcard_answer");
			request.params.put("authenticator", auth);
			request.params.put("game_session_id", gameSessionId);
			request.params.put("question_id", questionId);
			request.params.put("answer", answer);
			request.params.put("game_time", gameTime);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		new ExecutePost(this).execute(request);
	}
	
	public void getHighScores(String gameId, int startingRank, int range) {
		Request request = new Request();
		request.url = this.REQUEST_HANDLER_URL;
		try {
			request.params.put("request_type", "get_high_scores");
			request.params.put("game_id", gameId);
			request.params.put("starting_rank", startingRank);
			request.params.put("range", range);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		new ExecutePost(this).execute(request);
	}
	
	public void getMedia(String gameSessionId, int mediaType, String questionId) {
		//TODO - change media type to download image if question set uses image
		Request request = new Request();
		request.url = this.GET_MEDIA_URL;
		try {
			request.params.put("gsi", gameSessionId);
			request.params.put("mt", mediaType);
			request.params.put("qid", questionId);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		new ExecuteMoleculeDownload(this).execute(request);
	}
	
	public void downloadImage(String url, File imageFile) {
		new ExecuteImageDownload(this, imageFile).execute(url);
	}
	
	public void cancel() {
		this.cancelled = true;
	}
}
