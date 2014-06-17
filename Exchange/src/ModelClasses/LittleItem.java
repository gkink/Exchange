package ModelClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class LittleItem implements MyItemInterface{
	
	private String name;
	private int ID, userID;
	private QueryExecutor executor;
	private DBqueryGenerator generator;
	
	private void getItemFromBases(){
		ResultSet res = executor.selectResult(generator.getItemQuery(ID));
		try {
			res.next();
			name = res.getString("neme");
			userID = res.getInt("userID");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { if(null!=res)res.close();} catch (SQLException e)
			{e.printStackTrace();}
		}
	}
	
	public LittleItem(QueryExecutor executor, DBqueryGenerator generator, int ID){
		this.ID = ID;
		this.executor = executor;
		this.generator = generator;
		
	}

	@Override
	public String getItemName() {
		return name;
	}

	@Override
	public int getItemID() {
		return ID;
	}

	@Override
	public int getUserID() {
		return userID;
	}
}
