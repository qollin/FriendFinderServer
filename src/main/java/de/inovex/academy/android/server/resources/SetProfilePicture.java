package de.inovex.academy.android.server.resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import de.inovex.academy.android.server.dao.UserProfilePictureManager;
import de.inovex.academy.android.server.dto.UserProfilePicture;

@Path("/setprofilepicture")
public class SetProfilePicture {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMessage(String jsonImage) {
		Gson gson = new Gson();
		UserProfilePicture pic = gson.fromJson(jsonImage,
				UserProfilePicture.class);

		DatabaseConnection con = new DatabaseConnection();
		Connection conn = con.connect();
		UserProfilePictureManager userProfilePictureManager = new UserProfilePictureManager(
				conn);

		try {
			userProfilePictureManager.saveOrUpdate(pic);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(
					"Error - Set Profile Picture: " + e.getStackTrace(),
					"text/plain").build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.ok(
					"Error - Set Profile Picture: " + e.getStackTrace(),
					"text/plain").build();
		}
		return Response.ok("Success - Set Profile Picture", "text/plain")
				.build();
	}
}