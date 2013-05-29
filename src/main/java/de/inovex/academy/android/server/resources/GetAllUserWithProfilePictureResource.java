package de.inovex.academy.android.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import de.inovex.academy.android.server.dto.UserProfilePicture;

@Path("/getalluserwithprofilepicture")

public class GetAllUserWithProfilePictureResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	public String getMessage() {
		Gson gson = new Gson();
		UserProfilePicture pic = new UserProfilePicture();
//select
		return gson.toJson(pic);
	}
}