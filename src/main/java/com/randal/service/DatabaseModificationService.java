package com.randal.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.randal.data.SchemaCreator;

public class DatabaseModificationService {
	private Connection connection;
	private DatabaseUpdateDeleteService databaseUpdateService;

	public DatabaseModificationService(Connection connection) {
		this.connection = connection;
		this.databaseUpdateService = new DatabaseUpdateDeleteService(connection);
	}

	public void createSchema() throws SQLException, IOException {
		SchemaCreator schemaCreator = new SchemaCreator(connection);
		System.out.println("Creating your schema!");
		schemaCreator.loadSchemaFromFile();
		System.out.println("Done creating your schema!");
	}

	public void addRowToTable(String table, List<String> values, List<String> columnTypes)
			throws SQLException, ParseException {
		String sql = "INSERT INTO " + table + " VALUES " + insertStatementBuilder(values);
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.clearParameters();
		int i = 0;
		for (String columnType : columnTypes)
		{
			if ("VARCHAR2".equalsIgnoreCase(columnType))
			{
				// 1 indexed
				pstmt.setString(i+1, values.get(i));
			}
			else if ("NUMBER".equalsIgnoreCase(columnType))
			{
				pstmt.setFloat(i+1, new Float(values.get(i)));
			}
			else if ("TIMESTAMP".equalsIgnoreCase(columnType))
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				java.util.Date parsedDate = dateFormat.parse(values.get(i));
				java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				pstmt.setTimestamp(i+1, timestamp);
			}
			else if ("DATE".equalsIgnoreCase(columnType))
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date parsedDate = dateFormat.parse(values.get(i));
				java.sql.Date date = new java.sql.Date(parsedDate.getTime());
				pstmt.setDate(i+1, date);
			}
			i++;
		}
		
		int numRows = pstmt.executeUpdate();
		System.out.println(numRows + " rows updated.");
	}

	public void updateRowInTable(String table, Map<String, String> columnUpdates, Map<String, String> where)
			throws SQLException, ParseException {
		databaseUpdateService.updateRowInTable(table, columnUpdates, where);
	}
	
	public void deleteRowInTable(String table,  Map<String, String> where)
			throws SQLException, ParseException {
		databaseUpdateService.deleteRowInTable(table, where);
	}
	
	/*
	 * I thought I would never again have to build SQL like this AGHHHHHH
	 */
	private String insertStatementBuilder(List<String> values) {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		int i = 0;
		for(String value : values)
		{
			sb.append("?");
			// don't add the comma for the last one
			if(i != (values.size() - 1))
			{
				sb.append(",");
			}
			i++;
		}
		sb.append(")");
		return sb.toString();
	}
}
