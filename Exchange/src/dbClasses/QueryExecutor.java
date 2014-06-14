package dbClasses;

import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class QueryExecutor {

	private BasicDataSource datasource;
	
	/**
	 * Constructor
	 * @param datasource - Datasource object.
	 * tries to initialize it's local connection and statement variable and notes if exception occurs during the process.
	 */
	public QueryExecutor(BasicDataSource datasource){
		this.datasource = datasource;
	}

	/**
	 * method ResultSet
	 * @param query - sql select query String
	 * @return - tries to execute the given query using it's local statement variable, returns the resultSet, 
	 * the result of executing query. Notes if exception occurs during the process.
	 */
	public ResultSet selectResult(String selectQuery){
		ResultSet res = null;

		try {
			Connection cn = datasource.getConnection();
			Statement stm = cn.createStatement();
			
			res = stm.executeQuery(selectQuery);
		} catch (SQLException e) {
			System.out.println("Exception occured when executing Select query");
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * method int
	 * @param query sql query String(insert, update, delete)
	 * tries to execute given query using local statement variable and notes if exception occurs during
	 * process.
	 * @return returns last insert id if the insert query was executed, 0 otherwise 
	 */
	public int executeQuery(String query){
		int res = 0;
		try {
			Connection cn = datasource.getConnection();
			Statement stm = cn.createStatement();
			
			stm.executeUpdate(query);
			
			ResultSet rs = stm.getGeneratedKeys();
			while(rs.next())
				res = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("Exception occured when executing query(update, insert, delete)");
			e.printStackTrace();
		}
		
		return res;
	}
	public static void main(String[] args) {
		BasicDataSource s= new BasicDataSource();
		s.setDriverClassName("com.mysql.jdbc.Driver");
		s.setUsername("root");
		s.setPassword("123456");
		s.setUrl("jdbc:mysql://localhost:3306/Exchange");
		QueryExecutor e= new QueryExecutor(s);
		DBqueryGenerator q= new DBqueryGenerator();
		ResultSet r= e.selectResult("Select * from itemsHave");
		try {
			while(r.next()){
				System.out.print(r.getString("ID"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}