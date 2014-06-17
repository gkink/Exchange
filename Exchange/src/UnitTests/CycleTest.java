package UnitTests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class CycleTest {
	
	private QueryExecutor executor;
	private ResultSet rs;
	
	@Before
	public void setup(){
		executor = mock(QueryExecutor.class);	
		when(executor.selectResult(anyString())).thenReturn(rs);
	}

	@Test
	public void tesBasicConstructor() {
	}

}
