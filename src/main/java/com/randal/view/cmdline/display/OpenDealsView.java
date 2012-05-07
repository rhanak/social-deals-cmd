package com.randal.view.cmdline.display;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.randal.model.Deal;
import com.randal.service.DatabaseService;
import com.randal.view.cmdline.MenuHelper;
import com.randal.view.cmdline.TablesHelper;

/*
 * Display all deals that are still open for purchase for a given location. A deal is 
 *	open if itÕs sale end date/time > current date/time
 */
public class OpenDealsView {
	private DatabaseService databaseService;

	public OpenDealsView(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public void display() throws SQLException, ParseException {
		/*
		 * Ask the user what location they are in?
		 */
		String location = MenuHelper.inString(" What location would you like to search? : ");
		/*
		 * Find all the open deals in their location
		 */
		List<Deal> deals = databaseService.listDealsOpenByLocation(location);
		TablesHelper.displayDeals(deals, location);
	}
}
