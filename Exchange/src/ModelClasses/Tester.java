package ModelClasses;

import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dbClasses.MyDBInfo;
import dbClasses.QueryExecutor;


public class Tester {
	public static final String username = MyDBInfo.MYSQL_USERNAME;
	public static final String password = MyDBInfo.MYSQL_PASSWORD;
	public static final String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	public static final String database = MyDBInfo.MYSQL_DATABASE_NAME;

	
	public static void main(String[] args) {
		Connection con
		
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DriverManager.getConnection 
					( "jdbc:mysql://" + server, username ,password); 
			stmt = con.createStatement(); 
			stmt.executeQuery("USE " + database); 

			when(datasource.getConnection()).thenReturn(con);
		}catch (ClassNotFoundException e) { 
				e.printStackTrace(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}

		executor = new QueryExecutor(datasource);

	}
}
