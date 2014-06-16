package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
		String query=queryGenerator.getItemsHaveSelectQuery(ID);
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
				String time= rs.getString("createDate");
				this.createDate=new DateTime(time.replace('-', '/'));
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}
	@Override
	public void insert() {
		executor.executeQuery(queryGenerator.getItemInsertQuery(0, userId, name, description, keywords,createDate));
		
	}
	public void update(int ID, String field, String upd){
		executor.executeQuery(queryGenerator.getItemUpdateQuery(0, field, upd, ID));
		
	}
	public void delete(){
		executor.executeQuery(queryGenerator.getItemsHaveDeleteQuery(ID));
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

}