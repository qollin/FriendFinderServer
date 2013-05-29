package de.inovex.academy.android.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.inovex.academy.android.server.dto.User;

public class DTOManager {
	protected Connection conn;

	public DTOManager(Connection conn) {
		this.conn = conn;
	}
	
	protected void cleanup(PreparedStatement stmt, ResultSet rs) throws SQLException{
		SQLException exception = null;
		
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				exception = e;
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				exception = e;
			}
		}
		
		if (exception != null) {
			throw exception;
		}
	}

	protected void cleanup(PreparedStatement stmt) throws SQLException{
		cleanup(stmt, null);
	}

	protected boolean userExistsIn(User user, String table) throws SQLException {
		boolean result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT login FROM " + table + " WHERE login = ?");
			stmt.setString(1, user.getLogin());
			rs = stmt.executeQuery();
			result = rs.next();
		} finally {
			cleanup(stmt, rs);
		}
		
		return result;
	}
	

	
}
