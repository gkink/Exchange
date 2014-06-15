package ModelClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;


public class CycleContainer {
	private List<Cycle> cycles;
	private QueryExecutor executor;
	private DBqueryGenerator generator;
	
	public CycleContainer(QueryExecutor executor, DBqueryGenerator generator, int userID){
		this.executor = executor;
		this.generator =  generator;
		
		initList(userID);
	}
	
	private void initList(int userID){
		String getItems = generator.getItemByUser(userID);
		ResultSet rs = executor.selectResult(getItems);
		
		try {
			while(rs.next()){
				int itemID = rs.getInt(1);
				addItemCycles(itemID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addItemCycles(int itemID){
		String getCycles = generator.getCycleByItem(itemID);
		ResultSet rs = executor.selectResult(getCycles);
		
		try {
			while(rs.next()){
				int cycleID = rs.getInt(1);
				cycles.add(new Cycle(executor, generator, cycleID, dateTime));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
