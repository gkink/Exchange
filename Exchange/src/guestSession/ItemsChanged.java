package guestSession;

import java.sql.ResultSet;
import java.sql.SQLException;




import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class ItemsChanged implements ItemInterface {

	private int userId;
	private int ID;
	private String name;
	private DBqueryGenerator gen;
	private QueryExecutor exe;
	public ItemsChanged(DBqueryGenerator gen,QueryExecutor exe,int userId, String Name){
		this.gen=gen;
		this.exe=exe;
		this.userId=userId;
		this.name=Name;
	}
	public ItemsChanged(DBqueryGenerator gen,QueryExecutor exe,int ID){
		this.gen=gen;
		this.exe=exe;
		this.ID=ID;
		String query=gen.getItemSelectQuery("itemsChanged",ID);
		ResultSet rs= exe.selectResult(query);
		parseAndinit(rs);
	}
	private void parseAndinit(ResultSet rs) {
		try {
			while(rs.next()){
				this.ID = rs.getInt("ID");
				this.name = rs.getString("name");
				this.userId = rs.getInt("userId");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public String getItemName(){
		return name;
	}
	@Override
	public void insert() {
		this.ID=exe.executeQuery(gen.getItemsChangedInsertQuery(userId, name));
	}
	
	@Override
	public int getItemOwner() {
		
		return userId;
	}
	@Override
	public boolean equals(Object obj){
		if (obj == this) return true;
		
		if (!(obj instanceof ItemsChanged)) return false;
		
		ItemsChanged item = (ItemsChanged) obj;
		
		return (item.getItemId()==ID);
	}
	@Override
	public int getItemId() {
		
		return ID;
	}
	
}
