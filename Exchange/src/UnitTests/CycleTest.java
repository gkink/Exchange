package UnitTests;

import static org.junit.Assert.*;
import guestSession.DateTime;
import guestSession.ItemInterface;
import guestSession.ItemsHaveObject;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import ModelClasses.Cycle;
import ModelClasses.Pair;
import ModelClasses.User;
import static org.mockito.Mockito.*;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class CycleTest {
	
	private QueryExecutor executor;
	private DBqueryGenerator generator;
	private ResultSet rs1;
	private ResultSet rs2;
	private ResultSet rs3;
	
	private static final String sampleString = "sampleString";
	private static final int sampleInt = 0;
	private static final Time sampleTime = new Time(0);
	private static final Date sampleDate = new Date(0);
	private static final DateTime sampleDateTime = new DateTime(sampleDate, sampleTime);
	
	private final String firstRes = "first";
	private final String secondRes = "second";
	private final String thirdRes = "third";
	
	private User testerUser;
	
	@Before
	public void setup(){
		rs1 = mock(ResultSet.class);
		rs2 = mock(ResultSet.class);
		rs3 =  mock(ResultSet.class);
		
		generator = mock(DBqueryGenerator.class);
		
		
		when(generator.getCycleSelect(anyInt())).thenReturn(firstRes);
		when(generator.getUserQuery(anyInt())).thenReturn(secondRes);
		when(generator.getItemSelectQuery(anyString(),anyInt())).thenReturn(thirdRes);
		
		try {
			when(rs1.next()).thenReturn(true, false);
			when(rs1.getString(anyString())).thenReturn(sampleString);	
			
			when(rs2.next()).thenReturn(true, false);
			when(rs2.getInt(anyString())).thenReturn(sampleInt);
			when(rs2.getString(anyString())).thenReturn(sampleString);
			
			when(rs3.next()).thenReturn(true, false);
			when(rs3.getInt(anyString())).thenReturn(sampleInt);
			when(rs3.getString(anyString())).thenReturn(sampleString);
			when(rs3.getDate(anyString())).thenReturn(sampleDate);
			when(rs3.getTime(anyString())).thenReturn(sampleTime);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		executor = mock(QueryExecutor.class);	
		when(executor.selectResult(firstRes)).thenReturn(rs1);
		when(executor.selectResult(secondRes)).thenReturn(rs2);
		when(executor.selectResult(thirdRes)).thenReturn(rs3);
		
		
		testerUser = new User(executor, generator, sampleString, sampleString, sampleString, sampleString);
	}

	@Test
	public void tesBasicConstructor() {
		
	}
	
	@Test
	public void testSelectConstructor(){
		Cycle totest = new Cycle(executor, generator, sampleInt);
		Pair<User, ItemsHaveObject> testerPair = totest.getUserItemPair(0);
		
		User userToTest = testerPair.getFirst();
		ItemsHaveObject itemToTest = testerPair.getSecond();
		
		assertEquals(testerUser, userToTest);
		
		assertEquals(sampleString, itemToTest.getItemDescription());
		assertEquals(sampleString, itemToTest.getItemKeywords());
		assertEquals(sampleString, itemToTest.getItemName());
		assertEquals(sampleInt, itemToTest.getItemOwner());
		assertEquals(sampleDateTime, itemToTest.getCreateDate());
	}

}
