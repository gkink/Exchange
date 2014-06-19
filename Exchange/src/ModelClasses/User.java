package ModelClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class User {
	private DBqueryGenerator queryGenerator;
	private QueryExecutor executor;
	private int id, rating;
	private String firstName, lastName, email, password;

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
			String lastName, String email, String password){
		initVars(executor, queryGenerator, rating, firstName, lastName, email, 0,password);
	}

	private void initVars(QueryExecutor executor, DBqueryGenerator queryGenerator, int rating, String firstName, 
			String lastName, String email, int id,String password){
		this.executor = executor;
		this.queryGenerator = queryGenerator;
		this.rating = rating;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.id = id;
		this.password=password;
	}

	/*
	 * another constructor: executes select query, parses through the resultset and calls basic constructor
	 * using the valid information from database
	 */
	public User(QueryExecutor executor, DBqueryGenerator queryGenerator, int id){
		String selectQuery = queryGenerator.getUserQuery(id);
		ResultSet rs = executor.selectResult(selectQuery);
		int rating = 0;
		String firstName = null, lastName = null, email = null, password=null;		
		
		try {
			while(rs.next()){
				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				email = rs.getString("email");
				rating = rs.getInt("ranking");
				password=rs.getString("password");
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
		
		initVars(executor, queryGenerator, rating, firstName, lastName, email, id,password);
	}
	public User(QueryExecutor ex, DBqueryGenerator g, String email){
		ResultSet rs = ex.selectResult(g.getUserByEmail(email));
		int rating = 0;
		String firstName = null, lastName = null,password=null;
		int id=0;
		try {
			while(rs.next()){
				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				id = rs.getInt("ID");
				rating = rs.getInt("ranking");
				password=rs.getString("password");
				
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
		initVars(executor, queryGenerator, rating, firstName, lastName, email, id,password);
	}

	/*
	 * inserts a new user into database and returns its id.
	 */
	public int addToUsers(){
		String insertQuery = queryGenerator.insertIntoUsers(rating, firstName, lastName, email,password);
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
	public String getPassword(){
		return password;
	}
	public boolean emailInUse(){
		ResultSet rs = executor.selectResult(queryGenerator.getUserByEmail(email));
		int id = 0;
		try {
			while(rs.next())
				id = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id != 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof User))
			return false;
		
		User tocompare = (User) obj;
		
		return 	this.id == tocompare.id
				&& this.rating == tocompare.rating
				&& this.firstName.equals(tocompare.firstName)
				&& this.lastName.equals(tocompare.lastName)
				&& this.email.equals(tocompare.email);
	}
}
