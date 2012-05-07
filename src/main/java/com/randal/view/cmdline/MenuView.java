package com.randal.view.cmdline;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import com.randal.service.DatabaseService;
import com.randal.view.cmdline.display.ArbitrarySQLView;
import com.randal.view.cmdline.display.OpenDealsView;
import com.randal.view.cmdline.search.TablesSearchView;

public class MenuView {
	private DatabaseService databaseService;

	public MenuView(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public void menu() throws SQLException, ClassNotFoundException, IOException, ParseException {

		// Display the Menu
		int swValue;
		while (true) {
			System.out.println("============================");
			System.out.println("|  Randy Hanak SQL Project |");
			System.out.println("============================");
			System.out.println("| Options:                 |");
			System.out.println("|        1. View tables    |");
			System.out.println("|        2. Modify tables  |");
			System.out.println("|        3. Search Deals   |");
			System.out.println("|        4. Display all deals that are still open for purchase for a given location. |");
			System.out.println("|        5. Execute some SQL!  |");
			System.out.println("|        6. Exit |");
			System.out.println("============================");
			swValue = MenuHelper.inInt(" Select option: ");

			// Parse the input
			switch (swValue) {
			case 1:
				// View tables
				TablesListView tablesListView = new TablesListView(databaseService);
				tablesListView.display();
				break;
			case 2:
				TablesModifyView tablesModifyView = new TablesModifyView(databaseService);
				tablesModifyView.display();
				break;
			case 3:
				// Search
				TablesSearchView tablesSearchView = new TablesSearchView(databaseService);
				tablesSearchView.display();
				break;
			case 4:
				OpenDealsView openDealsView = new OpenDealsView(databaseService);
				openDealsView.display();
				break;
			case 5:
				ArbitrarySQLView arbitrarySQLView = new ArbitrarySQLView(databaseService);
				arbitrarySQLView.display();
				break;
			case 6:
				System.out.println("Exiting!");
				System.exit(0);
			default:
				System.out.println("Invalid selection");
				break; // This break is not really necessary
			}
		}
	}
}
