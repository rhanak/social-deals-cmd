package com.randal.view.cmdline;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.randal.model.Deal;

public class TablesHelper {
	
	public static void displayDeals(List<Deal> deals, String location, String merchant)
	{
		System.out.println("============================================");
		System.out.printf("|  Deals at %s for %s |", location, merchant);
		System.out.println("============================================");
		displayDeals(deals);
	}
	public static void displayDeals(List<Deal> deals, String location)
	{
		System.out.println("============================================");
		System.out.printf("|  Deals at %s |", location);
		System.out.println("============================================");
		displayDeals(deals);
	}
	public static void displayDeals(List<Deal> deals)
	{
		System.out.println("Id   Description   Original_price   Quantity   Sale_start   Sale_end   Deal_price   Expiration");
		
		for (int i = 0; i < deals.size(); i++) {
			Deal deal = deals.get(i);
			System.out.println(deal.getId() + "   " + deal.getDescription() + "   " + deal.getOriginalPrice() + "   " + deal.getQuantity() + "   " + deal.getSaleStart() + "   " + 
			deal.getSaleEnd() + "   " + deal.getDealPrice() + "   " + deal.getExpiration());
		}
	}
	
	public static void displayTables(List<String> tables)
	{
		System.out.println("============================");
		System.out.println("|  User Tables |");
		System.out.println("============================");
		
		for (int i = 0; i < tables.size(); i++) {
			String table = tables.get(i);
			System.out.println("| " + i + ". " + table + "  |");
		}
		System.out.println("|  m   Go back to main menu  |");
	}
	
	public static void displayRowsColumns(List<String> names, List<List<String>> values) throws SQLException
	{	
		// Display the column names
		for (String columnName : names)
		{
			System.out.printf("%s \t", columnName);
		}
		System.out.println();
		
		// Print out the rows
		for (List<String> row : values)
		{
			for (String columnValue : row)
			{
				System.out.printf("%s \t", columnValue);
			}
			System.out.println();
		}
	}
	
	public static boolean selectColumn(String columnName, String question) {
		String value = MenuHelper.inString(question + columnName
				+ "? Type y for yes or anything for no: ");
		if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("y")
				|| value.equalsIgnoreCase("ye")) {
			return true;
		} else {
			return false;
		}
	}

	public static Map<String, String> selectColumns(List<String> columnNames,
			List<String> columnTypes, String question) {
		Map<String, String> columnUpdates = new HashMap<String, String>();
		int i = 0;
		for (String name : columnNames) {
			if (selectColumn(name, question)) {

				String value = "";
				if ("TIMESTAMP".equalsIgnoreCase(columnTypes.get(i))) {
					value = MenuHelper.inString(" Type the value for " + name
							+ " (yyyy-MM-dd hh:mm):");
					dateErrorChecker(value, "yyyy-MM-dd hh:mm");

				} else if ("DATE".equalsIgnoreCase(columnTypes.get(i))) {
					value = MenuHelper.inString(" Type the value for " + name
							+ " (yyyy-MM-dd):");
					dateErrorChecker(value, "yyyy-MM-dd");
				} else {
					value = MenuHelper.inString(" Type the value for " + name
							+ ":");
				}
				columnUpdates.put(name, value);
			}
			i++;
		}
		return columnUpdates;
	}

	public static void dateErrorChecker(String dateString, String format) {
		if (format.equals("yyyy-MM-dd hh:mm")) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm");
				dateFormat.parse(dateString);
			} catch (ParseException e) {
				System.out
						.println("Sorry you entered an invalid date, try something like 2012-03-15 12:15");
			}
		} else if (format.equals("yyyy-MM-dd")) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm");
				dateFormat.parse(dateString);
			} catch (ParseException e) {
				System.out
						.println("Sorry you entered an invalid date, try something like 2012-03-15");
			}
		}
	}
}
