package UnitTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import guestSession.Transaction;
import guestSession.TransactionContainer2;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import ModelClasses.Pair;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class TransactionContainer2Test {
	
	private TransactionContainer2 container;
	private QueryExecutor executor;
	private DBqueryGenerator generator;
	private ResultSet set;
	private Transaction transaction;

	@Before
	public void init() throws SQLException {
		
		transaction = mock(Transaction.class);
		
		set = mock(ResultSet.class);
		when(set.next()).thenReturn(true, true, true, false);
		when(set.getInt(1)).thenReturn(1, 2, 3);
		when(set.getString(2)).thenReturn("first", "second", "third");
		
		generator = mock(DBqueryGenerator.class);
		when(generator.getTransactionsQuery(TransactionContainer2.NUMBER_OF_TOP_TRANSACTIONS)).thenReturn("test for top tuples");
		
		executor = mock(QueryExecutor.class);
		when(executor.selectResult("test for top tuples")).thenReturn(set);
		
		container = new TransactionContainer2(executor, generator);
	}
	
	@Test
	public void numOfPairsTest(){
		assertEquals(3, container.numOfPairs());
	}
	
	@Test
	public void getPairTest(){
		String [] arr = {"first", "second", "third"};
		for (int i = 0; i < container.numOfPairs(); i++){
			assertEquals(container.getPair(i).getFirst(), new Integer(i+1));
			assertEquals(container.getPair(i).getSecond(), arr[i]);
		}
	}
	
	@Test
	public void getPairTest2(){
		String [] arr = {"first", "second", "third"};
		for (int i = 0; i < container.numOfPairs(); i++){
			assertEquals(container.getPair(i), new Pair<Integer, String>(i+1, arr[i]));
		}
	}
	
	@Test
	public void addTransactionTest(){
		assertEquals(0, container.size());
		container.addTransaction(transaction);
		assertEquals(1, container.size());
	}
	
	@Test
	public void sizeTest(){
		assertEquals(0, container.size());
		for (int i = 0; i < container.numOfPairs(); i++){
			container.addTransaction(transaction);
		}
		assertEquals(3, container.size());
	}
	
	@Test
	public void getTransactionTest(){
		container.addTransaction(transaction);
		container.addTransaction(null);
		assertSame(container.getTransaction(0), transaction);
		assertSame(container.getTransaction(1), null);
	}
}
