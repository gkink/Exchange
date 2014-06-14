package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ModelClasses.Cycle;
import ModelClasses.Pair;
import ModelClasses.User;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class Transaction implements TransactionInterface{
	
	private int ID, size = 0;
	private DateTime dateTime;
	private DBqueryGenerator generator;
	private List<Pair<User, String>> userItemPairs;
	private QueryExecutor executor;
	
	private void getTransactionFromBases(){
		ResultSet res = executor.selectResult(generator.getTransactionQuery(ID));
		try {
			while(res.next()){
				User user = new User(executor, generator, res.getInt(1));
				userItemPairs.add(new Pair<User, String>(user, res.getString(2)));
				size++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { if(null!=res)res.close();} catch (SQLException e)
			{e.printStackTrace();}
		}
	}
	
	private void createNewTransaction(Cycle cycle){
		//TODO
	}
 	
	public Transaction(QueryExecutor executor, DBqueryGenerator generator, int ID, DateTime dateTime){
		this.ID = ID;
		this.dateTime = dateTime;
		this.executor = executor;
		this.generator = generator;
		getTransactionFromBases();
	}
	
	
	public Transaction(QueryExecutor executor, DBqueryGenerator generator, Cycle cycle){
		this.executor = executor;
		this.generator = generator;
		createNewTransaction(cycle);
	}
	
	@Override
	public Pair<User, String> getUserItemPair(int num){
		return userItemPairs.get(num);
	}
	
	@Override
	public int transactionSize(){
		return size;
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
	
	@Override
	public String toString(){
		String part1 = "Transaction ID = " + ID + ". Transaction created on " + dateTime.getDate() + " at " + dateTime.getTime() + "/n";
		StringBuilder build = new StringBuilder(part1);
		for(int i = 0; i < size; i++){
			build.append(userItemPairs.get(i).getFirst().getFirstName() + " " + userItemPairs.get(i).getFirst().getLastName() + " --|-- " + userItemPairs.get(i).getSecond() + "/n");
		}
		return build.toString();
	}
}
