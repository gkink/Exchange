package UnitTests;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import dbClasses.QueryExecutor;

public class QueryExecutorTest {
	private QueryExecutor executor;

	@Before
	public void setUp(){
		executor =  new QueryExecutor(datasource);
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
