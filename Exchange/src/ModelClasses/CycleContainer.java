package ModelClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;


public class CycleContainer {
	private List<Cycle> cycles;
	private QueryExecutor executor;
	private DBqueryGenerator generator;
	private List<Integer> idList;
	
	/***
	 * @param executor - QueryExecutor object
	 * @param generator - DBqueryGenerator object
	 * @param userID
	 * Constructs the list of cycle objects, using userID parameter, initializes the list with
	 * the user's cycles
	 */
	public CycleContainer(QueryExecutor executor, DBqueryGenerator generator, int userID){
		this.executor = executor;
		this.generator =  generator;
		idList = new ArrayList<Integer>();
		
		initList(userID);
	}
	
	/***
	 * @param executor - QueryExecutor object
	 * @param generator - DBqueryGenerator object
	 * @param itemID
	 * Constructs the list of cycle objects, using itemID parameter, initializes the list with
	 * item's cycles
	 */
	public CycleContainer(DBqueryGenerator generator, QueryExecutor executor, int itemID){
		this.executor = executor;
		this.generator =  generator;

		addItemCycles(itemID);
	}
	
	private void initList(int userID){
		String getItems = generator.getItemByUser(userID);
		ResultSet rs = executor.selectResult(getItems);
		
		try {
			while(rs.next()){
				int itemID = rs.getInt(1);
				idList.add(itemID);
			}
			rs.close();
			executor.closeStatement();
			executor.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i = 0 ; i < idList.size() ; i++)
			addItemCycles(idList.get(i));
	}
	
	private void addItemCycles(int itemID){
		String getCycles = generator.getCycleByItem(itemID);
		ResultSet rs = executor.selectResult(getCycles);
		
		try {
			while(rs.next()){
				int cycleID = rs.getInt(1);
				cycles.add(new Cycle(executor, generator, cycleID));
			}
			rs.close();
			executor.closeStatement();
			executor.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Cycle getCycle(int num){
		return cycles.get(num);
	}
	
	public int contSize(){
		return cycles.size();
	}
}
