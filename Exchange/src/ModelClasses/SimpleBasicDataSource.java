package ModelClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
  
public class SimpleBasicDataSource {

    public static void main(String[] args) throws Exception {
    	// Create a BasicDataSource object and configure database 
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    	dataSource.setUrl("jdbc:mysql://localhost:3306/tester");
    	dataSource.setUsername("root");
    	dataSource.setPassword("69727177");
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	
    	try {
    		// Get connection and execute a simple query
    		conn = dataSource.getConnection();
    		stmt = conn.prepareStatement("SELECT * FROM users");
    		ResultSet rs = stmt.executeQuery();
    		// Print fetched data
    		while (rs.next()) {
    			System.out.println("name : " + rs.getString("name"));

    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		if (stmt != null) {
    			stmt.close();
    		}
    		if (conn != null) {
    			conn.close();
    		}

    	}
    }
}	