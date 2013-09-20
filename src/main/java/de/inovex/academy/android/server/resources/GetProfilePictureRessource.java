package de.inovex.academy.android.server.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import de.inovex.academy.android.server.dao.UserProfilePictureManager;
import de.inovex.academy.android.server.dto.UserProfilePicture;

@Path("/getprofilepicture")
public class GetProfilePictureRessource {
	
	@Path("{userLogin}")
	@GET
	@Produces({"image/png"})
	public Response getProfilePicture(@PathParam("userLogin") String externalId, 
	        @Context Request request) {
		
		System.err.println("Requesting picture for user: "+externalId);
		DatabaseConnection con = new DatabaseConnection();
		Connection conn = con.connect();
		UserProfilePictureManager userProfilePictureManager = new UserProfilePictureManager(conn);
		
		List<UserProfilePicture> userLocationList;
		try {
			userLocationList = userProfilePictureManager
					.getAllUserProfilePictures();
			for (UserProfilePicture userProfilePicture : userLocationList) {
				if(userProfilePicture.getUser().getLogin().equalsIgnoreCase(externalId))
				{
					System.err.println("Found one pic, sending...");
					return Response.ok().type("image/png").entity(userProfilePicture.getProfilePicture()).build();
				}					
			}
		}
		catch(Exception e)
		{
			System.err.println("Excption getting user picture: "+e);
		}
		//some sample pics 
		File dummyPic = new File("pics/"+externalId+".png");
		if(dummyPic.exists()){
			FileInputStream fis;
			try {
				fis = new FileInputStream(dummyPic);
				return Response.ok().type("image/png").entity(fis).build();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return null;		
	}

}
