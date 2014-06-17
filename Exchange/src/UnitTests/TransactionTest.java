package UnitTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import guestSession.DateTime;
import guestSession.ItemsChanged;
import guestSession.Transaction;
import guestSession.TransactionContainer2;

import org.junit.Before;
import org.junit.Test;

import ModelClasses.Pair;
import ModelClasses.User;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class TransactionTest {
	
	private QueryExecutor executor;
	private DBqueryGenerator generator;
	private ResultSet set, userSet, itemSet;
	private Transaction transaction;
	private Date date;
	private Time time;
	private DateTime dateTime;
	
	public static final int transactionID = 3;

	@Before
	public void init() throws SQLException {
		
		set = mock(ResultSet.class);
		when(set.next()).thenReturn(true, true, true, false);
		when(set.getInt(1)).thenReturn(1, 2, 3);
		when(set.getInt(2)).thenReturn(1, 2, 3);
		
		userSet = mock(ResultSet.class);
		when(userSet.next()).thenReturn(true, false, true, false, true, false);
		when(userSet.getString("firstName")).thenReturn("user1", "user2", "user3");
		when(userSet.getString("lastName")).thenReturn("user1son", "user2son", "user3son");
		when(userSet.getString("email")).thenReturn("@user1", "@user2", "@user3");
		when(userSet.getInt("ranking")).thenReturn(1, 2, 3);
		
		itemSet = mock(ResultSet.class);
		when(itemSet.next()).thenReturn(true, false, true, false, true, false);
		when(itemSet.getInt("ID")).thenReturn(1, 2, 3);
		when(itemSet.getString("name")).thenReturn("item1", "item2", "item3");
		when(itemSet.getInt("userId")).thenReturn(1, 2, 3);
		
		generator = mock(DBqueryGenerator.class);
		when(generator.getTransactionQuery(transactionID)).thenReturn("test for transaction select");
		
		when(generator.getUserQuery(1)).thenReturn("user1");
		when(generator.getUserQuery(2)).thenReturn("user2");
		when(generator.getUserQuery(3)).thenReturn("user3");
		
		when(generator.getItemSelectQuery("itemsChanged", 1)).thenReturn("item1");
		when(generator.getItemSelectQuery("itemsChanged", 2)).thenReturn("item2");
		when(generator.getItemSelectQuery("itemsChanged", 3)).thenReturn("item3");
		
		executor = mock(QueryExecutor.class);
		when(executor.selectResult("test for transaction select")).thenReturn(set);
		
		when(executor.selectResult("user1")).thenReturn(userSet);
		when(executor.selectResult("user2")).thenReturn(userSet);
		when(executor.selectResult("user3")).thenReturn(userSet);
		
		when(executor.selectResult("item1")).thenReturn(itemSet);
		when(executor.selectResult("item2")).thenReturn(itemSet);
		when(executor.selectResult("item3")).thenReturn(itemSet);
		
		date = new Date(1000);
		time = new Time(10000);
		dateTime = new DateTime(date, time);
		
		transaction = new Transaction(executor, generator, transactionID, dateTime);
	}
	
	@Test
	public void sizeTest(){
		assertEquals(3, transaction.size());
	}
	
	@Test
	public void getDateTimeTest(){
		assertEquals(dateTime, transaction.getDateTime());
		assertNotEquals(null, transaction.getDateTime());
	
		Date date = new Date(100);
		Time time = new Time(1000);
		DateTime dateTime = new DateTime(date, time);
		assertNotEquals(dateTime, transaction.getDateTime());
		date.setTime(1000);
		time.setTime(10000);
		assertEquals(dateTime, transaction.getDateTime());
	}
	
	@Test
	public void getIDTest(){
		assertEquals(transaction.getID(), transactionID);
	}
	
	@Test
	public void getUserItemPairTest(){
		String [][] userNames = {{"user1", "user2", "user3"},
				{"user1son", "user2son", "user3son"},
				{"@user1", "@user2", "@user3"},
				{"item1", "item2", "item3"}};
		for (int i = 0; i < transaction.size(); i++){
			User user = new User(executor, generator, i, userNames[0][i], userNames[1][i], userNames[2][i]);
			ItemsChanged item = new ItemsChanged(generator, executor, i, userNames[3][i]);
			Pair<User, ItemsChanged> pair = new Pair<User, ItemsChanged>(user, item);
			assertEquals(transaction.getUserItemPair(i), pair);
			
		}
	}
}
