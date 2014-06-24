package guestSession;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	private ArrayList<ItemsHaveObject> itemsHave;
	private ArrayList<ItemsNeedObject> itemsNeed;
	private ArrayList<RealItemsObject> realItems;
	private List<Integer> IDs;
	private DBqueryGenerator generator;
	private QueryExecutor executor;
	
	public ItemContainer(DBqueryGenerator generator, QueryExecutor executor){
		itemsHave = new ArrayList<ItemsHaveObject>();
		itemsNeed = new ArrayList<ItemsNeedObject>();
		realItems = new ArrayList<RealItemsObject>();
		this.generator=generator;
		this.executor=executor;
		IDs = new ArrayList<Integer>();
	}
	//returns the 10 latest added items
	public ArrayList<ItemsHaveObject> getLatestItems(int userId){
		prepareList(generator.getLatestItems(userId));
		createItems(1);
		return itemsHave;
	}
	//return all the items the user with the specified userId has
	public  ArrayList<ItemsHaveObject> getUserItemsHave(int userId){
		
		prepareList(generator.getUserItems(userId, 0));
		createItems(1);
		return itemsHave;
	}
	//return all the items the user with the specified userId wants
	public  ArrayList<ItemsNeedObject> getUserItemsNeed(int userId){
		
		prepareList(generator.getUserItems(userId, 1));
		createItems(2);
		return itemsNeed;
	}
	//return all the items the user with the specified userId wants and the ones that exist(some other users have them)
	public ArrayList<RealItemsObject> getUserItemsReal(int userId){
		
		prepareList(generator.getUserItems(userId, 2));
		createItems(3);
		return realItems;
	}
	
	//returns all the items where the keywords contain the word given as a parameter
	public ArrayList<ItemsHaveObject> getSearchResultItems(String word){
		prepareList(generator.searchItemsKeywords(word));
		createItems(1);
		return itemsHave;
	}
	
	private void prepareList(String query){
		ResultSet rs= executor.selectResult(query);
		parseResultSet(rs);
	}
	
	private void parseResultSet(ResultSet rs){	
		try {
			while(rs.next()){
				IDs.add(rs.getInt("ID"));
			}
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");			
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    executor.closeVariables();
		}	
	}
	
	private void createItems(int type){
		for (Integer ID: IDs){
			if (type == 1){
				itemsHave.add(new ItemsHaveObject(generator, executor, ID));
			}else if (type == 2) {
				itemsNeed.add(new ItemsNeedObject(generator, executor, ID));
			}else {
				realItems.add(new RealItemsObject(generator, executor, ID));
			}
		}
	}


//	public static void main(String[] args) {
//		DataSource datasource = mock(DataSource.class);
//		try { 
//			Class.forName("com.mysql.jdbc.Driver"); 
//			Connection con = (Connection) DriverManager.getConnection 
//					( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD); 
//			Statement stmt = (Statement) con.createStatement(); 
//			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME); 
//			when(datasource.getConnection()).thenReturn(con);
//		}catch (ClassNotFoundException e) { 
//				e.printStackTrace(); 
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		QueryExecutor q = new QueryExecutor(datasource);
//		DBqueryGenerator d=new DBqueryGenerator();
//		System.out.println(d.getLatestItems(1));
//		ItemContainer i= new ItemContainer(d, q);
//		ArrayList<ItemsHaveObject> a= i.getSearchResultItems("net");
//		ArrayList<ItemsHaveObject> b= i.getLatestItems(1);
//		ArrayList<RealItemsObject> c= i.getUserItemsReal(1);
//		ArrayList<ItemsNeedObject> e= i.getUserItemsNeed(1);
//		
//		
//		for(int j=0; j<a.size();j++){
//		//	System.out.println(a.get(j).getItemKeywords());
//			
//		//	System.out.println(b.get(j).getItemName());
//			
//			
//			
//			
//		}
//		
//		for(int j=0; j<c.size();j++){
//			System.out.println(c.get(j).getItemId());
//			System.out.println(c.get(j).getRowId());
//		}
//		for(int j=0; j<e.size();j++){
//			System.out.println(e.get(j).getItemOwner());
//		}	
//        		
//        
//    }
	
	
}
