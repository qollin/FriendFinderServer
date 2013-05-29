package de.inovex.academy.android.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import de.inovex.academy.android.server.dto.User;
import de.inovex.academy.android.server.dto.UserLocation;

@Path("/setlocation")

public class SetLocationResource {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMessage(String jsonLoc) {
		Gson gson = new Gson();
		gson.fromJson(jsonLoc, UserLocation.class);
//todo:save
		return Response.ok("Success - Set User Location", "text/plain").build();
	}
}
