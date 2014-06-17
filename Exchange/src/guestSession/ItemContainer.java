package guestSession;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;














import javax.sql.DataSource;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import dbConnection.MyDBInfo;

/**
  * @author Irakli
 *This class is responsible for selecting items from the database.
 *It returns the selected as an ArrayList of items.
 */
public class ItemContainer {
	
	private DBqueryGenerator generator;
	private QueryExecutor executor;
	private int ID;
	public ItemContainer(DBqueryGenerator generator, QueryExecutor executor){
		this.generator=generator;
		this.executor=executor;
	}
	//returns the 10 latest added items
	public ArrayList<ItemsHaveObject> getLatestItems(){
		ArrayList<ItemsHaveObject> items= new ArrayList<>();
		prepareItemsHaveList(generator.getLatestItems(), items);
		return items;
	}
	//return all the items the user with the specified userId has
	public  ArrayList<ItemsHaveObject> getUserItemsHave(int userId){
		ArrayList<ItemsHaveObject> items= new ArrayList<>();
		prepareItemsHaveList(generator.getUserItems(userId, 0),items);
		return items;
	}
	//return all the items the user with the specified userId wants
	public  ArrayList<ItemsNeedObject> getUserItemsNeed(int userId){
		ArrayList<ItemsNeedObject> items= new ArrayList<>();
		prepareItemsNeedList(generator.getUserItems(userId, 1), items);
		return items;
	}
	//return all the items the user with the specified userId wants and the ones that exist(some other users have them)
	public ArrayList<Item> getUserItemsReal(int userId){
		prepareTheList(generator.getUserItems(userId, 2));
		return items;
	}
	
	//returns all the items where the keywords contain the word given as a parameter
	public ArrayList<ItemsHaveObject> getSearchResultItems(String word){
		ArrayList<ItemsHaveObject> items= new ArrayList<>();
		prepareItemsHaveList(generator.searchItemsKeywords(word),items);
		return items;
	}
	private void prepareItemsNeedList(String query, ArrayList<ItemsNeedObject> items){
		ResultSet rs= executor.selectResult(query);
		ParseAndInitItemsNeed(items, rs);
	}
	
	private void ParseAndInitItemsNeed(ArrayList<ItemsNeedObject> items,ResultSet rs){	
		try {
			while(rs.next()){
				int ID=rs.getInt("ID");
				ItemsNeedObject cur=new ItemsNeedObject(generator, executor,ID);
				items.add(cur);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}
	private void prepareItemsHaveList(String query, ArrayList<ItemsHaveObject> items){
		ResultSet rs= executor.selectResult(query);
		ParseAndInitItemsHave(items, rs);
	}
	
	private void ParseAndInitItemsHave(ArrayList<ItemsHaveObject> items,ResultSet rs){	
		try {
			while(rs.next()){
				int ID=rs.getInt("ID");
				ItemsHaveObject cur=new ItemsHaveObject(generator, executor,ID);
				items.add(cur);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}
/*	private void ParseAndInit(ArrayList<ItemsHaveObject> items,ResultSet rs){	
		try {
			while(rs.next()){
				
				String name = rs.getString("name");
				String description = rs.getString("description");
				String keywords = rs.getString("keywords");
				int userId = rs.getInt("userId");
				Date date= rs.getDate("createDate");
				Time time=rs.getTime("createDate");
				DateTime createDate=new DateTime(date, time);
				ItemsHaveObject cur=new ItemsHaveObject(generator, executor, name, description, keywords,userId,createDate);
				items.add(cur);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}
*/
	public static void main(String[] args) {
		DataSource datasource = mock(DataSource.class);
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
			Connection con = (Connection) DriverManager.getConnection 
					( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD); 
			Statement stmt = (Statement) con.createStatement(); 
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME); 
			when(datasource.getConnection()).thenReturn(con);
		}catch (ClassNotFoundException e) { 
				e.printStackTrace(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		QueryExecutor q = new QueryExecutor(datasource);
		DBqueryGenerator d=new DBqueryGenerator();
		ItemContainer i= new ItemContainer(d, q);
		ArrayList<ItemsHaveObject> a= i.getUserItemsHave(1);
		for(int j=0; j<a.size();j++){
			System.out.println(a.get(j).getItemId());
		}
        		
        		
        
    }
	
	
	
}
