package com.millersvillecs.moleculeandroid.data;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;


import android.graphics.Bitmap;
import android.os.AsyncTask;

/* NOT COMPLETE */
public class ExecuteGet extends AsyncTask<Request, Void, Bitmap>{
	
	private CommunicationManager commRef;
	
	public ExecuteGet(CommunicationManager cm) {
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
	protected Bitmap doInBackground(Request... requests) {
		if(requests.length != 1) {
			return null;
		}
		Request request = requests[0];
		HttpClient client = createHttpClient();
		HttpGet get = new HttpGet(request.url);
		HttpParams params = client.getParams();
		Bitmap bitmap = null;
		try {
			params.setParameter("gsi", request.params.getString("gsi"));
			params.setParameter("mt", request.params.getString("mt"));
			params.setParameter("qid", request.params.getString("qid"));
			
			HttpResponse response = client.execute(get);
			
			System.out.println("Ran OK!");
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (JSONException e) {
	    	e.printStackTrace();
	    }
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		this.commRef.setResourceresults(bitmap);
	}
}
