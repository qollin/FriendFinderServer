package de.inovex.academy.android;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import de.inovex.academy.android.server.dao.TableManager;
import de.inovex.academy.android.server.dao.UserLocationManager;
import de.inovex.academy.android.server.dao.UserProfilePictureManager;
import de.inovex.academy.android.server.dto.User;
import de.inovex.academy.android.server.dto.UserLocation;
import de.inovex.academy.android.server.dto.UserProfilePicture;

public class DBTestClient extends TestBase{
	private static User user = createUser();
	
	
	public static void main(String[] args) {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver"); 
			Connection conn = DriverManager.getConnection("jdbc:derby:friendfinder;create=true");
			TableManager manager = new TableManager(conn);
			manager.createTablesIfTheyNotExist();
			UserLocationManager userLocationManager = new UserLocationManager(conn);
			UserProfilePictureManager userProfilePictureManager = new UserProfilePictureManager(conn);
			
			createUserLocation(userLocationManager);
			createUserProfilePicture(userProfilePictureManager);
			
			List<UserLocation> userLocationList = userLocationManager.getAllUserLocations();
			printList(userLocationList);
			
			List<UserProfilePicture> userProfilePictureList = userProfilePictureManager.getAllUserProfilePictures();
			printList(userProfilePictureList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static User createUser() {
		User user = new User();
		user.setLogin("collin");
		return user;
	}

	private static void createUserLocation(UserLocationManager manager)
			throws SQLException {
		UserLocation userLocation = new UserLocation();
		userLocation.setUser(user);
		userLocation.setLatitude("43");
		userLocation.setLongitude("41");
		
		manager.saveOrUpdate(userLocation);
	}

	
	private static void createUserProfilePicture(UserProfilePictureManager manager) throws SQLException, IOException {
		UserProfilePicture profile = new UserProfilePicture();
		profile.setUser(user);
		profile.setProfilePicture(new byte[]{1,2,3,4,5,6,7,8,9});
		
		manager.saveOrUpdate(profile);
	}
}
