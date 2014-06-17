package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	private void getTransactionFromBases(){
		ResultSet res = executor.selectResult(generator.getTransactionQuery(ID));
		try {
			while(res.next()){
				User user = new User(executor, generator, res.getInt(1));
				ItemsChanged item = new ItemsChanged(generator, executor, res.getInt(2));
				userItemPairs.add(new Pair<User, ItemsChanged>(user, item));
				size++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { if(null!=res)res.close();} catch (SQLException e)
			{e.printStackTrace();}
		}
	}
	
	private Pair<User, ItemsChanged> createUserItemPair(Pair<User, ItemInterface> pair){
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
		for (int i = 0; i < size(); i++){
			executor.executeQuery(generator.insertIntoTransactionInfo(ID, getUserItemPair(i).getSecond().getItemId()));
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
		String part1 = "Transaction ID = " + ID + ". Transaction created on " + dateTime.getDate() + " at " + dateTime.getTime() + "\n";
		StringBuilder build = new StringBuilder(part1);
		for(int i = 0; i < size; i++){
			build.append(userItemPairs.get(i).getFirst().getFirstName() + " " + userItemPairs.get(i).getFirst().getLastName() + " --|-- " + userItemPairs.get(i).getSecond().getItemName() + "\n");
		}
		return build.toString();
	}
	
	public static void main(String[] args) {
		String [][] k = {{"erti", "ori"},{"sami", "otxi"}};
		System.out.println(k[0][1]);
	}
}
