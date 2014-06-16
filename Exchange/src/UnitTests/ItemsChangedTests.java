package UnitTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


import guestSession.ItemsChanged;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import dbConnection.MyDBInfo;

public class ItemsChangedTests {
	ItemsChanged i;
	QueryExecutor q;
	DBqueryGenerator d;
	
	@Before
	public void SetUp(){
		DataSource datasource = mock(DataSource.class);
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
			Connection con = (Connection) DriverManager.getConnection 
					( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD); 
			Statement stmt = (Statement) con.createStatement(); 
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME); 
			when(datasource.getConnection()).thenReturn(con);
		}catch (ClassNotFoundException e) { 
				e.printStackTrace(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		q = new QueryExecutor(datasource);
		d=new DBqueryGenerator();
		i=new ItemsChanged(d, q, 1, "bla");
		
	}
	@Test
	public void testParams(){
		assertEquals(i.getItemName(),"bla");
		assertEquals(i.getItemOwner(),1);	
		
		
	}
	@Test
	public void testInsertAndSelect(){
		i.insert();
		ItemsChanged s= new ItemsChanged(d, q, i.getItemId());
		assertEquals(i.getItemName(),s.getItemName());
		assertEquals(i.getItemOwner(),s.getItemOwner());
		assertEquals(s.getItemId(),s.getItemId());
		
		
	}
	
	
	

}
