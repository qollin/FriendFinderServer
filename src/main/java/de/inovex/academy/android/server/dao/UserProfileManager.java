package de.inovex.academy.android.server.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class UserProfileManager extends DTOManager {

	public UserProfileManager(Connection conn) {
		super(conn);
		
	}
	
	public void deleteUserProfile(String username) throws SQLException, IOException {
		UserProfilePictureManager userProfilePictureManager = new UserProfilePictureManager(conn);
		userProfilePictureManager.deleteUserProfilePicture(username);
		UserLocationManager userLocationManager = new UserLocationManager(conn);
		userLocationManager.deleteUserLocation(username);
	}

}
