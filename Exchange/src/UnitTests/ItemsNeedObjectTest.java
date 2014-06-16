package UnitTests;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


import guestSession.ItemsNeedObject;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import dbConnection.MyDBInfo;

public class ItemsNeedObjectTest {
	ItemsNeedObject i;
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
		i=new ItemsNeedObject(d, q, 1,"laptop","netbookcomp");
	}
	
	@Test
	public void testParams(){
		assertEquals(i.getItemName(),"laptop");
		assertEquals(i.getItemOwner(),1);
		assertEquals(i.getItemKeywords(), "netbookcomp");
		
	}
	@Test
	public void TestInsertAndSelectandUpdateandDelete(){
		i.insert();
		ItemsNeedObject test= new ItemsNeedObject(d, q, i.getItemId());
		assertEquals(i.getItemName(),test.getItemName());
		assertEquals(i.getItemOwner(),test.getItemOwner());
		assertEquals(i.getItemKeywords(), test.getItemKeywords());
		i.update("name", "notebook");
		assertEquals(i.getItemName(),"notebook");
		test= new ItemsNeedObject(d, q, i.getItemId());
		assertEquals (i.getItemName(),test.getItemName());
		i.delete();
		test= new ItemsNeedObject(d, q, i.getItemId());
	//	assertEquals(test, null);
	}
	@Test
	public void TestUpdate(){
		
		
		
		
	}
	@Test
	public void testDelete(){
		
	}
}

