package com.millersvillecs.moleculeandroid;

import java.io.IOException;

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

//http://www.androidsnippets.com/executing-a-http-post-request-with-httpclient
public class ExecutePost extends AsyncTask<Request, Void, JSONObject>{
	
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
		commRef.setJSONResults(response);
	}
}
