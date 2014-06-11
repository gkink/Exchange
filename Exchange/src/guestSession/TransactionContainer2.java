package guestSession;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbClasses.DBqueryGenerator;
import dbConnection.ConnectionPool;

/**
 * @author Gio
 * This class contains transactions. It connects to databases to get latest
 * transactions. It uses QueryGenerator to get corresponding query text.
 * Will it use ExecuteQuery class or not is not yet decided.
 */
public class TransactionContainer2 {
	
	private List<TransactionInterface> transactions;
	private Map<Integer, String> IDs;
	private DBqueryGenerator queryGenerator;
	private int size = 0;
	private ResultSet transactionIDs;
	
	private ResultSet getTransactionIDSet(String query){
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			Connection con = pool.getConnectionFromPool();
			ResultSet set = con.createStatement().executeQuery(query);
			pool.returnConnectionToPool(con);
			return set;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void getTransactionIDs(){
		try {
			while(transactionIDs.next()){
				IDs.put(transactionIDs.getInt(1), transactionIDs.getString(2));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResultSet();
	}
	
	private void closeResultSet(){
		try {
			transactionIDs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void init(){
		IDs = new HashMap<Integer, String>();
		transactions = new ArrayList<TransactionInterface>();
		queryGenerator = new DBqueryGenerator();
	}
	
	public TransactionContainer2(){
		init();
		transactionIDs = getTransactionIDSet(queryGenerator.getTransactionsQuery());
		getTransactionIDs();
	}
	
	public TransactionContainer2(int userID){
		init();
		transactionIDs = getTransactionIDSet(queryGenerator.getTrasactionsForUserQuery(userID));
		getTransactionIDs();
	}
	
	public TransactionInterface getTransaction(int num){
		return transactions.get(num);
	}
	
	public void addTransaction(TransactionInterface transaction){
		transactions.add(transaction);
		size++;
	}
	
	public int size(){
		return size;
	}
	
	public Map<Integer, String> getIDsAndDate(){
		return IDs;
	}
}
