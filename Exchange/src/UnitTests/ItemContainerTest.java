package UnitTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import guestSession.ItemContainer;
import guestSession.ItemsChanged;
import guestSession.ItemsHaveObject;
import guestSession.ItemsNeedObject;
import guestSession.RealItemsObject;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.mysql.fabric.xmlrpc.base.Array;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import dbClasses.DBqueryGenerator;
import dbClasses.MyDBInfo;
import dbClasses.QueryExecutor;


public class ItemContainerTest {
	ItemContainer i;
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
		i=new ItemContainer(d, q);
	}
	@Test
	public void testLatestItems(){
		String query = d.getLatestItems();
		ResultSet rs= q.selectResult(query);
		ArrayList<ItemsHaveObject> test= new ArrayList<>();
		ParseAndInitTest1(test,rs);
		assertEquals(test.size(), i.getLatestItems().size());
		
	}
	private void ParseAndInitTest1(ArrayList<ItemsHaveObject> test,ResultSet rs){	
		try {
			while(rs.next()){
				int ID=rs.getInt("ID");
					ItemsHaveObject cur=new ItemsHaveObject(d, q,ID);
					test.add(cur);
				
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}
}
