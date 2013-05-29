package de.inovex.academy.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

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

import de.inovex.academy.android.server.dto.User;
import de.inovex.academy.android.server.dto.Session;
import de.inovex.academy.android.server.dto.UserLocation;

public class TestClient {
	private static final String urlPrefix = "http://localhost:9998/";
	private static final String user = "testuser";
	private static final String latitude = "50.967052";
	private static final String longitude = "7.014019";
	
	private Gson gson = new Gson();

	private Object postRequest(String verb, Object parameter) throws ClientProtocolException, IOException {
		HttpPost request = createRequest(verb, parameter);

		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = httpclient.execute(request);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				StringWriter writer = new StringWriter();
				IOUtils.copy(instream, writer, "UTF-8");
				return gson.fromJson(writer.toString(), UserLocation.class);
			} finally {
				instream.close();
			}
		}
		throw new IOException("did not get a parsable return value");
	}

	private HttpPost createRequest(String verb, Object parameter)
			throws UnsupportedEncodingException {
		HttpPost request = new HttpPost(urlPrefix + verb);
		StringEntity input = new StringEntity(gson.toJson(parameter));
		input.setContentType(MediaType.APPLICATION_JSON);
		request.setEntity(input);
		return request;
	}
	
	public static void main(String[] args) {
		User cred = new User();
		cred.setLogin(user);
		UserLocation loc = new UserLocation();
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		
		TestClient client = new TestClient();
		
		try {
//			Session session = (Session)client.postRequest("login", cred);
			loc = (UserLocation)client.postRequest("user", latitude);
			loc = (UserLocation)client.postRequest("user", longitude);
			System.out.println(loc.getLatitude() + ", " + loc.getLongitude());
//			System.out.println(session.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
