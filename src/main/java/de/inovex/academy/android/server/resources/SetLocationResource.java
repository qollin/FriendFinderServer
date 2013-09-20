package de.inovex.academy.android.server.resources;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import de.inovex.academy.android.server.dao.UserLocationManager;
import de.inovex.academy.android.server.dto.UserLocation;

@Path("/setlocation")
public class SetLocationResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMessage(String jsonLoc) {
		System.err.println("Got locationstring: "+ jsonLoc);
		Gson gson = new Gson();
		UserLocation userLocation = gson.fromJson(jsonLoc, UserLocation.class);
		System.err.println("Serialized to: "+userLocation.getUser());
		// todo:save		
		DatabaseConnection con = new DatabaseConnection();
		Connection conn = con.connect();
		System.err.println("DB Connection: "+conn);
		UserLocationManager userLocationManager = new UserLocationManager(conn);
		try {
			userLocationManager.saveOrUpdate(userLocation);
			System.err.println("Saved!");
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}		
		return Response.ok("Success - Set User Location", "text/plain").build();
	}
}