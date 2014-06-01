package guestSession;

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
	
	private List<Transaction > transactions;
	
	public TransactionContainer(){
		transactions = new ArrayList<Transaction>();
		DBqueryGenerator queryGenerator = new DBqueryGenerator();
		String query = queryGenerator.getTransactionsQuery();
		
	}
	
	public TransactionContainer(int userID){
		
	}
	
	public Transaction getTransaction(int num){
		return null;
	}
	
	public Transaction getNextTransaction(int num){
		return null;
	}
}
