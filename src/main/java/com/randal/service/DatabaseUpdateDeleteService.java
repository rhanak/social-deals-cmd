package com.randal.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;

public class DatabaseUpdateDeleteService {
	private Connection connection;

	public DatabaseUpdateDeleteService(Connection connection) {
		this.connection = connection;
	}
	public void updateRowInTable(String table, Map<String, String> columnUpdates, Map<String, String> where)
			throws SQLException, ParseException {
		String sql = "UPDATE " + table + " SET " + columnUpdatesBuilder(columnUpdates, ",") + " where " + columnUpdatesBuilder(where , "AND");
		System.out.println(sql);
		Statement stmt = connection.createStatement();
		int numRows = stmt.executeUpdate(sql);
		System.out.println(numRows + " rows updated.");
	}
	public void deleteRowInTable(String table, Map<String, String> where)
			throws SQLException, ParseException {
		String sql = "DELETE FROM " + table + " WHERE " + columnUpdatesBuilder(where , "AND");
		System.out.println(sql);
		Statement stmt = connection.createStatement();
		int numRows = stmt.executeUpdate(sql);
		System.out.println(numRows + " rows updated.");
	}
	
	private String columnUpdatesBuilder(Map<String, String> columnUpdates, String and) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		Set<String> columns = columnUpdates.keySet();
		for(String column : columns)
		{
			String value = columnUpdates.get(column);
			sb.append(column);
			sb.append("=");
			sb.append("'" + value + "'");
			// don't add the comma for the last one
			if(i != (columns.size() - 1))
			{
				sb.append(and);
			}
			i++;
		}
		return sb.toString();
	}
}
