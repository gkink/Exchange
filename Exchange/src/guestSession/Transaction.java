package guestSession;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ModelClasses.CycleInterface;
import ModelClasses.Pair;
import ModelClasses.User;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class Transaction implements TransactionInterface{
	
	private int ID, size;
	private DateTime dateTime;
	private DBqueryGenerator generator;
	private List<Pair<User, ItemsChanged>> userItemPairs;
	private QueryExecutor executor;
	private List<Pair<Integer, Integer>> userItemIDs;
	
	private void getTransactionFromBases(){
		userItemIDs = new ArrayList<Pair<Integer, Integer>>();
		ResultSet res = executor.selectResult(generator.getTransactionQuery(ID));
		try {
			while(res.next()){
				userItemIDs.add(new Pair<Integer, Integer>(res.getInt("transactionID"), res.getInt("itemID")));
				size++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { if(null!=res)res.close();} catch (SQLException e){e.printStackTrace();}
			executor.closeVariables();
		}
		for (Pair<Integer, Integer> p: userItemIDs){
			User user = new User(executor, generator, p.getFirst());
			ItemsChanged item = new ItemsChanged(generator, executor, p.getSecond());
			userItemPairs.add(new Pair<User, ItemsChanged>(user, item));
		}
	}
	
	private Pair<User, ItemsChanged> createUserItemPair(Pair<User, ItemsHaveObject> pair){
		ItemsChanged item = new ItemsChanged(generator, executor, pair.getSecond().getItemOwner(), pair.getSecond().getItemName());
		item.insert();
		return new Pair<User, ItemsChanged>(pair.getFirst(), item);
	}

	private void createNewTransaction(CycleInterface cycle){
		for (int i = 0; i < cycle.cycleSize(); i++){
			userItemPairs.add(createUserItemPair(cycle.getUserItemPair(i)));
		}
	}
 	
	public Transaction(QueryExecutor executor, DBqueryGenerator generator, int ID, DateTime dateTime){
		this.ID = ID;
		this.dateTime = dateTime;
		this.executor = executor;
		this.generator = generator;
		size = 0;
		this.userItemPairs = new ArrayList<Pair<User, ItemsChanged>>();
		getTransactionFromBases();
	}
	
	
	public Transaction(QueryExecutor executor, DBqueryGenerator generator, CycleInterface cycle){
		this.executor = executor;
		this.generator = generator;
		size = cycle.cycleSize();
		this.userItemPairs = new ArrayList<Pair<User, ItemsChanged>>();
		createNewTransaction(cycle);
	}
	
	@Override
	public Pair<User, ItemsChanged> getUserItemPair(int num){
		return userItemPairs.get(num);
	}
	
	@Override
	public int size(){
		return size;
	}

	@Override
	public void addToTheBases() {
		ID = executor.executeQuery(generator.insertIntoTransactions());
		executor.closeVariables();
		ResultSet res = executor.selectResult(generator.getTransactionQuery(ID));
		try {
			res.next();
			Date date = res.getDate(2);
			Time time = res.getTime(2);
			dateTime = new DateTime(date, time);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { if(null!=res)res.close();} catch (SQLException e){e.printStackTrace();}
			executor.closeVariables();
		}
		
		for (int i = 0; i < size(); i++){
			executor.executeQuery(generator.insertIntoTransactionInfo(ID, getUserItemPair(i).getSecond().getItemId()));
			executor.closeVariables();
		}
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
		String part1 = "| Transaction ID: " + ID + " " +  dateTime + "\n";
		StringBuilder build = new StringBuilder(part1);
		for(int i = 0; i < size; i++){
			build.append("| userID: " + userItemPairs.get(i).getFirst().getId() + " | Firstname: " + userItemPairs.get(i).getFirst().getFirstName() + " | Lastname: " + userItemPairs.get(i).getFirst().getLastName() + " | itemID: " + userItemPairs.get(i).getSecond().getItemId() + " | itemName: " + userItemPairs.get(i).getSecond().getItemName() + " |\n");
		}
		return build.toString();
	}
}
