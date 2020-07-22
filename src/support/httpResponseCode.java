package support;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpResponseCode {
	
	public static String getResponseCode (String urlString) throws MalformedURLException, IOException {
		URL url = new URL(urlString);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("GET");
		huc.connect();
		return huc.getResponseCode() + " " + huc.getResponseMessage();
	}
}
