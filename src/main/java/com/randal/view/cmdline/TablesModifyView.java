package com.randal.view.cmdline;

import java.sql.SQLException;
import java.text.ParseException;

import com.randal.service.DatabaseService;

public class TablesModifyView {
	// This class doesn't need the database service, but its dependencies do
	// Should be using ioc for something like this but blah its a small project
	private DatabaseService databaseService;
	public TablesModifyView(DatabaseService databaseService)
	{
		this.databaseService = databaseService;
	}
	
	private void displayWhatToDo()
	{
		System.out.println("============================");
		System.out.println("|  What do you want to do? |");
		System.out.println("============================");
		System.out.println("|          1. Add          |");
		System.out.println("|          2. Update       |");
		System.out.println("|          3. Delete       |");
		
		System.out.println("|  m   Go back to main menu  |");
	}
	
	public void display() throws SQLException, ParseException
	{
		displayWhatToDo();
		
		String tableSelectedString = MenuHelper.inString(" Select (Ex '1'): ");
		// Go back to main menu
		if(tableSelectedString.equalsIgnoreCase("m"))
		{
			return;
		}
		int tableSelectedInt = Integer.parseInt(tableSelectedString);
		while(tableSelectedInt < 1 || tableSelectedInt > 3)
		{
			System.out.println("Not a valid selection. Select a number from 1 to 3");
			tableSelectedInt = MenuHelper.inInt(" Select (Ex '1'): ");
		}
		switch(tableSelectedInt)
		{
		case 1:
			TablesAddView tablesAddView = new TablesAddView(databaseService);
			tablesAddView.display();
			break;
		case 2:
			TablesUpdateView tablesUpdateView = new TablesUpdateView(databaseService);
			tablesUpdateView.display();
			break;
		case 3:
			TablesDeleteView tablesDeleteView = new TablesDeleteView(databaseService);
			tablesDeleteView.display();
			break;
		}
	}
}
