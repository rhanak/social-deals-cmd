package com.randal.view.cmdline;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.randal.service.DatabaseService;

public class TablesUpdateView {
	private DatabaseService databaseService;
	private List<String> tables;

	public TablesUpdateView(DatabaseService databaseService) {
		this.databaseService = databaseService;
		this.tables = databaseService.userTables();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void display() throws SQLException, ParseException {
		/*
		 * Display the available tables
		 */
		TablesHelper.displayTables(tables);

		/*
		 * Get the selected table
		 */
		String tableSelectedString = MenuHelper
				.inString(" Select table (Ex '1'): ");
		// Go back to main menu
		if (tableSelectedString.equalsIgnoreCase("m")) {
			return;
		}
		int tableSelectedInt = Integer.parseInt(tableSelectedString);
		while (tableSelectedInt < 0 || tableSelectedInt >= tables.size()) {
			System.out.println("Not a valid table. Select a number from 0 to "
					+ tables.size());
			tableSelectedInt = MenuHelper.inInt(" Select table (Ex '1'): ");
		}
		String tableSelected = tables.get(tableSelectedInt);

		/*
		 * Get the names and types of the columns for the table selected
		 */
		List[] o = databaseService.columnNamesTypes(tableSelected);
		List<String> columnNames = (List<String>) o[0];
		List<String> columnTypes = (List<String>) o[1];

		/*
		 * Ask the user for the columns and values he/she wants for the where
		 * clause.
		 */
		Map<String, String> whereClause = TablesHelper.selectColumns(columnNames,
				columnTypes,
				" Would you like to include this column in the where ");
		/*
		 * Ask the user for the columns and values he/she wants for the columns
		 * to update the row!
		 */
		Map<String, String> columnUpdates = TablesHelper.selectColumns(columnNames,
				columnTypes, " Would you like to update this column ");

		databaseService.updateRowInTable(tableSelected, columnUpdates,
				whereClause);
	}
}
