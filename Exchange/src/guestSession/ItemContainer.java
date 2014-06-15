package guestSession;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;









import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

/**
  * @author Irakli
 *This class is responsible for selecting items from the database.
 *It returns the selected as an ArrayList of items.
 */
public class ItemContainer {
	private ArrayList<Item> items;
	private DBqueryGenerator generator;
	private QueryExecutor executor;
	public ItemContainer(DBqueryGenerator generator, QueryExecutor executor){
		this.generator=generator;
		this.executor=executor;
	}
	//returns the 10 latest added items
	public ArrayList<Item> getLatestItems(){
		prepareTheList(generator.getLatestItems());
		return items;
	}
	//return all the items the user with the specified userId has
	public  ArrayList<Item> getUserItemsHave(int userId){
		prepareTheList(generator.getUserItems(userId, 0));
		return items;
	}
	//return all the items the user with the specified userId wants
	public  ArrayList<Item> getUserItemsNeed(int userId){
		prepareTheList(generator.getUserItems(userId, 1));
		return items;
	}
	//return all the items the user with the specified userId wants and the ones that exist(some other users have them)
	public ArrayList<Item> getUserItemsReal(int userId){
		prepareTheList(generator.getUserItems(userId, 2));
		return items;
	}
	//returns all the items where the keywords contain the word given as a parameter
	public ArrayList<Item> getSearchResultItems(String word){
		prepareTheList(generator.searchItemsKeywords(word));
		return items;
	}
	private void prepareTheList(String query){
		items=new ArrayList<>();
		ResultSet rs= executor.selectResult(query);
		ParseAndInit(items, rs);
	}
	private void ParseAndInit(ArrayList<Item> items,ResultSet rs){	
		try {
			while(rs.next()){
				
				String name = rs.getString("name");
				String description = rs.getString("description");
				String keywords = rs.getString("keywords");
				int userId = rs.getInt("userId");
				Date time= rs.getDate("createDate");
				DateTime par=new DateTime(time.toString());
				Item cur=new Item(generator, executor, name, description, keywords,userId,par);
				items.add(cur);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}

	public static void main(String[] args) {
      
        		
        		
        
    }
	
	
	
}
