package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class ItemsNeedObject implements ItemInterface  {
	DBqueryGenerator gen;
	QueryExecutor exe;
	private String keyWords;
	private int userId;
	private String name;
	private int ID;
	public ItemsNeedObject(DBqueryGenerator gen, QueryExecutor exe, int userId,	String Name, String keyWords) {
		this.name=Name;
		this.userId=userId;
		this.keyWords=keyWords;
		this.exe=exe;
		this.gen=gen;
	}
	public ItemsNeedObject(DBqueryGenerator gen, QueryExecutor exe, int ID) {
		this.gen=gen;
		this.exe=exe;
		ResultSet rs= exe.selectResult(gen.getItemSelectQuery("itemsNeed", ID));
		parseAndinit(rs);
	}
	private void parseAndinit(ResultSet rs){	
		try {
			while(rs.next()){
				this.ID = rs.getInt("ID");
				this.name = rs.getString("name");
				this.keyWords = rs.getString("keywords");
				this.userId = rs.getInt("userId");
			}
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    exe.closeStatement();
		    exe.closeConnection();
		}
	}
	@Override
	public void insert() {
		this.ID=exe.executeQuery(gen.getItemsNeedInsertQuery(userId, name, keyWords));		
//		exe.closeStatement();
//		exe.closeConnection();
	}
	public void update(String field, String upd){
		switch (field){
			case "name": this.name=upd;
			break;
			case "keywords": this.keyWords=upd;
			break;
			default :System.out.println("Illegal Field");
			return;
		}
		exe.executeQuery(gen.getItemUpdateQuery("itemsNeed", field, upd, ID));
//		exe.closeStatement();
//		exe.closeConnection();
	}
	public void delete(){
		exe.executeQuery(gen.getItemDeleteQuery("itemsNeed",ID));
//		exe.closeStatement();
//		exe.closeConnection();
	}
	public String getItemKeywords(){
		return keyWords;
	}
	@Override
	public int getItemOwner() {
		// TODO Auto-generated method stub
		return userId;
	}

	@Override
	public int getItemId() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return name;
	}
	

}
