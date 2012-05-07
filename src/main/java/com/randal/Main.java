package com.randal;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import com.randal.data.ConnectionManager;
import com.randal.view.cmdline.CommandLineView;

public class Main {
	public static void main(String []args) throws SQLException, ClassNotFoundException, IOException, ParseException
	{
		ConnectionManager connectionManager = new ConnectionManager();
		CommandLineView commandLineView = new CommandLineView();
		commandLineView.setConnectionManager(connectionManager);
		commandLineView.menu(args);
	}
}
