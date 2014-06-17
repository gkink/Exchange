package UnitTests;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import guestSession.DateTime;
import guestSession.ItemsHaveObject;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import dbConnection.MyDBInfo;

public class ItemsHaveObjectTests {
	ItemsHaveObject i;
	QueryExecutor q;
	DBqueryGenerator d;
	Date date;
	Time time;
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
		date = new Date(1994, 2, 1);
		time= new Time(12, 00, 00);
		DateTime dateTime = new DateTime(date, time);
		q = new QueryExecutor(datasource);
		d=new DBqueryGenerator();
		i=new ItemsHaveObject(d, q, "laptop", "chedavs","netbookcomp",1,new DateTime(date, time));
	}
	
	@Test
	public void testParams(){
		assertEquals(i.getItemName(),"laptop");
		assertEquals(i.getItemOwner(),1);
		assertEquals(i.getItemDescription(),"chedavs");
		assertEquals(i.getItemKeywords(), "netbookcomp");
		DateTime tester=new DateTime(date,time);
		assertEquals(i.getCreateDate().getDate(), tester.getDate());
		assertEquals(i.getCreateDate().getTime(), tester.getTime());
	}
	@Test
	public void TestInsertAndSelectandUpdateandDelete(){
		i.insert();
		ItemsHaveObject test= new ItemsHaveObject(d, q, i.getItemId());
		assertEquals(i.getItemName(),test.getItemName());
		assertEquals(i.getItemOwner(),test.getItemOwner());
		assertEquals(i.getItemDescription(),test.getItemDescription());
		assertEquals(i.getItemKeywords(), test.getItemKeywords());
		assertEquals(i.getCreateDate().getDate(), test.getCreateDate().getDate());
		//time gasasworebelia
		//	assertEquals(i.getCreateDate().getTime(), test.getCreateDate().getTime());
		i.update("name", "notebook");
		assertEquals(i.getItemName(),"notebook");
		test= new ItemsHaveObject(d, q, i.getItemId());
		assertEquals (i.getItemName(),test.getItemName());
		i.delete();
		test= new ItemsHaveObject(d, q, i.getItemId());
	//	assertEquals(test, null);
	}
	@Test
	public void TestUpdate(){
		
		
		
		
	}
	@Test
	public void testDelete(){
		
	}
}
