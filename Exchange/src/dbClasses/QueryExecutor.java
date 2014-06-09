package dbClasses;
import java.sql.*;
import org.apache.tomcat.jdbc.pool.ConnectionPool;

public class QueryExecutor {
	private Connection cn;
	private Statement stm;
	
	/**
	 * Constructor
	 * @param pool - apache tomcat connectionPool object.
	 * tries to initialize it's local connection and statement variable and notes if exception occurs during the process.
	 */
	public QueryExecutor(ConnectionPool pool){
		try {
			cn = pool.getConnection();
			stm = cn.createStatement();
		} catch (SQLException e) {
			System.out.println("Exception occured when initializing connection");
			e.printStackTrace();
		}
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
			res = stm.executeQuery(selectQuery);
		} catch (SQLException e) {
			System.out.println("Exception occured when executing Select query");
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * method void
	 * @param query sql query String(insert, update, delete)
	 * tries to execute given query using local statement variable and notes if exception occurs during
	 * process.
	 */
	public void executeQuery(String query){
		try {
			stm.execute(query);
		} catch (SQLException e) {
			System.out.println("Exception occured when executing query(update, insert, delete)");
			e.printStackTrace();
		}
	}
}