package com.randal.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.randal.model.Deal;

public class DatabaseListService {
	private Connection connection;

	public DatabaseListService(Connection connection) {
		this.connection = connection;
	}
	
	@SuppressWarnings("rawtypes")
	public List[] columnNamesTypes(String table) throws SQLException
	{
		List<String> columnNames = new ArrayList<String>();
		List<String> columnTypes = new ArrayList<String>();

		String sqlQuery = "SELECT * from " + table;
		PreparedStatement pstmt2 = connection.prepareStatement(sqlQuery);
		ResultSet rset = pstmt2.executeQuery();
		
		ResultSetMetaData rmd = rset.getMetaData();
		for (int i = 1; i <= rmd.getColumnCount(); i++) {
			String name = rmd.getColumnName(i);
			String type = rmd.getColumnTypeName(i);
			
			columnNames.add(name);
			columnTypes.add(type);
		}
		return new List [] {columnNames, columnTypes};
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List[] listTuples(String table) throws SQLException {
		List[] o = columnNamesTypes(table);
		List<String> columnNames = (List<String>)o[0];
		List<String> columnTypes = (List<String>)o[1];
		
		// I feel dirty building up SQL statements like this, but we can't use
		// ORM
		String sqlQuery = "SELECT * from " + table;
		PreparedStatement pstmt2 = connection.prepareStatement(sqlQuery);
		ResultSet rset = pstmt2.executeQuery();
		
		return new List [] {columnNames, buildUpDatastructureRows(rset, columnTypes)};
	}
	
	private List<List<String>> buildUpDatastructureRows(ResultSet rset, List<String> columnTypes) throws SQLException
	{
		List<List<String>> columnValues = new ArrayList<List<String>>();
		while (rset.next()) {
			List<String> currentRow = new ArrayList<String>();
			for (int i = 0; i < columnTypes.size(); i++)
			{
				if ("VARCHAR2".equalsIgnoreCase(columnTypes.get(i)))
				{
					// 1 indexed
					currentRow.add(rset.getString(i+1));
				}
				else if ("NUMBER".equalsIgnoreCase(columnTypes.get(i)))
				{
					currentRow.add(new Float(rset.getFloat(i+1)).toString());
				}
				else if ("TIMESTAMP".equalsIgnoreCase(columnTypes.get(i)))
				{
					currentRow.add(rset.getTimestamp(i+1).toString());
				}
				else if ("DATE".equalsIgnoreCase(columnTypes.get(i)))
				{
					currentRow.add(rset.getDate(i+1).toString());
				}
			}
			columnValues.add(currentRow);
		}
		return columnValues;
	}
	
	/*
	 * Display all deals that are still open for purchase for a given location. A deal is 
	 *	open if itÕs sale end date/time > current date/time	
	 */
	public List<Deal> listDealsOpenByLocation(String location) throws SQLException
	{
		String sql = "select d.id, d.description, d.original_price, d.quantity, d.sale_start, d.sale_end, d.deal_price, d.expiration" + 
				" from deals d, merchants m, valid_at v where d.merchant_id = m.id" +
				" and d.id=v.deal_id and UPPER(v.city) like '%" + location.toUpperCase() + "%'" +
				" and d.sale_end > CURRENT_TIMESTAMP";
		System.out.println(sql);
		Statement stmt = connection.createStatement();
		ResultSet rset = stmt.executeQuery(sql);
	
		List<Deal> deals = new ArrayList<Deal>();
		while (rset.next()) 
		{
			Deal deal = new Deal();
			deal.setId(rset.getString(1));
			deal.setDescription(rset.getString(2));
			deal.setOriginalPrice(rset.getInt(3));
			deal.setQuantity(rset.getInt(4));
			deal.setSaleStart(rset.getTimestamp(5));
			deal.setSaleEnd(rset.getTimestamp(6));
			deal.setDealPrice(rset.getInt(7));
			deal.setExpiration(rset.getTimestamp(8));
			deals.add(deal);
		}
		return deals;
	}
	
	/*
	 * Search all (current or past) deals based on location and merchant name
	 */
	public List<Deal> searchDealsByLocationAndMerchantName(String location, String merchantName) throws SQLException
	{
		String sql = "select d.id, d.description, d.original_price, d.quantity, d.sale_start, d.sale_end, d.deal_price, d.expiration" + 
				" from deals d, merchants m, valid_at v where d.merchant_id = m.id" +
				" and d.id=v.deal_id and UPPER(v.city) like '%" + location.toUpperCase() + "%' and UPPER(m.name) like '%" + merchantName.toUpperCase() + "%'";
		System.out.println(sql);
		Statement stmt = connection.createStatement();
		ResultSet rset = stmt.executeQuery(sql);
	
		List<Deal> deals = new ArrayList<Deal>();
		while (rset.next()) 
		{
			Deal deal = new Deal();
			deal.setId(rset.getString(1));
			deal.setDescription(rset.getString(2));
			deal.setOriginalPrice(rset.getInt(3));
			deal.setQuantity(rset.getInt(4));
			deal.setSaleStart(rset.getTimestamp(5));
			deal.setSaleEnd(rset.getTimestamp(6));
			deal.setDealPrice(rset.getInt(7));
			deal.setExpiration(rset.getTimestamp(8));
			deals.add(deal);
		}
		return deals;
	}
}
