package com.randal.view.cmdline.search;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.randal.model.Deal;
import com.randal.service.DatabaseService;
import com.randal.view.cmdline.MenuHelper;
import com.randal.view.cmdline.TablesHelper;

public class TablesSearchView {
	private DatabaseService databaseService;

	public TablesSearchView(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public void display() throws SQLException, ParseException {

		/*
		 * Ask the user what location they are in?
		 */
		String location = MenuHelper.inString(" What location would you like to search? : ");
		
		/*
		 * What merchant name do they want to search for?
		 */
		String merchant = MenuHelper.inString(" What merchant would you like to search? : ");
		
		/*
		 * Search deals in their location by merchant name
		 */
		List<Deal> deals = databaseService.searchDealsByLocationAndMerchantName(location, merchant);
		TablesHelper.displayDeals(deals, location, merchant);
	}
}
