package mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

import mySQL.MyDBInfo;;

public class ConnectionPool {
	
	private static ConnectionPool instance;
	private Stack<Connection> pool;
	private int CONNECTION_LIMIT = 10; // TODO
	
	private ConnectionPool() throws ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		pool = new Stack<Connection>();
	}
	
	public static ConnectionPool getInstance() throws ClassNotFoundException{
		if (instance == null) instance = new ConnectionPool();
		return instance;
	}
	
	public Connection getConnectionFromPool() throws SQLException{
		int size = pool.size();
		Connection connection;
		if (size > 0) {
			connection = pool.pop();
		} else {
			connection = createConnection();
		}
		
		return connection;
	}
	
	public void returnConnectionToPool(Connection con){
		pool.push(con);
	}
	
	public void close() throws SQLException {
		int size = pool.size();
		boolean error = false;
		for (int i = 0; i < size; i++) {
			try {
				pool.get(i).close();
			} catch (SQLException e) {
				e.printStackTrace();
				error = true;
			}
		}
		
		if (error) {
			throw new SQLException("Some connections could not be closed");
		}
	}
	
	private Connection createConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:mysql://"
				+ MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);

	}
}
