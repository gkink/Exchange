package UnitTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import guestSession.DateTime;
import guestSession.ItemInterface;
import guestSession.ItemsChanged;
import guestSession.ItemsHaveObject;
import guestSession.Transaction;
import guestSession.TransactionContainer2;

import org.junit.Before;
import org.junit.Test;

import ModelClasses.CycleInterface;
import ModelClasses.Pair;
import ModelClasses.User;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class TransactionTest {
	
	private QueryExecutor executor;
	private DBqueryGenerator generator;
	private ResultSet set, userSet, itemSet, set2;
	private Transaction transaction, transaction2;
	private Date date;
	private Time time;
	private DateTime dateTime;
	private CycleInterface cycle;
	
	public static final int transactionID = 3;

	@Before
	public void init() throws SQLException {
		
		date = new Date(1000);
		time = new Time(10000);
		dateTime = new DateTime(date, time);
		
		set = mock(ResultSet.class);
		when(set.next()).thenReturn(true, true, true, false);
		when(set.getInt(1)).thenReturn(1, 2, 3);
		when(set.getInt(2)).thenReturn(1, 2, 3);
		
		userSet = mock(ResultSet.class);
		when(userSet.next()).thenReturn(true, false, true, false, true, false);
		when(userSet.getString("firstName")).thenReturn("user1", "user2", "user3");
		when(userSet.getString("lastName")).thenReturn("user1son", "user2son", "user3son");
		when(userSet.getString("email")).thenReturn("@user1", "@user2", "@user3");
		when(userSet.getInt("ranking")).thenReturn(0, 0, 0);
		when(userSet.getString("password")).thenReturn("p", "p", "p");
		
		itemSet = mock(ResultSet.class);
		when(itemSet.next()).thenReturn(true, false, true, false, true, false);
		when(itemSet.getInt("ID")).thenReturn(1, 2, 3);
		when(itemSet.getString("name")).thenReturn("item1", "item2", "item3");
		when(itemSet.getInt("userId")).thenReturn(1, 2, 3);
		
		set2 = mock(ResultSet.class);
		when(set2.next()).thenReturn(true, false);
		when(set2.getTime(2)).thenReturn(time);
		when(set2.getDate(2)).thenReturn(date);
		
		generator = mock(DBqueryGenerator.class);
		when(generator.getTransactionQuery(transactionID)).thenReturn("test for transaction select");
		when(generator.getTransactionQuery(7)).thenReturn("getDateTime");
		when(generator.insertIntoTransactions()).thenReturn("return7");
		
		when(generator.getUserQuery(1)).thenReturn("user1");
		when(generator.getUserQuery(2)).thenReturn("user2");
		when(generator.getUserQuery(3)).thenReturn("user3");
		
		when(generator.insertIntoUsers(0, "user1", "user1son", "@user1", "p")).thenReturn("insert1");
		when(generator.insertIntoUsers(0, "user2", "user2son", "@user2", "p")).thenReturn("insert2");
		when(generator.insertIntoUsers(0, "user3", "user3son", "@user3", "p")).thenReturn("insert3");
		
		when(generator.getItemSelectQuery("itemsChanged", 1)).thenReturn("item1");
		when(generator.getItemSelectQuery("itemsChanged", 2)).thenReturn("item2");
		when(generator.getItemSelectQuery("itemsChanged", 3)).thenReturn("item3");
		
		when(generator.getItemsChangedInsertQuery(1, "item1")).thenReturn("itemInsert1");
		when(generator.getItemsChangedInsertQuery(2, "item2")).thenReturn("itemInsert2");
		when(generator.getItemsChangedInsertQuery(3, "item3")).thenReturn("itemInsert3");
		when(generator.getItemsChangedInsertQuery(1, "item1insert")).thenReturn("itemInsert4");
		when(generator.getItemsChangedInsertQuery(2, "item2insert")).thenReturn("itemInsert5");
		
		executor = mock(QueryExecutor.class);
		when(executor.selectResult("test for transaction select")).thenReturn(set);
		when(executor.selectResult("getDateTime")).thenReturn(set2);
		when(executor.executeQuery("return7")).thenReturn(7);
		
		when(executor.selectResult("user1")).thenReturn(userSet);
		when(executor.selectResult("user2")).thenReturn(userSet);
		when(executor.selectResult("user3")).thenReturn(userSet);
		
		when(executor.executeQuery("insert1")).thenReturn(1);
		when(executor.executeQuery("insert2")).thenReturn(2);
		when(executor.executeQuery("insert3")).thenReturn(3);
		
		when(executor.selectResult("item1")).thenReturn(itemSet);
		when(executor.selectResult("item2")).thenReturn(itemSet);
		when(executor.selectResult("item3")).thenReturn(itemSet);
		
		when(executor.executeQuery("itemInsert1")).thenReturn(1);
		when(executor.executeQuery("itemInsert2")).thenReturn(2);
		when(executor.executeQuery("itemInsert3")).thenReturn(3);
		when(executor.executeQuery("itemInsert4")).thenReturn(1);
		when(executor.executeQuery("itemInsert5")).thenReturn(2);
		
		transaction = new Transaction(executor, generator, transactionID, dateTime);
		
		cycle = mock(CycleInterface.class);
		
		when(cycle.cycleSize()).thenReturn(2);
		
		User user1 = new User(executor, generator, "user1cycle", "user1soncycle", "@user1", "p");
		ItemsHaveObject item1 = new ItemsHaveObject(generator, executor, "item1insert", "desc1", "kayword1", 1, dateTime);
		Pair<User, ItemsHaveObject> pair1 = new Pair<User, ItemsHaveObject>(user1, item1);
		
		User user2 = new User(executor, generator, "user2cycle", "user2soncycle", "@user2", "p");
		ItemsHaveObject item2 = new ItemsHaveObject(generator, executor, "item2insert", "desc2", "kayword2", 2, dateTime);
		Pair<User, ItemsHaveObject> pair2 = new Pair<User, ItemsHaveObject>(user2, item2);
		
		when(cycle.getUserItemPair(0)).thenReturn(pair1);
		when(cycle.getUserItemPair(1)).thenReturn(pair2);
		
		transaction2 = new Transaction(executor, generator, cycle);
		transaction2.addToTheBases();
	}
	
	@Test
	public void sizeTest(){
		assertEquals(3, transaction.size());
		assertEquals(2, transaction2.size());
	}
	
	@Test
	public void getDateTimeTest(){
		assertEquals(dateTime, transaction.getDateTime());
		assertNotEquals(null, transaction.getDateTime());
		assertEquals(dateTime, transaction2.getDateTime());
	
		Date date = new Date(100);
		Time time = new Time(1000);
		DateTime dateTime = new DateTime(date, time);
		assertNotEquals(dateTime, transaction.getDateTime());
		date.setTime(1000);
		time.setTime(10000);
		assertEquals(dateTime, transaction.getDateTime());
		assertEquals(dateTime, transaction2.getDateTime());
	}
	
	@Test
	public void getIDTest(){
		assertEquals(transaction.getID(), transactionID);
		assertEquals(transaction2.getID(), 7);
	}
	
	@Test
	public void getUserItemPairTest(){
		String [][] userNames = {{"user1", "user2", "user3"},
				{"user1son", "user2son", "user3son"},
				{"@user1", "@user2", "@user3"},
				{"item1", "item2", "item3"}};
		for (int i = 0; i < transaction.size(); i++){
			
			User user = new User(executor, generator, userNames[0][i], userNames[1][i], userNames[2][i], "p");
			user.addToUsers();
			ItemsChanged item = new ItemsChanged(generator, executor, i+1, userNames[3][i]);
			item.insert();
			
			Pair<User, ItemsChanged> pair = new Pair<User, ItemsChanged>(user, item);
			assertEquals(transaction.getUserItemPair(i), pair);
		}
		System.out.println(transaction2);
		System.out.println(transaction);
	}
}
