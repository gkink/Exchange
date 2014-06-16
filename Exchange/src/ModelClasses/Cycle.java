package ModelClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import guestSession.DateTime;
import guestSession.Item;
import guestSession.ItemInterface;
import guestSession.ItemsHaveObject;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class Cycle implements CycleInterface{
	private DBqueryGenerator queryGenerator;
	private QueryExecutor executor;
	private int id;
	private List<Pair<User, ItemInterface> >  list;

	public Cycle(QueryExecutor executor, DBqueryGenerator generator, int id){
		this.executor =  executor;
		this.queryGenerator = generator;
		this.id = id;
		
		initList();
	}

	private void initList(){
		list = new ArrayList<Pair<User, ItemInterface> >();
		
		ResultSet rs = executor.selectResult(queryGenerator.getCycleSelect(id));
		int userid, itemid;
		try {
			while(rs.next()){
				userid = rs.getInt(1);
				itemid = rs.getInt(2);
				User user = new User(executor, queryGenerator, userid);
				ItemInterface item = new ItemsHaveObject(queryGenerator, executor, itemid);
				
				Pair<User, ItemInterface> curr = new Pair<User, ItemInterface>(user, item);
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
	public Cycle(QueryExecutor executor, DBqueryGenerator generator, List<Pair<User, ItemInterface> >  list){
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
	public Pair<User, ItemInterface> getUserItemPair(int num) {
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
			String insert = queryGenerator.cycleInfoInsert(id, list.get(i).getSecond().getItemId());
			executor.executeQuery(insert);
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
}
