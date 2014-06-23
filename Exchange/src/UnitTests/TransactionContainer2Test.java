package UnitTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import guestSession.DateTime;
import guestSession.Transaction;
import guestSession.TransactionContainer2;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.junit.Before;
import org.junit.Test;

import ModelClasses.Pair;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class TransactionContainer2Test {
	
	private TransactionContainer2 container, container2;
	private QueryExecutor executor;
	private DBqueryGenerator generator;
	private ResultSet set, set2;
	private Transaction transaction;
	
	public static final int userID = 3;

	@Before
	public void init() throws SQLException {
		
		transaction = mock(Transaction.class);
		Date date = new Date(1000);
		Time time = new Time(1000);
		
		set = mock(ResultSet.class);
		when(set.next()).thenReturn(true, true, true, false);
		when(set.getInt(1)).thenReturn(1, 2, 3);
		when(set.getDate(2)).thenReturn(date, date, date);
		when(set.getTime(2)).thenReturn(time, time, time);
		
		set2 = mock(ResultSet.class);
		when(set2.next()).thenReturn(true, true, true, false);
		when(set2.getInt(1)).thenReturn(2, 4, 6);
		when(set2.getDate(2)).thenReturn(date, date, date);
		when(set2.getTime(2)).thenReturn(time, time, time);
		
		generator = mock(DBqueryGenerator.class);
		when(generator.getTransactionsQuery(TransactionContainer2.NUMBER_OF_TOP_TRANSACTIONS)).thenReturn("test for top tuples");
		when(generator.getTrasactionsForUserQuery(userID)).thenReturn("test for user transactions");
		
		executor = mock(QueryExecutor.class);
		when(executor.selectResult("test for top tuples")).thenReturn(set);
		when(executor.selectResult("test for user transactions")).thenReturn(set2);
		
		container = new TransactionContainer2(executor, generator);
		
		container2 = new TransactionContainer2(executor, generator, userID);
	}
	
	@Test
	public void numOfPairsTest(){
		assertEquals(3, container.numOfPairs());
		assertEquals(3, container2.numOfPairs());
	}
	
	@Test
	public void getPairTest(){
		Date date = new Date(1000);
		Time time = new Time(1000);
		DateTime dateTime = new DateTime(date, time);
		
		for (int i = 0; i < container.numOfPairs(); i++){
			assertEquals(container.getPair(i).getFirst(), new Integer(i+1));
			assertEquals(container.getPair(i).getSecond(), dateTime);
		}
		for (int i = 0; i < container2.numOfPairs(); i++){
			assertEquals(container2.getPair(i).getFirst(), new Integer((i+1)*2));
			assertEquals(container2.getPair(i).getSecond(), dateTime);
		}
	}
	
	@Test
	public void getPairTest2(){
		Date date = new Date(1000);
		Time time = new Time(1000);
		DateTime dateTime = new DateTime(date, time);
		
		for (int i = 0; i < container.numOfPairs(); i++){
			assertEquals(container.getPair(i), new Pair<Integer, DateTime>(i+1, dateTime));
		}
		for (int i = 0; i < container2.numOfPairs(); i++){
			assertEquals(container2.getPair(i), new Pair<Integer, DateTime>((i+1)*2, dateTime));
		}
	}
	
	@Test
	public void addTransactionTest(){
		assertEquals(0, container.size());
		container.addTransaction(transaction);
		assertEquals(1, container.size());
		
		assertEquals(0, container2.size());
		container2.addTransaction(transaction);
		assertEquals(1, container2.size());
	}
	
	@Test
	public void sizeTest(){
		assertEquals(0, container.size());
		for (int i = 0; i < container.numOfPairs(); i++){
			container.addTransaction(transaction);
		}
		assertEquals(3, container.size());
		
		assertEquals(0, container2.size());
		for (int i = 0; i < container2.numOfPairs(); i++){
			container2.addTransaction(transaction);
		}
		assertEquals(3, container2.size());
	}
	
	@Test
	public void getTransactionTest(){
		container.addTransaction(transaction);
		container.addTransaction(null);
		assertSame(container.getTransaction(0), transaction);
		assertSame(container.getTransaction(1), null);
		
		container2.addTransaction(transaction);
		container2.addTransaction(null);
		assertSame(container2.getTransaction(0), transaction);
		assertSame(container2.getTransaction(1), null);
	}
}
