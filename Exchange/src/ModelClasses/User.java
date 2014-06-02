package ModelClasses;

import dbClasses.QueryExecutor;

public class User {
	private QueryExecutor executor;
	private int id, rating;
	private String firstName, lastName, email;
	
	public User(QueryExecutor executor, int id, int rating, String firstName, 
			String lastName, String lastName, String email){
		this.executor = executor;
		
	}
	
}
