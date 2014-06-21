package dbClasses;

import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

import javax.sql.DataSource;



public class QueryExecutor {

	private DataSource datasource;
	private Connection con;

	/**
	 * Constructor
	 * @param datasource - Datasource object.
	 * tries to initialize it's local connection and statement variable and notes if exception occurs during the process.
	 */
	public QueryExecutor(DataSource datasource){
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
			con = datasource.getConnection();
			Statement stm = con.createStatement();
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
			con = datasource.getConnection();
			Statement stm = con.createStatement();

			stm.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stm.getGeneratedKeys();
			while(rs.next())
				res = rs.getInt(1);
			
			stm.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when executing query(update, insert, delete)");
			e.printStackTrace();
		}

		return res;
	}
	
	public void closeConnection(){
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when closing connection");
			e.printStackTrace();
		}
	}
}