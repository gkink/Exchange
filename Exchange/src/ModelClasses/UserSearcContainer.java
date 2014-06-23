package ModelClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class UserSearcContainer {
	private List<User> result;
	private List<Integer> utility;
	
	public UserSearcContainer(QueryExecutor ex, DBqueryGenerator gen, String name){
		result =  new ArrayList<User>();
		utility =  new ArrayList<Integer>();
		
		
		String query = gen.getUsersByName(name);
		ResultSet rs = ex.selectResult(query);
		
		try {
			while(rs.next()){
				utility.add(rs.getInt(1));
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ex.closeVariables();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		initResult(ex, gen);
	}
	
	private void initResult(QueryExecutor ex, DBqueryGenerator gen){
		for(int i: utility){
			result.add(new User(ex, gen, i));
		}
	}
	
	public User getUser(int index){
		return result.get(index);
	}
	
	public int size(){
		return result.size();
	}
}
