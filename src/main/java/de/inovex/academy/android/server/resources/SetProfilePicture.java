package de.inovex.academy.android.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import de.inovex.academy.android.server.dto.UserProfilePicture;

@Path("/setprofilepicture")
public class SetProfilePicture {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMessage(String jsonImage) {
		Gson gson = new Gson();
		gson.fromJson(jsonImage, UserProfilePicture.class);
		
		return Response.ok("Success - Set Profile Picture", "text/plain")
				.build();
	}
}