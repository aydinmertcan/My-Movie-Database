package com.bilgeadam.marathon.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.bilgeadam.marathon.utils.DatabaseConnection;
import com.bilgeadam.marathon.utils.ScriptRunner;

public class Client {
	public static void main(String[] args) {
		createCsvTables();
	}
	

	private static void createCsvTables() {
		try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
			String path = "./src/com/bilgeadam/marathon/sqls/moviescsv/CreateCsvTables.sql";
			boolean isExecuted = new ScriptRunner().executeCreationStatement(stmt, path);
			if(isExecuted) 
				System.out.println("Tables are successfully created.");
			else
				System.out.println("Error is occured while creating tables.");
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	
	private static void deleteCsvTables() {
		
		
	}
	
}
