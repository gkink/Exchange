package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;




import ModelClasses.Cycle;
//import ModelClasses.Cycle;
import ModelClasses.CycleInterface;
import ModelClasses.ItemInterface;
import ModelClasses.LittleItem;
import ModelClasses.Pair;
import ModelClasses.User;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class Transaction implements TransactionInterface{
	
	private int ID, size = 0;
	private DateTime dateTime;
	private DBqueryGenerator generator;
	private List<Pair<User, ItemInterface>> userItemPairs;
	private QueryExecutor executor;
	private CycleInterface cycle;
	
	private void getTransactionFromBases(){
		ResultSet res = executor.selectResult(generator.getTransactionQuery(ID));
		try {
			while(res.next()){
				User user = new User(executor, generator, res.getInt(1));
				ItemInterface item = new LittleItem(executor, generator, res.getInt(2));
				userItemPairs.add(new Pair<User, ItemInterface>(user, item));
				size++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { if(null!=res)res.close();} catch (SQLException e)
			{e.printStackTrace();}
		}
	}
	

	private void createNewTransaction(CycleInterface cycle){
		for (int i = 0; i < cycle.cycleSize(); i++){
			
			//userItemPairs.add(cycle.getUserItemPair(i));
		}
	}

	private void createNewTransaction(Cycle cycle){
		
	}
 	
	public Transaction(QueryExecutor executor, DBqueryGenerator generator, int ID, DateTime dateTime){
		this.ID = ID;
		this.dateTime = dateTime;
		this.executor = executor;
		this.generator = generator;
		getTransactionFromBases();
	}
	
	
	public Transaction(QueryExecutor executor, DBqueryGenerator generator, CycleInterface cycle){
		this.executor = executor;
		this.generator = generator;
		createNewTransaction(cycle);
	}
	
	@Override
	public Pair<User, ItemInterface> getUserItemPair(int num){
		return userItemPairs.get(num);
	}
	
	@Override
	public int transactionSize(){
		return size;
	}

	@Override
	public void addToTheBases() {
		int transactionID = executor.executeQuery(generator.insertIntoTransactions());
		
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
