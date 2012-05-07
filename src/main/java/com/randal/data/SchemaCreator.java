package com.randal.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

public class SchemaCreator {

	private static final String FILENAME_SCHEMA_SQL_SCRIPT = "/phase2.sql";
	private ScriptRunner scriptRunner;
	
	public SchemaCreator(Connection connection) {
		scriptRunner = new ScriptRunner(connection, true, false);
	}

	public void loadSchemaFromFile() throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(this.getClass()
					.getResourceAsStream(FILENAME_SCHEMA_SQL_SCRIPT)));
			if (reader != null) {
				scriptRunner.runScript(reader);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			reader.close();
		}

	}
}
