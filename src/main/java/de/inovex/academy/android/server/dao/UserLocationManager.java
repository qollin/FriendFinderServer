package de.inovex.academy.android.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.inovex.academy.android.server.dto.User;
import de.inovex.academy.android.server.dto.UserLocation;

public class UserLocationManager extends DTOManager {
	private static final String TABLENAME = "user_location";
	
	public UserLocationManager(Connection conn) {
		super(conn);
	}
	
	public void saveOrUpdate(UserLocation userLocation) throws SQLException {
		if (userExistsIn(userLocation.getUser(), TABLENAME)) {
			updateUserLocation(userLocation);
		} else {
			insertUserLocation(userLocation);
		}
	}
	
	
	
	
	public List<UserLocation> getAllUserLocations() throws SQLException {
		List<UserLocation> userLocationList = new ArrayList<UserLocation>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement("SELECT login, latitude, longitude FROM " + TABLENAME);
			rs = stmt.executeQuery();
	
			while (rs.next()) {
				UserLocation userLocation = createEmptyUserLocationWithUser();
				
				userLocation.getUser().setLogin(rs.getString("login"));
				userLocation.setLatitude(rs.getString("latitude"));
				userLocation.setLongitude(rs.getString("longitude"));
				
				userLocationList.add(userLocation);			
			}
		} finally {
			cleanup(stmt, rs);
		}
		return userLocationList;
	}

	private UserLocation createEmptyUserLocationWithUser() {
		UserLocation userLocation = new UserLocation();
		User user = new User();
		userLocation.setUser(user);
		return userLocation;
	}

	public void deleteUserLocation(String username) throws SQLException {
		executeStatement("DELETE FROM "+TABLENAME+" WHERE login = ?", username);
	}
	
	private void updateUserLocation(UserLocation userLocation) throws SQLException {
		executeStatement("UPDATE " + TABLENAME + " SET latitude = ?, longitude = ? WHERE login = ?", userLocation.getLatitude(), userLocation.getLongitude(), userLocation.getUser().getLogin());
	}
	
	private void insertUserLocation(UserLocation userLocation) throws SQLException {
		executeStatement("INSERT INTO " + TABLENAME + " (login, latitude, longitude) VALUES (?,?,?)", userLocation.getUser().getLogin(), userLocation.getLatitude(), userLocation.getLongitude());
	}
	

}
