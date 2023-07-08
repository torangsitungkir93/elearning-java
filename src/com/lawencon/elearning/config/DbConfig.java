package com.lawencon.elearning.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
private static Connection conn;
	
	private DbConfig() {}
	
	public synchronized static Connection conn() throws SQLException {
		final String url = "jdbc:postgresql://localhost:5432/learningsystem";
		final String username = "postgres";
		final String pass = "postgrepostgre";
		
		if(conn != null) {
			return conn;
		}
		
		conn = DriverManager.getConnection(url, username, pass);
		conn.setAutoCommit(false);
		return conn;
	}
}
