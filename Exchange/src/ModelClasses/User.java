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
	public User(QueryExecutor executor, DBqueryGenerator queryGenerator, int rating, String firstName, 
			String lastName, String email){
		initVars(executor, queryGenerator, rating, firstName, lastName, email, 0);
	}

	private void initVars(QueryExecutor executor, DBqueryGenerator queryGenerator, int rating, String firstName, 
			String lastName, String email, int id){
		this.executor = executor;
		this.queryGenerator = queryGenerator;
		this.rating = rating;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.id = id;
	}

	/*
	 * another constructor: executes select query, parses through the resultset and calls basic constructor
	 * using the valid information from database
	 */
	public User(QueryExecutor executor, DBqueryGenerator queryGenerator, int id){
		String selectQuery = queryGenerator.getUserQuery(id);
		ResultSet rs = executor.selectResult(selectQuery);
		int rating = 0;
		String firstName = null, lastName = null, email = null;		
		
		try {
			while(rs.next()){
				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				email = rs.getString("email");
				rating = rs.getInt("ranking");
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
		
		initVars(executor, queryGenerator, rating, firstName, lastName, email, id);
	}

	/*
	 * inserts a new user into database and returns its id.
	 */
	public int addToUsers(){
		String insertQuery = queryGenerator.insertIntoUsers(rating, firstName, lastName, email);
		this.id = executor.executeQuery(insertQuery);
		
		return this.id;
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
	
	@Override
	public boolean equals(Object obj) {
		if(!obj.getClass().equals(this.getClass()))
			return false;
		
		User tocompare = (User) obj;
		
		return 	this.id == tocompare.id
				&& this.rating == tocompare.rating
				&& this.firstName == tocompare.firstName
				&& this.lastName == tocompare.lastName
				&& this.email == tocompare.email;
	}
}
