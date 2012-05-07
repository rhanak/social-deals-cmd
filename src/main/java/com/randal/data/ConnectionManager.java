package com.randal.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private Connection connection;
	
	public Connection getConnection(String username, String password) throws SQLException, ClassNotFoundException {
		if (connection == null)
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			DriverManager.setLoginTimeout(10);
			// localhost
			connection = DriverManager.getConnection("jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g", username, password); 
		}
		return connection;
	}
}
