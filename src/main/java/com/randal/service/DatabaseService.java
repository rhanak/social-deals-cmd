package com.randal.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.randal.model.Deal;

public class DatabaseService {
	private DatabaseModificationService databaseModificationService;
	private DatabaseListService databaseListService;
	private Connection connection;

	public DatabaseService(Connection connection) {
		this.connection = connection;
		this.databaseModificationService = new DatabaseModificationService(
				connection);
		this.databaseListService = new DatabaseListService(connection);
	}

	public List<String> userTables() {
		List<String> tables = Arrays.asList(new String[] { "purchase_history",
				"merchants", "merchant_locations", "types", "deals",
				"valid_at", "customers", "credit_cards", "reviews",
				"interested_in", "subscribes_to" });

		return tables;
	}

	public void createSchema() throws SQLException, IOException {
		databaseModificationService.createSchema();
	}

	public void addRowToTable(String table, List<String> values,
			List<String> columnTypes) throws SQLException, ParseException {
		databaseModificationService.addRowToTable(table, values, columnTypes);
	}

	public void updateRowInTable(String table,
			Map<String, String> columnUpdates, Map<String, String> where)
			throws SQLException, ParseException {
		databaseModificationService.updateRowInTable(table, columnUpdates,
				where);
	}

	public void deleteRowInTable(String table, Map<String, String> where)
			throws SQLException, ParseException {
		databaseModificationService.deleteRowInTable(table, where);
	}

	public List<Deal> searchDealsByLocationAndMerchantName(String location,
			String merchantName) throws SQLException {
		return databaseListService.searchDealsByLocationAndMerchantName(
				location, merchantName);
	}

	public List<Deal> listDealsOpenByLocation(String location)
			throws SQLException {
		return databaseListService.listDealsOpenByLocation(location);
	}

	@SuppressWarnings("rawtypes")
	public List[] columnNamesTypes(String table) throws SQLException {
		return databaseListService.columnNamesTypes(table);
	}

	@SuppressWarnings("rawtypes")
	public List[] listTuples(String table) throws SQLException {
		return databaseListService.listTuples(table);
	}

	public List<List<String>> executeSQL(String sql) throws SQLException {

		Statement stmt = connection.createStatement();

		String[] words = sql.split(" ");
		Boolean isQ = isQuery(words[0]);
		if (isQ) {
			System.out.printf("'%s'", sql);
			ResultSet rset = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rset.getMetaData();
			List<List<String>> rows = new ArrayList<List<String>>();
			while (rset.next()) {
				int size = metaData.getColumnCount();
				List<String> currentRow = new ArrayList<String>();
				for (int i = 1; i <= size; i++) {
					String type = metaData.getColumnTypeName(i);
					// BAD copy and paste but im getting lazy
					if ("VARCHAR2".equalsIgnoreCase(type)) {
						// 1 indexed
						currentRow.add(rset.getString(i));
					} else if ("NUMBER".equalsIgnoreCase(type)) {
						currentRow.add(new Float(rset.getFloat(i))
								.toString());
					} else if ("TIMESTAMP".equalsIgnoreCase(type)) {
						currentRow.add(rset.getTimestamp(i).toString());
					} else if ("DATE".equalsIgnoreCase(type)) {
						currentRow.add(rset.getDate(i).toString());
					}
				}
				rows.add(currentRow);
			}
			return rows;
		} else if(!isQ){
			int rows = stmt.executeUpdate(sql);
			System.out.printf("Returned %d rows!", rows);
		}
		else {
			System.out.println("Aghh some error!!");
		}
		return null;
	}
	
	public List<String> getColumnsNames(String sql) throws SQLException
	{
		Statement stmt = connection.createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		ResultSetMetaData metaData = rset.getMetaData();
		int size = metaData.getColumnCount();
		List<String> names = new ArrayList<String>();
		for(int i = 1; i <= size; i++)
		{
			names.add(metaData.getColumnName(i));
		}
		return names;
	}

	private Boolean isQuery(String firstWord) {
		Map<String, String> queriesOrUpdates = new HashMap<String, String>();
		queriesOrUpdates.put("select", "query");
		queriesOrUpdates.put("insert", "update");
		queriesOrUpdates.put("update", "update");
		queriesOrUpdates.put("delete", "update");

		String word = queriesOrUpdates.get(firstWord.toLowerCase());
		if (word != null) {
			if(word.equals("query"))
			{
				return true;
			}
			else {
				return false;
			}
		}
		return null;
	}
}
