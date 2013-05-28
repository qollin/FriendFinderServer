package de.inovex.academy.android.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import de.inovex.academy.android.server.dto.Credentials;
import de.inovex.academy.android.server.dto.Session;

@Path("/login")
public class LoginResource {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMessage(String jsonCred) {
		Gson gson = new Gson();
		Credentials cred = gson.fromJson(jsonCred, Credentials.class);
		
		System.out.println(cred.getLogin());
		Session session = new Session();
		session.setId("sehr_lange_id");
		
		return gson.toJson(session);
	}
	
}
