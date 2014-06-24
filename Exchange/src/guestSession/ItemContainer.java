package guestSession;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	ArrayList<ItemsHaveObject> itemsHave;
	ArrayList<ItemsNeedObject> itemsNeed;
	ArrayList<RealItemsObject> realItems;
	private DBqueryGenerator generator;
	private QueryExecutor executor;
	
	public ItemContainer(DBqueryGenerator generator, QueryExecutor executor){
		this.generator=generator;
		this.executor=executor;
	}
	//returns the 10 latest added items
	public ArrayList<ItemsHaveObject> getLatestItems(int userId){
		
		prepareList(generator.getLatestItems(userId), 0);
		return itemsHave;
	}
	//return all the items the user with the specified userId has
	public  ArrayList<ItemsHaveObject> getUserItemsHave(int userId){
		
		prepareList(generator.getUserItems(userId, 0),0);
		return itemsHave;
	}
	//return all the items the user with the specified userId wants
	public  ArrayList<ItemsNeedObject> getUserItemsNeed(int userId){
		
		prepareList(generator.getUserItems(userId, 1), 1);
		return itemsNeed;
	}
	//return all the items the user with the specified userId wants and the ones that exist(some other users have them)
	public ArrayList<RealItemsObject> getUserItemsReal(int userId){
		prepareList(generator.getUserItems(userId, 2),2);
		return realItems;
	}
	
	//returns all the items where the keywords contain the word given as a parameter
	public ArrayList<ItemsHaveObject> getSearchResultItems(String word){
		
		prepareList(generator.searchItemsKeywords(word),0);
		return itemsHave;
	}
	
	private void prepareList(String query, int type){
		if(type==0) itemsHave= new ArrayList<>();
		if(type ==1)itemsNeed= new ArrayList<>();
		if(type==2)realItems=new ArrayList<>();
		ResultSet rs= executor.selectResult(query);
		ParseAndInitItems(type, rs);
		executor.closeVariables();
	}
	
	private void ParseAndInitItems(int type,ResultSet rs){	
		try {
			while(rs.next()){
				int ID=rs.getInt("ID");
				if(type==0){
				ItemsHaveObject cur=new ItemsHaveObject(generator, executor,ID);
				itemsHave.add(cur);
				}if (type==1){
					ItemsNeedObject cur=new ItemsNeedObject(generator, executor,ID);
					itemsNeed.add(cur);
				}if (type==2){
					RealItemsObject cur=new RealItemsObject(generator, executor,ID);
					realItems.add(cur);
				}
				
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}

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
		System.out.println(d.getLatestItems(1));
		ItemContainer i= new ItemContainer(d, q);
		ArrayList<ItemsHaveObject> a= i.getSearchResultItems("net");
		ArrayList<ItemsHaveObject> b= i.getLatestItems(1);
		ArrayList<RealItemsObject> c= i.getUserItemsReal(1);
		ArrayList<ItemsNeedObject> e= i.getUserItemsNeed(1);
		
		
		for(int j=0; j<a.size();j++){
		//	System.out.println(a.get(j).getItemKeywords());
			
		//	System.out.println(b.get(j).getItemName());
			
			
			
			
		}
		
		for(int j=0; j<c.size();j++){
			System.out.println(c.get(j).getItemId());
			System.out.println(c.get(j).getRowId());
		}
		for(int j=0; j<e.size();j++){
			System.out.println(e.get(j).getItemOwner());
		}	
        		
        
    }
	
	
	
}
