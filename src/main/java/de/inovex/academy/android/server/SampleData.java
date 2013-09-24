package de.inovex.academy.android.server;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;

import de.inovex.academy.android.server.dao.TableManager;
import de.inovex.academy.android.server.dao.UserLocationManager;
import de.inovex.academy.android.server.dao.UserProfilePictureManager;
import de.inovex.academy.android.server.dto.User;
import de.inovex.academy.android.server.dto.UserLocation;
import de.inovex.academy.android.server.dto.UserProfilePicture;

public class SampleData {
	private User howard = createUser("Howard");
	private User sheldon = createUser("Sheldon");
	private User penny = createUser("Penny");
	
	public void create() throws ClassNotFoundException, SQLException, IOException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver"); 
		Connection conn = DriverManager.getConnection("jdbc:derby:friendfinder;create=true");
		TableManager manager = new TableManager(conn);
		manager.createTablesIfTheyNotExist();
		
		UserLocationManager userLocationManager = new UserLocationManager(conn);
		UserProfilePictureManager userProfilePictureManager = new UserProfilePictureManager(conn);

		createUserLocation(userLocationManager, howard, "54", "8");
		createUserLocation(userLocationManager, sheldon, "52", "9");
		createUserLocation(userLocationManager, penny, "53", "8.5");

		createUserProfilePicture(userProfilePictureManager, howard, FileUtils.readFileToByteArray(new File("Howard.jpeg")));
		createUserProfilePicture(userProfilePictureManager, sheldon, FileUtils.readFileToByteArray(new File("Sheldon.jpeg")));
		createUserProfilePicture(userProfilePictureManager, penny, FileUtils.readFileToByteArray(new File("Penny.jpeg")));
		
		conn.commit();
		conn.close();
	}
	
	private  User createUser(String username) {
		User user = new User();
		user.setLogin(username);
		return user;
	}

	private  void createUserLocation(UserLocationManager manager, User user, String latitude, String longitude)
			throws SQLException {
		UserLocation userLocation = new UserLocation();
		userLocation.setUser(user);
		userLocation.setLatitude(latitude);
		userLocation.setLongitude(longitude);
		
		manager.saveOrUpdate(userLocation);
	}

	
	private  void createUserProfilePicture(UserProfilePictureManager manager, User user, byte[] profilePicture) throws SQLException, IOException {
		UserProfilePicture profile = new UserProfilePicture();
		profile.setUser(user);
		profile.setProfilePicture(profilePicture);
		
		manager.saveOrUpdate(profile);
	}

}
