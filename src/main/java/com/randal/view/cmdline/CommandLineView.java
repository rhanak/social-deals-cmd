package com.randal.view.cmdline;

import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.randal.data.ConnectionManager;
import com.randal.service.DatabaseService;

public class CommandLineView {
	private ConnectionManager connectionManager;
	private DatabaseService databaseService;

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public void login(String username, String password) throws SQLException,
			ClassNotFoundException {
		System.out.println("Logging in with username, " + username);
		this.databaseService = new DatabaseService(
				connectionManager.getConnection(username, password));
	}

	private void setup(String[] args) throws SQLException, ClassNotFoundException, IOException {
		// Login
		Map<String, String> credentials = readCredentials();
		login(credentials.get("username"), credentials.get("password"));
		//login("username", "password");
		
		// Create my schema
		System.out.println("'" + args[0] + "'");
		if (!(args.length > 0 && args[0].equals("noimportschema")))
		{
			databaseService.createSchema();
		}
		else
		{
			System.out.println("Skipping Schema Creation!");
		}
	}

	public void menu(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException {
		// Setup the necessary stuff so the user can interact with the database
		setup(args);
		// Go Menu! Go!
		new MenuView(databaseService).menu();
	}

	private Map<String, String> readCredentials() {
		// open up standard input
		Console cons;
		if ((cons = System.console()) == null) {
			return null;
		}
		System.out.print("Enter your username: ");
		String username = cons.readLine();
		System.out.print("Enter your password: ");
		char[] passwd;
		if ((passwd = cons.readPassword("[%s]", "Password:")) == null) {
			return null;
		}

		// Return a map with the username and password
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("username", username);
		credentials.put("password", new String(passwd));
		return credentials;
	}
}
