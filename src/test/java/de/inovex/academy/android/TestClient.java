package de.inovex.academy.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

public class TestClient extends TestBase {
	private static final String urlPrefix = "http://ec2-50-17-60-135.compute-1.amazonaws.com:9998/";
	private static final String login = "testuser";
	private static final String latitude = "50.967052";
	private static final String longitude = "7.014019";

	private Gson gson = new Gson();

	private Object postRequest(String verb, Object parameter, Class clazz)
			throws ClientProtocolException, IOException {
		HttpResponse response;
		if (parameter == null) {
			response = executeHttpRequest(verb, null);
		} else {
			response = executeHttpRequest(verb, parameter);
		}
		HttpEntity entity = response.getEntity();
		// if (entity != null) {
		InputStream instream = entity.getContent();
		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(instream, writer, "UTF-8");
			return gson.fromJson(writer.toString(), clazz);
		} finally {
			instream.close();
		}
		// }
		// throw new IOException("did not get a parsable return value");
	}

	private boolean postRequest(String verb, Object parameter)
			throws ClientProtocolException, IOException {
		HttpResponse response = executeHttpRequest(verb, parameter);

		return response.getStatusLine().getStatusCode() == 200;
	}

	private HttpResponse executeHttpRequest(String verb, Object parameter)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException {
		HttpPost request;
		if (parameter == null) {
			request = createRequest(verb, null);
		} else {
			request = createRequest(verb, parameter);
		}
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = httpclient.execute(request);
		return response;
	}

	private HttpPost createRequest(String verb, Object parameter)
			throws UnsupportedEncodingException {
		HttpPost request = new HttpPost(urlPrefix + verb);
		if (parameter != null) {
			StringEntity input = new StringEntity(gson.toJson(parameter));
			input.setContentType(MediaType.APPLICATION_JSON);
			request.setEntity(input);
		}
		return request;
	}

	public static void main(String[] args) {
//		User user = new User();
//		user.setLogin(login);
//		UserLocation loc = new UserLocation();
//		loc.setUser(user);
//		loc.setLatitude(latitude);
//		loc.setLongitude(longitude);
//		TestClient client = new TestClient();

		// try {
		// Session session = (Session) client.postRequest("login", user,
		// Session.class);
		// client.postRequest("setlocation", loc);
		// List<UserLocation> userLocationList = (List<UserLocation>) client
		// .postRequest("getalluserlocation", null, UserLocation.class);
		// System.out.println(loc.getLatitude() + ", " + loc.getLongitude());
		// System.out.println(session.getId());
		// System.out.println(userLocationList);
		// printList(userLocationList);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// List<UserLocation> userLocationList =
		// (List<UserLocation>)getHTML("getalluserlocation");
		String result = getHTML("http://ec2-50-17-60-135.compute-1.amazonaws.com:9998/getalluserlocation");
		System.out.println("Result: " + result);
	}

	public static String getHTML(String urlToRead) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
