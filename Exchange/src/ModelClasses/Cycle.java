package ModelClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import guestSession.ItemInterface;
import guestSession.ItemsHaveObject;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class Cycle implements CycleInterface{
	private DBqueryGenerator queryGenerator;
	private QueryExecutor executor;
	private int id;
	private List<Pair<User, ItemsHaveObject> >  list;
	private List<Integer> userIdCont;
	private List<Integer> itemIdCont;
	private static final int HashKey = 1001;

	public Cycle(QueryExecutor executor, DBqueryGenerator generator, int id){
		this.executor =  executor;
		this.queryGenerator = generator;
		this.id = id;
		userIdCont =  new ArrayList<Integer>();
		itemIdCont =  new ArrayList<Integer>();

		initList();
	}

	private void initList(){
		list = new ArrayList<Pair<User, ItemsHaveObject> >();

		ResultSet rs = executor.selectResult(queryGenerator.getCycleSelect(id));
		int userid, itemid;
		try {
			while(rs.next()){
				userid = rs.getInt("userId");
				itemid = rs.getInt("itemId");
				userIdCont.add(userid);
				itemIdCont.add(itemid);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				executor.closeVariables();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		addElemsToList();
	}

	private void addElemsToList(){
		for(int i = 0 ; i < userIdCont.size() ; i++){
			int userid = userIdCont.get(i);
			int itemid =  itemIdCont.get(i);


			User user = new User(executor, queryGenerator, userid);
			ItemsHaveObject item = new ItemsHaveObject(queryGenerator, executor, itemid);

			Pair<User, ItemsHaveObject> curr = new Pair<User, ItemsHaveObject>(user, item);
			list.add(curr);
		}
	}

	/**
	 * @param executor
	 * @param generator
	 * @param list
	 * This constructor is given list which creates a new cycle, then addToBases method should be called
	 * in order to add new cycle into cycles table, and add all nesessary rows in cycleinfo
	 */
	public Cycle(QueryExecutor executor, DBqueryGenerator generator, List<Pair<User, ItemsHaveObject> >  list){
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
	public Pair<User, ItemsHaveObject> getUserItemPair(int num) {
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
			String insert = queryGenerator.cycleInfoInsert(id, list.get(i).getSecond().getItemId(), 0);
			executor.executeQuery(insert);
		}
	}

	@Override
	public int getID() {
		return id;
	}

	public void delete(){
		executor.executeQuery(queryGenerator.deleteCycleInfo(id));
		executor.executeQuery(queryGenerator.deleteCycle(id));
	}

	public void accept(int itemID){
		executor.executeQuery(queryGenerator.insertAcceptCycle(itemID));
	}

	public boolean userAccepted(int userId){
		String query =  queryGenerator.getCycleAccept(userId);
		ResultSet rs =  executor.selectResult(query);

		int accept = 0;

		try {
			while(rs.next())
				accept =  rs.getInt("accept");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				executor.closeVariables();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return accept == 1;
	}

	public int generateHash(){
		Collections.sort(userIdCont);
		int curr = userIdCont.get(0);
		for(int i = 1 ; i < userIdCont.size() ; i++){
			curr = cantorFunction(curr, userIdCont.get(i));
		}

		return curr;
	}

	private int cantorFunction(int first, int second){
		int firstElem, secondElem;
		firstElem = first + second;
		secondElem = firstElem + 1;

		return firstElem*secondElem%HashKey + second%HashKey;
	}
}

