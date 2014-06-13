package guestSession;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;








import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class ItemContainer {
	private ArrayList<Item> items;
	
	private DBqueryGenerator generator;
	private QueryExecutor executor;
	public ItemContainer(DBqueryGenerator generator, QueryExecutor executor){
		this.generator=generator;
		this.executor=executor;
	}
	
	public ArrayList<Item> getLatestItems(){
		prepareTheList(generator.getLatestItems());
		
		
		return items;
	}
	
	public  ArrayList<Item> getUserItems(int userId){
		prepareTheList(generator.getUserItems(userId, 0));
		
		return items;
		
	}
	
	public  ArrayList<Item> getUserItemsNeed(int userId){
		prepareTheList(generator.getUserItems(userId, 1));
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
				int ID = rs.getInt("ID");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String keywords = rs.getString("keywords");
				int userId = rs.getInt("userId");
				Date time= rs.getDate("createDate");
				Item cur=new Item(generator, executor,ID, name, description, keywords,userId,time);
				items.add(cur);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}

	public static void main(String[] args) {
        ItemContainer i= new ItemContainer(null, null);
       
        
        		
        		
        
    }
	
	
	
}
