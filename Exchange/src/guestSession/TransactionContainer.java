package guestSession;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbClasses.DBqueryGenerator;

/**
 * @author Gio
 * This class contains transactions. It connects to databases to get latest
 * transactions. It uses QueryGenerator to get corresponding query text.
 * Will it use ExecuteQuery class or not is not yet decided.
 */
public class TransactionContainer {
	
	private List<Transaction> transactions;
	private DBqueryGenerator queryGenerator;
	private int currentTransaction = 0;
	private ResultSet transactionIDs;
	
	private ResultSet getTransactionIDSet(String query){
		try {
		//	ConnectionPool pool = ConnectionPool.getInstance();
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
	
	private void createTransactions(){
		try {
			while(transactionIDs.next()){
				DateTime dateTime = new DateTime(transactionIDs.getString(2));
				Transaction tr = new Transaction(transactionIDs.getInt(1), dateTime);
				transactions.add(tr);	
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
		transactions = new ArrayList<Transaction>();
		queryGenerator = new DBqueryGenerator();
	}
	
	public TransactionContainer(){
		init();
		transactionIDs = getTransactionIDSet(queryGenerator.getTransactionsQuery());
		createTransactions();
	}
	
	public TransactionContainer(int userID){
		init();
		transactionIDs = getTransactionIDSet(queryGenerator.getTrasactionsForUserQuery(userID));
		createTransactions();
	}
	
	public Transaction getTransaction(int num){
		return transactions.get(num);
	}
	
	public Transaction getNextTransaction(){
		return transactions.get(currentTransaction++);
	}
}
