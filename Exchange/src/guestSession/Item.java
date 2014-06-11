package guestSession;


import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import dbConnection.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Item {
	private int ID;
	private String name;
	private String description;
	private String keywords;
	private int userId;
	ConnectionPool pool;
	DBqueryGenerator queryGenerator;
	QueryExecutor executor;
	public Item(DBqueryGenerator generator,QueryExecutor executor,int ID, String name, String description, String keywords, int userId){
		this.ID=ID;
		this.name=name;
		this.description=description;
		this.keywords=keywords;
		this.userId=userId;
		ConnectionPool p;
		this.queryGenerator=generator;
		this.executor=executor;
		try {
			pool= ConnectionPool.getInstance();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Item(DBqueryGenerator generator,QueryExecutor executor,int ID){
		this.executor = executor;
		this.queryGenerator = queryGenerator;
		String query=queryGenerator.getItemSelectQuery(ID);
		ResultSet rs= executor.selectResult(query);
		parseAndinit(rs);
		
	}
	private void parseAndinit(ResultSet rs){	
		try {
			while(rs.next()){
				this.ID = rs.getInt("ID");
				this.name = rs.getString("name");
				this.description = rs.getString("description");
				this.keywords = rs.getString("keywords");
				this.userId = rs.getInt("userId");
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println("Exception occured when parcing through the resultSet");
			//e.printStackTrace();
		}
	}
	public String getItemName(){
		return name;
	}
	public int getItemOwner(){
		return userId;
	}
	public String getItemDescription(){
		return description;
	}
	private Connection getConnection(){
		Connection con=null;
		try {
			con = pool.getConnectionFromPool();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	private Statement getStatement(Connection con){
		Statement st=null;
		try {
			st=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return st;
	}
	/**
	 * @author Irakli
	 * This method inserts the item into one of the tables of the database
	 * depending on the parameter that is passed to the function(int type is 1 or 0)
	 */
	public void insert(int type){
		/*Connection con=getConnection();
		Statement st=getStatement(con);
		
		try {
			st.executeQuery(queryGenerator.getItemInsertQuery(type, userId, name, description, keywords));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}pool.returnConnectionToPool(con);*/
		executor.executeQuery(queryGenerator.getItemInsertQuery(type, userId, name, description, keywords));
		
	}
	/**
	 * @author irakli
	 * This method selects an item from the database and returns it. 
	 * es metodi gamogvadgeba mashin, roca useri daachers konkretul
	 * items da magis gverdis gaxsna mogviwevs mashin parametrad url-s 
	 * gadaecema item-is id da iq ukve am metods gamoviyeneb servletshi da 
	 * informacias gamovutan users. 
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	/*public Item select(int itemId) throws NumberFormatException, SQLException{
		Connection con=getConnection();
		Statement st= getStatement(con);
		ResultSet rs=null;
		try {
			st.executeQuery("USE Exchange");
			rs=st.executeQuery(queryGenerator.getItemSelectQuery(itemId));
			rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.executeQuery(queryGenerator.getItemInsertQuery(type, userId, name, description, keywords));
		Item t= new Item(Integer.parseInt(rs.getString("ID")), rs.getString("name"),rs.getString("description"), rs.getString("keywords"), Integer.parseInt(rs.getString("userId")));
		return t;
	}*/
	public static void main(String[] args) {
		DBqueryGenerator q=new DBqueryGenerator();
		org.apache.tomcat.jdbc.pool.ConnectionPool p=null;
		try {
			p = new org.apache.tomcat.jdbc.pool.ConnectionPool(null);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		QueryExecutor e=new QueryExecutor(p);
		Item t=new Item(q,e,0, "bla", "", "", 0);
		System.out.print(t.getItemName());
		
		
		
	}

}
