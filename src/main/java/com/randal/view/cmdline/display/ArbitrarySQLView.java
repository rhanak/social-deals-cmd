package com.randal.view.cmdline.display;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.randal.service.DatabaseService;
import com.randal.view.cmdline.MenuHelper;
import com.randal.view.cmdline.TablesHelper;

public class ArbitrarySQLView {
	private DatabaseService databaseService;

	public ArbitrarySQLView(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public void display() throws SQLException, ParseException {
		/*
		 * Get the SQL from the user
		 */
		String sql = MenuHelper.inString(" Type your SQL here! : ");
		
		/*
		 * Execute the SQL
		 */
		int last = sql.length();
		Character lastChar1 = sql.charAt(last-1);
		if(lastChar1.equals(';'))
		{
			sql = sql.substring(0, last-1);
		}
		List<List<String>> rows = databaseService.executeSQL(sql);
		
		
		List<String> names = databaseService.getColumnsNames(sql);
		TablesHelper.displayRowsColumns(names, rows);
	}
}
