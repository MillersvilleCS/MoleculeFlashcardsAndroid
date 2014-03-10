package com.millersvillecs.moleculeandroid.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
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

import android.os.AsyncTask;

public class ExecuteGet extends AsyncTask<Request, Void, String[]>{
	
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
	protected String[] doInBackground(Request... requests) {
		if(requests.length != 1) {
			return null;
		}
		Request request = requests[0];
		String[] data = new String[0];
		HttpClient client = createHttpClient();
		try {
    		HttpGet get = new HttpGet(request.url + "?gsi=" + request.params.getString("gsi") +
    		                                        "&mt=" + request.params.getString("mt") +
    		                                        "&qid=" + request.params.getString("qid"));
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			/* TODO: Switch to passing StringBuffer and using in-line scanning methods? - Performance? */
            StringBuffer content = new StringBuffer();
            String line = " ";
            while(line != null && !line.contains("M  END")){
                content.append(line);
                content.append("\n");
                line = reader.readLine();
            }
            reader.close();
            data = content.substring(0, content.length()).split("\n");
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (JSONException e) {
	    	e.printStackTrace();
	    }
		return data;
	}
	
	@Override
	protected void onPostExecute(String[] data) {
		this.commRef.setMoleculeResponse(data);
	}
}
