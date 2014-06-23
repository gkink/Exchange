package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.Time;
import java.sql.Date;



import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class ItemsHaveObject implements ItemInterface {
	private int ID;
	private String name;
	private String description;
	private String keywords;
	private int userId;
	private DateTime createDate;
	DBqueryGenerator queryGenerator;
	QueryExecutor executor;
	public ItemsHaveObject(DBqueryGenerator generator,QueryExecutor executor, String name, String description, String keywords, int userId, DateTime date){
		this.name=name;
		this.description=description;
		this.keywords=keywords;
		this.userId=userId;
		this.queryGenerator=generator;
		this.executor=executor;
		createDate=date;		
	}
	
	public ItemsHaveObject(DBqueryGenerator generator,QueryExecutor executor,int ID){
		this.executor = executor;
		this.queryGenerator = generator;
		String query=queryGenerator.getItemSelectQuery("itemsHave",ID);
		ResultSet rs= executor.selectResult(query);
		parseAndinit(rs);
	
		
	}
	

	private void parseAndinit(ResultSet rs){	
		try {
			while(rs.next()){
				this.ID = rs.getInt("ID");
				this.name = rs.getString("name");
				this.description = rs.getString("description");
				this.keywords = rs.getString("keywords");
				this.userId = rs.getInt("userId");
				Date date= rs.getDate("createDate");
				Time time=rs.getTime("createDate");
				this.createDate=new DateTime(date, time);
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
		executor.closeConnection();
		executor.closeStatement();
	}
	@Override
	public void insert() {
		this.ID= executor.executeQuery(queryGenerator.getItemInsertQuery(0, userId, name, description, keywords,createDate));
		executor.closeConnection();
		executor.closeStatement();
	}
	public void update(String field, String upd){
		switch (field){
		case "description" :this.description=upd;
		break;
		case "name": this.name=upd;
		break;
		case "keywords": this.keywords=upd;
		break;
		default :System.out.println("Illegal Field");
		return;
		}
		executor.executeQuery(queryGenerator.getItemUpdateQuery("itemsHave", field, upd, ID));
		executor.closeConnection();
		executor.closeStatement();
				
		
		
		
	}
	public void delete(){
		executor.executeQuery(queryGenerator.getItemDeleteQuery("itemsHave",ID));
		executor.closeConnection();
		executor.closeStatement();
	}
	public DateTime getCreateDate(){
		return createDate;
	}
	public String getItemName(){
		return name;
	}
	public String getItemDescription(){
		return description;
	}
	public String getItemKeywords(){
		return keywords;
	}
	@Override
	public int getItemOwner() {
		// TODO Auto-generated method stub
		return  userId;
	}
	
	
	@Override
	public int getItemId() {
		// TODO Auto-generated method stub
		return ID;
	}
	@Override
	public boolean equals(Object obj){
		if (obj == this) return true;
		
		if (!(obj instanceof ItemsChanged)) return false;
		
		ItemsHaveObject item = (ItemsHaveObject) obj;
		
		return (item.getItemId()==ID);
	}
}
