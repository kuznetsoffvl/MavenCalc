package testgeeksforless.kuznetsovvladimir.mavencalc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

	// Database credentials
	static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/mavencalcdb";
	static final String USER = "postgres";
	static final String PASS = "b3nql#";
	private static Connection connection = null;

	public void connectDatabase() {

		System.out.println("Testing connection to PostgreSQL JDBC");

		try {
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException exep) {
			System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
			exep.printStackTrace();
			return;
		}

		System.out.println("PostgreSQL JDBC Driver successfully connected");

		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (SQLException exep) {
			System.out.println("Connection Failed");
			exep.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You successfully connected to database now");
		} else {
			System.out.println("Failed to make connection to database");
		}
	}

	public void getData() {

		connectDatabase();
		
		if (connection == null) {
			System.out.println("Failed to make connection to database");
			return;
		}

		// Data select
		try {
			Statement stmt = (Statement) connection.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM calcresults");
			int columns = resultSet.getMetaData().getColumnCount();
			ResultSetMetaData meta = resultSet.getMetaData();
			int rowCounter = 0;
			
			System.out.println("Getting data from SQL table");

			// Перебор строк с данными
			while (resultSet.next()) {
				rowCounter++;
				System.out.print("row # " + rowCounter + ": " + "\n");
				for (int i = 1; i <= columns; i++) {

					String columnName = meta.getColumnName(i);
					System.out.print(columnName + ": " + resultSet.getString(i) + "\t");
				}
				System.out.println();
			}
			
			if (rowCounter == 0) {
				System.out.println("There is no data to display");
			}
			System.out.println();
			
			if (resultSet != null)
				resultSet.close();
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException exep) {
			System.out.println("Connection Failed");
			exep.printStackTrace();
		}
	}

	public void findResult(double resToFind) {
		
		connectDatabase();
		
		if (connection == null) {
			System.out.println("Failed to make connection to database");
			return;
		}
		
		System.out.println("Searching result " + resToFind + " in SQL table");
		
		// Data select
		try {
			Statement stmt = (Statement) connection.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM calcresults WHERE calcresult = " + resToFind);
			int columns = resultSet.getMetaData().getColumnCount();
			ResultSetMetaData meta = resultSet.getMetaData();
			int rowCounter = 0;

			// Перебор строк с данными
			while (resultSet.next()) {
				rowCounter++;
				System.out.print("row # " + rowCounter + ": " + "\n");
				for (int i = 1; i <= columns; i++) {

					String columnName = meta.getColumnName(i);
					System.out.print(columnName + ": " + resultSet.getString(i) + "\t");
				}
				System.out.println();
			}
			
			if (rowCounter == 0) {
				System.out.println("There is no data to display");
			}	
			System.out.println();
			
			if (resultSet != null)
				resultSet.close();
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();

		} catch (SQLException exep) {
			System.out.println("Connection Failed");
			exep.printStackTrace();
		}
	}
}