package de.inovex.academy.android.server.resources;

import java.sql.Connection;
import java.sql.DriverManager;

import de.inovex.academy.android.server.dao.TableManager;

public class DatabaseConnection {
	
	public Connection connect() {
		try {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver"); 
		Connection conn = DriverManager.getConnection("jdbc:derby:friendfinder;create=true");
		TableManager manager = new TableManager(conn);
		manager.createTablesIfTheyNotExist();
		return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
