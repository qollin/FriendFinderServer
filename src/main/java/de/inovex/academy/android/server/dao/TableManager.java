package de.inovex.academy.android.server.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableManager extends DTOManager {
	public TableManager(Connection conn) {
		super(conn);
	}

	public void createTablesIfTheyNotExist() throws SQLException{
		createUserLocationTableIfItNotExists();
		createUserProfilePictureTableIfItNotExists();
	}
	
	private void createUserLocationTableIfItNotExists() throws SQLException {
		createTableIfItNotExists("user_location", "CREATE TABLE user_location (login VARCHAR(256), latitude VARCHAR(256), longitude VARCHAR(256))");
	}
	
	private void createUserProfilePictureTableIfItNotExists() throws SQLException {
		createTableIfItNotExists("user_profile_picture", "CREATE TABLE user_profile_picture (login VARCHAR(256), filename VARCHAR(256))");
	}
	
	private void createTableIfItNotExists(String tablename, String sql) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getTables(null, "APP", tablename.toUpperCase(), null);
			if(!rs.next()) {
				stmt = conn.prepareStatement(sql);
				stmt.execute();
			}
		} finally {
			cleanup(stmt, rs);
		}
	}
	
	
}
