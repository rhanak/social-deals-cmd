package com.randal.view.cmdline;

import java.sql.SQLException;
import java.util.List;

import com.randal.service.DatabaseService;

public class TablesListView {
	private DatabaseService databaseService;
	private List<String> tables;
	public TablesListView(DatabaseService databaseService)
	{
		this.databaseService = databaseService;
		this.tables = databaseService.userTables();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void displayRowsColumns(String tableSelected) throws SQLException
	{
		List[] namesAndValues = databaseService.listTuples(tableSelected);
		List<String> names = namesAndValues[0];
		List<List<String>> values = namesAndValues[1];
		TablesHelper.displayRowsColumns(names, values);
	}
	
	public void display() throws SQLException
	{
		/*
		 * Display the Menu
		 */
		TablesHelper.displayTables(tables);
		
		/*
		 * Get the table the user selected
		 */
		String tableSelectedString = MenuHelper.inString(" Select table (Ex '1'): ");
		// Go back to main menu
		if(tableSelectedString.equalsIgnoreCase("m"))
		{
			return;
		}
		int tableSelectedInt = Integer.parseInt(tableSelectedString);
		while(tableSelectedInt < 0 || tableSelectedInt >= tables.size())
		{
			System.out.println("Not a valid table. Select a number from 0 to " + tables.size());
			tableSelectedInt = MenuHelper.inInt(" Select table (Ex '1'): ");
		}
		String tableSelected = tables.get(tableSelectedInt);
		
		/*
		 * Display the rows and columns from the table the user selected
		 */
		displayRowsColumns(tableSelected);
	}
}
