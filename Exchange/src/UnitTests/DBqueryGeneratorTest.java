package UnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dbClasses.DBqueryGenerator;
import dbClasses.clauseHelper;

public class DBqueryGeneratorTest {

	private DBqueryGenerator generator;
	
	@Before
	public void setUp() throws Exception {
		generator = new DBqueryGenerator();
	}
	
	@Test
	public void testForInsert() {
		String[] columns = new String[]{
			"id", "userName", "password"
		};
		
		String[] values = new String[]{
			"1", "giorgi", "giorgi"	
		};

		String insertTester = generator.generateInsert("users", columns, new String[]{});
		assertNull(insertTester);

		
		insertTester = generator.generateInsert("users", columns, values);
		assertEquals("insert into users (id, userName, password) values ('1', 'giorgi', 'giorgi')", insertTester);		
	}
	
	@Test
	public void testForSelect(){
		String[] columns = new String[]{
			"productid", "name"
		};
		
		clauseHelper[] values = new clauseHelper[]{
				new clauseHelper("productid", "like", "LH"),
				new clauseHelper("price", ">", "1"),
		};
		
		String selectTester = generator.generateSelect("products", columns, values, "and");
		//this query is tested also on the database of our fifth assignment
		assertEquals("select productid, name from products where productid like '%LH%' and price > '1' ", selectTester);
	}
}
