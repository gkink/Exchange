package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class RealItemsObject{
	private int itemID;
	private int userId;
	private int ID;
	private QueryExecutor executor;
	private DBqueryGenerator generator;
	public RealItemsObject(DBqueryGenerator generator, QueryExecutor executor,
			int ID) {
		this.generator=generator;
		this.executor=executor;
		ResultSet rs= executor.selectResult(generator.getItemSelectQuery("realItems", ID));
		parseAndinit(rs);
	}
	private void parseAndinit(ResultSet rs){	
		try {
			while(rs.next()){
				this.ID = rs.getInt("ID");
				this.userId = rs.getInt("userId");
				this.itemID=rs.getInt("itemId");
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}finally{
				try {
					if(rs != null)
						rs.close();
					executor.closeVariables();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	public RealItemsObject(DBqueryGenerator generator, QueryExecutor executor,
			int userId,int itemId) {
		this.userId=userId;
		this.itemID=itemId;
		this.generator=generator;
		this.executor=executor;
	}
	public int getItemOwner(){
		return userId;
	}
	public int getItemId(){
		return itemID;
		
	}
	public int getRowId(){
		return ID;
	}
	public void insert(){
		ID=executor.executeQuery(generator.getRealItemInsertQuery(userId, itemID));
	}
	public void delete(){
		executor.executeQuery(generator.getItemDeleteQuery("realItems", ID));
	}


	
}