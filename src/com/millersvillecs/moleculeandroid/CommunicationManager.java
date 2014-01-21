package com.millersvillecs.moleculeandroid;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class CommunicationManager {
	
	private final String REQUEST_HANDLER_URL = "http://docktest.gcl.cis.udel.edu/exscitech_sam/request_handler.php",
						 GET_MEDIA_URL = "http://docktest.gcl.cis.udel.edu/exscitech_sam/get_media.php";
	private OnCommunicationListener callback;
	
	private class Request {
		String url;
		JSONObject params = new JSONObject();
	}
	
	//http://www.androidsnippets.com/executing-a-http-post-request-with-httpclient
	private class ExecutePost extends AsyncTask<Request, Void, JSONObject>{
		
		private CommunicationManager commRef;
		
		public ExecutePost(CommunicationManager cm) {
			this.commRef = cm;
		}
		
		//http://stackoverflow.com/questions/11504467/how-to-https-post-in-android
		private HttpClient createHttpClient() {
		    HttpParams params = new BasicHttpParams();
		    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		    HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		    HttpProtocolParams.setUseExpectContinue(params, true);

		    SchemeRegistry schemeReg = new SchemeRegistry();
		    schemeReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		    schemeReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		    ClientConnectionManager connection = new ThreadSafeClientConnManager(params, schemeReg);

		    return new DefaultHttpClient(connection, params);
		}
		
		@Override
		protected JSONObject doInBackground(Request... params) {
			if(params.length != 1) {
				return null;
			}
			Request request = params[0];
			HttpClient client = createHttpClient();
			HttpPost post = new HttpPost(request.url);
			JSONObject responseJSON = null;
			try {
				StringEntity json = new StringEntity(request.params.toString());
				json.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		        post.setEntity(json);
		        HttpResponse response = client.execute(post);
		        HttpEntity entity = response.getEntity();
		        String responseText = EntityUtils.toString(entity);
		        responseJSON = new JSONObject(responseText);
		    } catch (ClientProtocolException e) {
		    	e.printStackTrace();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    } catch (JSONException e) {
		    	e.printStackTrace();
		    }
			return responseJSON;
		}
		
		@Override
		protected void onPostExecute(JSONObject response) {
			commRef.setResults(response);
		}
	}
	
	public CommunicationManager(OnCommunicationListener callback) {
		this.callback = callback;
	}
	
	public void setResults(JSONObject response) {
		this.callback.onRequestResponse(response);
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
}
