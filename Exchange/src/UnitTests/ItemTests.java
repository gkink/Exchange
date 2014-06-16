package UnitTests;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import guestSession.DateTime;
import guestSession.Item;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class ItemTests {
	DBqueryGenerator gen;
	QueryExecutor exe;
	BasicDataSource s;
	Item select;
	Item normal;
	DateTime date;
	@Before
	public void SetUp(){
		gen= new DBqueryGenerator();
		BasicDataSource s= new BasicDataSource();
		s.setDriverClassName("com.mysql.jdbc.Driver");
		s.setUsername("root");
		s.setPassword("123456");
		s.setUrl("jdbc:mysql://localhost:3306/Exchange");
		exe= new QueryExecutor(s);
		
	
		date = new DateTime("2014/06/01 04:32");
		normal= new Item(gen, exe, "laptop", "chedavs", "laptopnetbook",1, date);
		
		
	}
	
	@Test
	public void TestGetParams(){
		assertEquals(normal.getItemCreateDate(), date);	
		assertEquals(normal.getItemDescription(), "chedavs");	
		assertEquals(normal.getItemName(), "laptop");
		assertEquals(normal.getItemOwner(), 1);	
		
	
	}
	@Test
	public void TestInsert(){
	//	normal.insert(0);
		ResultSet rs= exe.selectResult("Select name from itemsHave where description='chedavs'");
		String name=null;
		
		try {
			while(rs.next())name = rs.getString("name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		assertEquals(normal.getItemName(),name );
	}
	@Test
	public void TestSelect(){
		select=new Item(gen, exe, 1);

		assertEquals(normal.getItemName(), select.getItemName());
	}
	
	@Test
	public void TestUpdate(){
		String check="aghar chedavs";
		select=new Item(gen, exe, 1);
		select.update(0, "description", check);
		ResultSet rs= exe.selectResult("Select ID from itemsHave where description='"+ check+"'");
		int ID=0;
		
		try {
			while(rs.next())ID = rs.getInt("ID");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ID);
	//	System.out.println(select.ge);
		assertEquals(ID, 1);
		
	}
	

}
