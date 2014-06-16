package UnitTests;

import static org.junit.Assert.*;

import java.sql.*;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;


/**
 * using this static import helps you not write classname mockito before calling each method(like this:
 * Mockito.method), instead, after you use this static import, the static block of mockito class will run
 * automatically, and you can then use method without classname before it(method(), instead of Mockito.method())
 * another avialable import is "import org.mockito.Mockito", but then you will have to use classname(Mockito.method)
 */
import static org.mockito.Mockito.*;
import dbConnection.MyDBInfo;
import dbClasses.QueryExecutor;

public class QueryExecutorTest {
	
	private Connection con;
	private Statement stmt;
	private DataSource datasource;
	private QueryExecutor executor;
	
	public static final String username = MyDBInfo.MYSQL_USERNAME;
	public static final String password = MyDBInfo.MYSQL_PASSWORD;
	public static final String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	public static final String database = MyDBInfo.MYSQL_DATABASE_NAME;

	@Before
	public void setUp(){
		//this is mock object of any existing class
		datasource = mock(DataSource.class);
		
		//copieds this from assignment 3 code
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DriverManager.getConnection 
					( "jdbc:mysql://" + server, username ,password); 
			stmt = con.createStatement(); 
			stmt.executeQuery("USE " + database); 
			
			//this code is responsible for overriding the mockobjects given method(getConnection() in this case)
			//after this fragment of the code is called, datasource.getconnectin method will return the local connecion
			//variable con
			when(datasource.getConnection()).thenReturn(con);
		}catch (ClassNotFoundException e) { 
				e.printStackTrace(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		executor = new QueryExecutor(datasource);
	}
	

	/*
	 * test for select. Uses QueryExecutor object, which is given mock datasource object when created, this is to make
	 * sure, that every time the executor calls datasource.getConnection(), the local connection variable con will
	 * be returned
	 */
	@Test
	public void testSelect() {
		String selectQuery = "select * from users";
		
		ResultSet k = executor.selectResult(selectQuery);
		
		int id = 0, rating = 0;
		String name = null, lastname = null, email = null;
		
		try {
			while(k.next()){
				id = k.getInt(1);
				name = k.getString(2);
				lastname = k.getString(3);
				email = k.getString(4);
				rating = k.getInt(5);
				
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(name);
		assertNotNull(lastname);
		assertNotNull(email);
		
		assertEquals(1, id);
		assertEquals("irakli", name);
		assertEquals("kobalava", lastname);
		assertEquals("ikoba@.gmail.com", email);
		assertEquals(0, rating);
	}
	
	@Test
	public void testInsert(){
		String insertQuery = "insert into users (firstName, lastName, email, ranking) values"+ 
	"( 'archil', 'bakhsoliani', '1@gmail.com', '10')";
		int id = executor.executeQuery(insertQuery);
		
		System.out.print(id);
		assertEquals(12, id);
	}

}
