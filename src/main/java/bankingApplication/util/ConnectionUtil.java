package bankingApplication.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	public static Connection getConnection() {
		
		String url = "jdbc:postgresql://localhost:5432/postgres";
		try {
			return DriverManager.getConnection(url, 
								System.getenv("db_name"), 
								System.getenv("db_pass"));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to connect to database. Sad :(");
			return null;
		}
		
	}


}
