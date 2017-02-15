package com.iotek.fgs.connweb;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Connection {
	public static String getData(String userUrl)  throws IOException{
		URL url = new URL(userUrl);
		URLConnection conn = url.openConnection();
	
//		HttpGet get = new HttpGet(userUrl);
//		HttpClient client = new DefaultHttpClient();
//		HttpResponse response = client.execute(get);
//		int status = response.getStatusLine().getStatusCode();
//		if(status == HttpStatus.SC_OK){
//			InputStream is = response.getEntity().getContent();
		InputStream is = conn.getInputStream();
			String data = GetData.getData(is);
			is.close();
			return data;
//		}
		
//		return null;
	}
}
