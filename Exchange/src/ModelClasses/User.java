package ModelClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class User {
	private DBqueryGenerator queryGenerator;
	private QueryExecutor executor;
	private int id, rating;
	private String firstName, lastName, email;
	
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
	public User(QueryExecutor executor, DBqueryGenerator queryGenerator, int id, int rating, String firstName, 
			String lastName, String email){
		this.executor = executor;
		this.queryGenerator = queryGenerator;
		this.id = id;
		this.rating = rating;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
		
	/*
	 * another constructor: executes select query, parses through the resultset and calls basic constructor
	 * using the valid information from database
	 */
	public User(QueryExecutor executor, DBqueryGenerator queryGenerator, int id){
		this.executor = executor;
		this.queryGenerator = queryGenerator;
		
		String selectQuery = queryGenerator.getUserQuery(id);
		ResultSet rs = executor.selectResult(selectQuery);
		
		parseAndinit(rs);
	}
	
	/*
	 * parses through the resultset and initializes all private variables
	 */
	private void parseAndinit(ResultSet rs){	
		try {
			while(rs.next()){
				this.id = rs.getInt("ID");
				this.firstName = rs.getString("firstName");
				this.lastName = rs.getString("lastName");
				this.email = rs.getString("email");
				this.rating = rs.getInt("ranking");
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}

	/*
	 * inserts a new user into database.
	 */
	public void addToUsers(){
		String insertQuery = queryGenerator.insertIntoUsers(id, rating, firstName, lastName, email);
		executor.executeQuery(insertQuery);
	}
	
	/*
	 * User info from user table only
	 * Note: When calling this method, the user is expected to have deleted all the other info related to
	 * the User being deleted, from database.
	 */
	public void deleteUser(){
		String deleteQuery = queryGenerator.deleteFromUsers(id);
		executor.executeQuery(deleteQuery);
	}
	
	//basic getter methods
	
	public int getId(){
		return id;
	}
	
	public int getRating(){
		return rating;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getEmail(){
		return email;
	}
}
