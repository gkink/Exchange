package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ModelClasses.Pair;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
 * @author Gio
 * This class contains transactions. It connects to databases to get latest
 * transactions. It uses QueryGenerator to get corresponding query text.
 * Will it use ExecuteQuery class or not is not yet decided.
 */
public class TransactionContainer2 {
	
	private List<TransactionInterface> transactions;
	private List<Pair<Integer, String>> IDs;
//	private DBqueryGenerator generator;
	private int size = 0;
	private ResultSet transactionIDs;
	private QueryExecutor executor;
	
	private ResultSet getTransactionIDSet(String query){
		return executor.selectResult(query);
	}
	
	private void getTransactionIDs(){
		try {
			while(transactionIDs.next()){
				Pair<Integer, String> pair = new Pair<Integer, String>(transactionIDs.getInt(1), transactionIDs.getString(2));
				IDs.add(pair);	
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
	
	private void init(QueryExecutor executor, DBqueryGenerator generator){
		IDs = new ArrayList<Pair<Integer, String>>();
		transactions = new ArrayList<TransactionInterface>();
//		this.generator = generator;
		this.executor = executor;
	}
	
	public TransactionContainer2(QueryExecutor executor, DBqueryGenerator generator){
		init(executor, generator);
		transactionIDs = getTransactionIDSet(generator.getTransactionsQuery());
		getTransactionIDs();
	}
	
	public TransactionContainer2(QueryExecutor executor, DBqueryGenerator generator, int userID){
		init(executor, generator);
		transactionIDs = getTransactionIDSet(generator.getTrasactionsForUserQuery(userID));
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
	
	public List<Pair<Integer, String>> getIDsAndDate(){
		return IDs;
	}
}
