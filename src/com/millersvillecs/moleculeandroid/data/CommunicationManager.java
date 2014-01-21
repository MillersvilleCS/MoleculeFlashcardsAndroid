package com.millersvillecs.moleculeandroid.data;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;


import android.graphics.Bitmap;

public class CommunicationManager {
	
	private final String REQUEST_HANDLER_URL = "http://docktest.gcl.cis.udel.edu/exscitech_sam/request_handler.php",
						 GET_MEDIA_URL = "http://docktest.gcl.cis.udel.edu/exscitech_sam/get_media.php";
	private OnCommunicationListener callback;
	
	public CommunicationManager(OnCommunicationListener callback) {
		this.callback = callback;
	}
	
	public void setJSONResults(JSONObject response) {
		this.callback.onRequestResponse(response);
	}
	
	public void setResourceresults(Bitmap bitmap) {
		this.callback.onResourceResponse(bitmap);
	}
	
	public void setImageResults(Bitmap bitmap) {
		this.callback.onImageResponse(bitmap);
	}
	
	//http://www.mail-archive.com/android-beginners@googlegroups.com/msg18680.html
	private static String md5Hash(String s) {
        MessageDigest m = null;

        try {
                m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
        }

        m.update(s.getBytes(), 0, s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
	}
	
	public void login(String email, String password) {
		Request request = new Request();
		String passwordHash = md5Hash(password + email);
		request.url = this.REQUEST_HANDLER_URL;
		try {
			request.params.put("request_type", "login");
			request.params.put("email", email);
			request.params.put("hash", passwordHash);
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
	
	public void loadFlashcardGame(String auth, int gameId) {
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
	
	public void endFlashcardGame(String auth, String gameSessionId, String gameTime) {
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
	
	public void submitFlashcardAnswer(String auth, String gameSessionId, int questionId, int answer, int gameTime) {
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
	
	public void getMedia(String gameSessionId, int mediaType, int questionId) {
		Request request = new Request();
		request.url = this.GET_MEDIA_URL;
		try {
			request.params.put("gsi", gameSessionId);
			request.params.put("mt", mediaType);
			request.params.put("qid", questionId);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		new ExecuteGet(this).execute(request);
	}
	
	public void downloadImage(String url) {
		new ExecuteImageDownload(this).execute(url);
	}
}
