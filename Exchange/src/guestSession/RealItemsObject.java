package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class RealItemsObject{
	int UserWantId;
	int itemID;
	int userId;
	int ID;
	QueryExecutor e;
	DBqueryGenerator g;
	public RealItemsObject(DBqueryGenerator generator, QueryExecutor executor,
			int ID) {
		this.g=generator;
		this.e=executor;
		ResultSet rs= e.selectResult(g.getItemSelectQuery("realItems", ID));
		parseAndinit(rs);
	}
	private void parseAndinit(ResultSet rs){	
		try {
			while(rs.next()){
				this.ID = rs.getInt("ID");
				this.userId = rs.getInt("userId");
				this.itemID=rs.getInt("itemId");
			}
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    e.closeStatement();
		    e.closeConnection();
		}
	}
	public RealItemsObject(DBqueryGenerator generator, QueryExecutor executor,
			int userId,int itemId) {
		this.userId=userId;
		this.itemID=itemId;
		this.g=generator;
		this.e=executor;
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
		ID=e.executeQuery(g.getRealItemInsertQuery(userId, itemID));
//		e.closeStatement();
//		e.closeConnection();
	}
	public void delete(){
		e.executeQuery(g.getItemDeleteQuery("realItems", ID));
//		e.closeStatement();
//		e.closeConnection();
	}


	
}
