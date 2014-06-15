package ModelClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import guestSession.DateTime;
import guestSession.Item;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class Cycle implements CycleInterface{
	private DBqueryGenerator queryGenerator;
	private QueryExecutor executor;
	private int id;
	private DateTime datetime;
	private List<Pair<User, Item> >  list;

	public Cycle(QueryExecutor executor, DBqueryGenerator generator, int id, DateTime dateTime){
		this.executor =  executor;
		this.queryGenerator = generator;
		this.id = id;
		this.datetime = datetime;
		
		initList();
	}

	private void initList(){
		list = new ArrayList<Pair<User, Item> >();
		
		ResultSet rs = executor.selectResult(queryGenerator.getCycleSelect(id));
		int userid, itemid;
		try {
			while(rs.next()){
				userid = rs.getInt(1);
				itemid = rs.getInt(2);
				User user = new User(executor, queryGenerator, userid);
				Item item = new Item(queryGenerator, executor, itemid);
				
				Pair<User, Item> curr = new Pair<User, Item>(user, item);
				list.add(curr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param executor
	 * @param generator
	 * @param list
	 * This constructor is given list which creates a new cycle, then addToBases method should be called
	 * in order to add new cycle into cycles table, and add all nesessary rows in cycleinfo
	 */
	public Cycle(QueryExecutor executor, DBqueryGenerator generator, List<Pair<User, Item> >  list){
		this.executor = executor;
		this.queryGenerator = generator;
		this.list = list;
	}

	//getters
	@Override
	public int cycleSize() {
		return list.size();
	}

	@Override
	public Pair<User, Item> getUserItemPair(int num) {
		return list.get(num);
	}

	/**
	 * This method is probably called after dfs finds a cycle, then, after the list object is initialized
	 * according to the cycle content, that dfs was able to find, the basic constructor is expected to be
	 * called, and this method should be called by that cycle object created with basic constructor. it will
	 * add new row to cycles table, then iterate the list object and add appropriate rows into cycleInfo table
	 */
	@Override
	public void addToTheBases() {
		this.id = executor.executeQuery(queryGenerator.insertIntoCycles());
		
		for(int i = 0 ; i < cycleSize() ; i++){
			String insert = queryGenerator.cycleInfoInsert(id, list.get(i).getSecond().getID());
			executor.executeQuery(insert);
		}
	}

	@Override
	public DateTime getDateTime() {
		return datetime;
	}

	@Override
	public int getID() {
		return id;
	}
	
}
