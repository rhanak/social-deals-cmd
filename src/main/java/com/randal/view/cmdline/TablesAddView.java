package com.randal.view.cmdline;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.randal.service.DatabaseService;

public class TablesAddView {
	private DatabaseService databaseService;
	private List<String> tables;

	public TablesAddView(DatabaseService databaseService) {
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
		 * Ask the user for the values he/she wants for the columns Insert the
		 * row!
		 */
		List<String> columnValues = valuesWalkThrough(columnNames, columnTypes);
		databaseService.addRowToTable(tableSelected, columnValues, columnTypes);
	}

	private List<String> valuesWalkThrough(List<String> columnNames,
			List<String> columnTypes) {
		List<String> columnValues = new ArrayList<String>();
		int i = 0;
		for (String name : columnNames) {
			String value = "";
			if ("TIMESTAMP".equalsIgnoreCase(columnTypes.get(i))) {
				value = MenuHelper.inString(" Type the value for " + name
						+ " (yyyy-MM-dd hh:mm):");
				dateErrorChecker(value,"yyyy-MM-dd hh:mm");

			} else if ("DATE".equalsIgnoreCase(columnTypes.get(i))) {
				value = MenuHelper.inString(" Type the value for " + name
						+ " (yyyy-MM-dd):");
				dateErrorChecker(value,"yyyy-MM-dd");
			} else {
				value = MenuHelper
						.inString(" Type the value for " + name + ":");
			}
			columnValues.add(value);
			i++;
		}
		return columnValues;
	}

	private void dateErrorChecker(String dateString, String format) {
		if (format.equals("yyyy-MM-dd hh:mm")) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm");
				dateFormat.parse(dateString);
			} catch (ParseException e) {
				System.out.println("Sorry you entered an invalid date, try something like 2012-03-15 12:15");
			}
		} else if (format.equals("yyyy-MM-dd")) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm");
				dateFormat.parse(dateString);
			} catch (ParseException e) {
				System.out.println("Sorry you entered an invalid date, try something like 2012-03-15");
			}
		}
	}
}
