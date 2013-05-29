package de.inovex.academy.android;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import de.inovex.academy.android.server.dao.TableManager;
import de.inovex.academy.android.server.dao.UserLocationManager;
import de.inovex.academy.android.server.dto.User;
import de.inovex.academy.android.server.dto.UserLocation;

public class DBTestClient {

	public static void printList(@SuppressWarnings("rawtypes") List l) {
		for (Object o : l) {
			System.out.println(o);
		}
	}
	
	public static void main(String[] args) {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver"); 
			Connection conn = DriverManager.getConnection("jdbc:derby:friendfinder;create=true");
			TableManager manager = new TableManager(conn);
			manager.createTablesIfTheyNotExist();
			
			UserLocation userLocation = new UserLocation();
			User user = new User();
			user.setLogin("collin");
			userLocation.setUser(user);
			userLocation.setLatitude("43");
			userLocation.setLongitude("41");
			
			UserLocationManager userLocationManager = new UserLocationManager(conn);
			userLocationManager.saveOrUpdate(userLocation);
			
			List<UserLocation> userLocationList = userLocationManager.getAllUserLocations();
			printList(userLocationList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
