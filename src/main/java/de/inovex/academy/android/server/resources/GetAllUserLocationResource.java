package de.inovex.academy.android.server.resources;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import de.inovex.academy.android.DBTestClient;
import de.inovex.academy.android.server.dto.UserLocation;
import de.inovex.academy.android.server.dao.UserLocationManager;

@Path("/getalluserlocation")
public class GetAllUserLocationResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMessage() {
		Gson gson = new Gson();

		DatabaseConnection con = new DatabaseConnection();
		Connection conn = con.connect();

		UserLocationManager userLocationManager = new UserLocationManager(conn);
		try {
			List<UserLocation> userLocationList = userLocationManager
					.getAllUserLocations();	
			return gson.toJson(userLocationList);
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error - Get All User Location: " + e.getStackTrace();
		}
	}
}
