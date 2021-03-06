package UnitTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

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
			DataSource datasource = new DataSource() {
			
			@Override
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setLoginTimeout(int seconds) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setLogWriter(PrintWriter out) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Logger getParentLogger() throws SQLFeatureNotSupportedException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getLoginTimeout() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public PrintWriter getLogWriter() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public java.sql.Connection getConnection(String username, String password)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public java.sql.Connection getConnection() throws SQLException {
				Connection con=null;
				try { 
					Class.forName("com.mysql.jdbc.Driver"); 
					con = (Connection) DriverManager.getConnection 
							( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD); 
					Statement stmt = (Statement) con.createStatement(); 
					stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME); 
					}catch (ClassNotFoundException e) { 
						e.printStackTrace(); 
				}
				return con;
			}
		};
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
