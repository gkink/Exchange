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
 * transactions or to get transactions for a certain user. It uses 
 * QueryGenerator and ExecuteQuary to get corresponding ResultSet.
 * 
 */
public class TransactionContainer2 {
	
	//
	public static final int NUMBER_OF_TOP_TRANSACTIONS = 7;
	
	private List<TransactionInterface> transactions;
	private List<Pair<Integer, String>> IDs;
//	private DBqueryGenerator generator;
	private int size = 0, numOfIDs = 0;
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
				numOfIDs++;
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
	
	/**
	 * Creates new TransactionContainer with the last transaction ID_s in it.
	 * @param executor
	 * @param generator
	 */
	public TransactionContainer2(QueryExecutor executor, DBqueryGenerator generator){
		init(executor, generator);
		transactionIDs = getTransactionIDSet(generator.getTransactionsQuery(NUMBER_OF_TOP_TRANSACTIONS));
		getTransactionIDs();
	}
	
	
	/**
	 * Creates new TransactionContainer object for the user whose ID equals userID.
	 * @param executor
	 * @param generator
	 * @param userID
	 */
	public TransactionContainer2(QueryExecutor executor, DBqueryGenerator generator, int userID){
		init(executor, generator);
		transactionIDs = getTransactionIDSet(generator.getTrasactionsForUserQuery(userID));
		getTransactionIDs();
	}
	
	/**
	 * Returns num_th transaction in the container
	 * @param num
	 * @return TransactionInterface
	 */
	public TransactionInterface getTransaction(int num){
		return transactions.get(num);
	}
	
	
	/**
	 * Adds new Transaction into the container.
	 * @param transaction
	 */
	public void addTransaction(TransactionInterface transaction){
		transactions.add(transaction);
		size++;
	}
	
	/**
	 * Returns size of the container (where size means how many transaction it contains).
	 * Note: it doesn't return how many transaction ID_s it contain. It just returns how many transaction objects it has.
	 * @return int
	 */
	public int size(){
		return size;
	}
	
	
	/**
	 * Returns number of Pair objects.
	 * @return int
	 */
	public int numOfPairs(){
		return numOfIDs;
	}
	
	/**
	 * Returns a Pair of Integer and String, where integer
	 * represents ID of a transaction and String represents its dateTime.
	 * @return Pair of Integer and String.
	 */
	public Pair<Integer, String> getPair(int num){
		return IDs.get(num);
	}
}
