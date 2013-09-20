package de.inovex.academy.android.server.resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import de.inovex.academy.android.server.dao.DTOManager;
import de.inovex.academy.android.server.dao.UserProfileManager;
import de.inovex.academy.android.server.dao.UserProfilePictureManager;
import de.inovex.academy.android.server.dto.UserProfilePicture;

@Path("/deleteuserprofile")
public class DeleteUserProfileRessource {
	
	@Path("{userLogin}")
	@GET
	public Response deleteuserprofile(@PathParam("userLogin") String externalId, 
	        @Context Request request) {
		
		System.err.println("try to delete user: "+externalId);
		
		DatabaseConnection con = new DatabaseConnection();
		Connection conn = con.connect();
		
		UserProfileManager userProfileManager = new UserProfileManager(conn);
		try {
			userProfileManager.deleteUserProfile(externalId);
			return Response.ok("OK!", "plain/text").build();

		} catch (Exception e) {
			System.err.println("error deleting profile "+e);
		}
		
		
		return null;
	}

	

}
