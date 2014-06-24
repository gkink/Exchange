package UnitTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import ModelClasses.User;
import dbClasses.DBqueryGenerator;
import dbClasses.MyDBInfo;
import dbClasses.QueryExecutor;

public class UserTest {
	private Connection con;
	private Statement stmt;
	private DataSource datasource;
	private QueryExecutor executor;
	private DBqueryGenerator generator;

	public static final String username = MyDBInfo.MYSQL_USERNAME;
	public static final String password = MyDBInfo.MYSQL_PASSWORD;
	public static final String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	public static final String database = MyDBInfo.MYSQL_DATABASE_NAME;

	@Before
	public void setUp(){
		datasource = mock(DataSource.class);
		generator =  new DBqueryGenerator();

		//copied this from assignment 3 code
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
	
	@Test
	public void sampletest(){
		User testUser = new User(executor, generator, "Ibrahim", "Ajaev", "randomr10@yahoo.com", "sample");		
		assertEquals(true, testUser.userRegistered());
		assertEquals(5, testUser.getRating());
		User testUser2 = new User(executor, generator, "giorgie", "gheambashidze", "ggeha@gmail.com", "12345");		
		assertEquals(false, testUser2.userRegistered());
		assertEquals(0, testUser2.getRating());
	}

	@Test
	public void testFirstConstructorAndInsert() {
		User testUser = new User(executor, generator, "giorgi", "qinqladze", "gqin@gmail.com", "123");

		assertEquals(0, testUser.getId());
		assertEquals(0, testUser.getRating());
		assertEquals("giorgi", testUser.getFirstName());
		assertEquals("qinqladze", testUser.getLastName());
		assertEquals("gqin@gmail.com", testUser.getEmail());
		assertEquals("123", testUser.getPassword());

		int newid = testUser.addToUsers();
		assertEquals(2, newid);
	}

	@Test
	public void testSecondConstructor(){
		User testUser = new User(executor, generator, 1);

		assertEquals(2, testUser.getId());
		assertEquals(0, testUser.getRating());
		assertEquals("giorgi", testUser.getFirstName());
		assertEquals("qinqladze", testUser.getLastName());
		assertEquals("gqin@gmail.com", testUser.getEmail());
	}

}
