package guestSession;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dbClasses.DBqueryGenerator;
import dbConnection.ConnectionPool;

public class Transaction implements TransactionInterface{
	
	private int ID;
	private DateTime dateTime;
	private Connection con;
	private ConnectionPool pool;
	private DBqueryGenerator queryGenerator;
	private Map<String, String> transaction;
	
	private ResultSet itemInfo(){
		queryGenerator = new DBqueryGenerator();
		String query = queryGenerator.getTransactionQuery(ID);
		try {
			pool = ConnectionPool.getInstance();
			con = pool.getConnectionFromPool();
			ResultSet set = con.createStatement().executeQuery(query);
			return set;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void closeResultSet(ResultSet set){
		try {
			set.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getUserAndItemName(int itemID){
		String query = queryGenerator.getItemChangedWithUser(itemID);
		try {
			ResultSet set = con.createStatement().executeQuery(query);
			set.next();
			set.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getTransactionFromBases(){
		ResultSet itemIDs = itemInfo();
		try {
			while(itemIDs.next()){
				getUserAndItemName(itemIDs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResultSet(itemIDs);
		pool.returnConnectionToPool(con);
	}
	
	public void createNewTransaction(){
		//TODO
	}
 	
	public Transaction(int ID, DateTime dateTime){
		this.ID = ID;
		this.dateTime = dateTime;
		getTransactionFromBases();
	}
	
	public Transaction(){
		createNewTransaction();
	}

	@Override
	public Iterator<String> getUsers() {
 		return transaction.keySet().iterator();
	}

	@Override
	public Iterator<String> getItems() {
		return transaction.values().iterator();
	}

	@Override
	public Map<String, String> getItemsAndUsers() {
		return transaction;
	}

	@Override
	public void addToTheBases() {
		//TODO
	}

	@Override
	public DateTime getDateTime() {
		return dateTime;
	}

	@Override
	public int getID() {
		return ID;
	}
}
