package de.inovex.academy.android.server.dao;

import java.sql.Connection;
import java.sql.SQLException;

import de.inovex.academy.android.server.dto.UserProfilePicture;

public class UserProfilePictureManager extends DTOManager {
	private static final String TABLENAME = "user_profile_picture";
	
	public UserProfilePictureManager(Connection conn) {
		super(conn);
	}

	public void save(UserProfilePicture profile) throws SQLException{
		if (userExistsIn(profile.getUser(), TABLENAME)) {
			
		}
	}
	
}
