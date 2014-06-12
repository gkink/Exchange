package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;

import ModelClasses.Cycle;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class Transaction implements TransactionInterface{
	
	private int ID;
	private DateTime dateTime;
	private DBqueryGenerator queryGenerator;
	private Map<String, String> transaction;
	private DataSource datasource;
	
	private void getTransactionFromBases(){
		queryGenerator = new DBqueryGenerator();
		String query = queryGenerator.getTransactionQuery(ID);
		QueryExecutor executor = new QueryExecutor(datasource);
		ResultSet res = executor.selectResult(query);
		try {
			while(res.next()){
				transaction.put(res.getString(1) + " " + res.getString(2), res.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { if(null!=res)res.close();} catch (SQLException e)
			{e.printStackTrace();}
		}
	}
	
	public void createNewTransaction(){
		//TODO
	}
 	
	public Transaction(int ID, DateTime dateTime, DataSource datasource){
		this.ID = ID;
		this.dateTime = dateTime;
		this.datasource = datasource;
		getTransactionFromBases();
	}
	
	public Transaction(Cycle cycle, DataSource datasource){
		this.datasource = datasource;
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
