package guestSession;


import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;




public class Item {
	
	private int ID;
	private String name;
	private String description;
	private String keywords;
	private int userId;
	private Date createDate;
	DBqueryGenerator queryGenerator;
	QueryExecutor executor;
	/**
	 * Basic constructor -  is given all the necessary arguments
	 * @param executor - QueryExecutor object
	 * @param queryGenerator - DBqueryGenerator object
	 * @param id - user id in database
	 * @param rating - user rating in database
	 * @param firstName - user firstName from database
	 * @param lastName - user lastName from database
	 * @param email - user email frome database
	 * Note: when the basic constructor is called, it is expected to be given valid information from
	 * database, and not a single parameter should be ignored.
	 */
	public Item(DBqueryGenerator generator,QueryExecutor executor,int ID, String name, String description, String keywords, int userId, Date date){
		this.ID=ID;
		this.name=name;
		this.description=description;
		this.keywords=keywords;
		this.userId=userId;
		this.queryGenerator=generator;
		this.executor=executor;
		createDate=date;
		
		
	}
	public Item(DBqueryGenerator generator,QueryExecutor executor,int ID){
		this.executor = executor;
		this.queryGenerator = generator;
		String query=queryGenerator.getItemSelectQuery(ID);
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
				Date time= rs.getDate("createDate");
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}
	/**
	 * @author Irakli
	 * This methods return information about the item that might be needed
	 */
	public String getItemName(){
		return name;
	}
	public int getItemOwner(){
		return userId;
	}
	public String getItemDescription(){
		return description;
	}
	public Date getItemCreateDate(){
		return createDate;
	}
	
	
	/**
	 * @author Irakli
	 * This methods are responsible for item-database connection, they either
	 * insert, delete, or update information about an item. The type parameter is needed
	 * to find out which table is being reffered to itemsHave or itemsNeed (the type is either 1 or 0)
	 */
	public void insert(int type){
		
		executor.executeQuery(queryGenerator.getItemInsertQuery(type, userId, name, description, keywords,createDate));
		
	}
	public void update(int type, String field, String text){
		executor.executeQuery(queryGenerator.getItemUpdateQuery(type, field, text, ID));
		
	}
	public void delete(int type){
		executor.executeQuery(queryGenerator.getItemDeleteQuery(type, ID));
	}


}
