package de.inovex.academy.android.server.dao;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.inovex.academy.android.server.dto.User;
import de.inovex.academy.android.server.dto.UserProfilePicture;

public class UserProfilePictureManager extends DTOManager {
	private static final String TABLENAME = "user_profile_picture";
	private static final String PICTURE_DIR = "pics";
	
	public UserProfilePictureManager(Connection conn) {
		super(conn);
	}

	public void saveOrUpdate(UserProfilePicture profile) throws SQLException, IOException{
		if (userExistsIn(profile.getUser(), TABLENAME)) {
			updateProfilePicture(profile);
		} else {
			insertProfilePicture(profile);
		}
	}
	
	public List<UserProfilePicture> getAllUserProfilePictures() throws SQLException, IOException {
		List<UserProfilePicture> userProfilePictureList = new ArrayList<UserProfilePicture>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement("SELECT login, filename FROM " + TABLENAME);
			rs = stmt.executeQuery();
	
			while (rs.next()) {
				UserProfilePicture profile = createEmptyUserProfilePictureWithUser();
				
				profile.getUser().setLogin(rs.getString("login"));

				String filename = rs.getString("filename");
				readProfilePictureInto(profile, filename);

				userProfilePictureList.add(profile);
			}
		} finally {
			cleanup(stmt, rs);
		}
		
		return userProfilePictureList;
	}

	private void readProfilePictureInto(UserProfilePicture profile,
			String filename) throws IOException {
		Path path = createPath(filename);
		byte[] profilePicture = Files.readAllBytes(path);
		profile.setProfilePicture(profilePicture);
	}
	
	private UserProfilePicture createEmptyUserProfilePictureWithUser() {
		UserProfilePicture profile = new UserProfilePicture();
		User user = new User();
		profile.setUser(user);
		
		return profile;
			
	}

	private void updateProfilePicture(UserProfilePicture profile) throws IOException {
		String filename = createFilename(profile); 
		writeFile(profile, filename);
	}

	private void insertProfilePicture(UserProfilePicture profile) throws SQLException, IOException {
		String filename = createFilename(profile); 
		executeStatement("INSERT into " + TABLENAME + " (login, filename) VALUES (?,?)", profile.getUser().getLogin(), filename);
		
		writeFile(profile, filename);
	}

	private void writeFile(UserProfilePicture profile, String filename)
			throws IOException {
		Path path = createPath(filename);
		Files.write(path, profile.getProfilePicture(), StandardOpenOption.CREATE);
	}

	private Path createPath(String filename) {
		Path path = FileSystems.getDefault().getPath(PICTURE_DIR, filename);
		return path;
	}

	private String createFilename(UserProfilePicture profile) {
		String filename = profile.getUser().getLogin() + ".dat";
		return filename;
	}

	
	
	
}
