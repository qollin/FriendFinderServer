package de.inovex.academy.android.server.resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;

import de.inovex.academy.android.server.dao.UserProfilePictureManager;
import de.inovex.academy.android.server.dto.UserProfilePicture;

@Path("/getalluserwithprofilepicture")
public class GetAllUserWithProfilePictureResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMessage() {
		Gson gson = new Gson();
		// select

		DatabaseConnection con = new DatabaseConnection();
		Connection conn = con.connect();
		UserProfilePictureManager userProfilePictureManager = new UserProfilePictureManager(
				conn);
		List<UserProfilePicture> userLocationList;
		try {
			userLocationList = userProfilePictureManager
					.getAllUserProfilePictures();
			return gson.toJson(userLocationList);
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error - Get All Profile Picture: " + e.getStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			return "Error - Get All Profile Picture: " + e.getStackTrace();
		}
	}
}