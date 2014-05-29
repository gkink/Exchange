package dbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * am klass yuradgebas nu miaqcevt chemtvis davwere ro gametesta sworad tu mushaobda 
 * bazastan connection da sworad mushaobs tu. 
 * tu tqvenc gindat ro gatestot mashin baza sheqmenit bolos atvirtuli SQL
 * failis mixedvit, mere insertic gaaketet egec im failidan rac repoze devs 
 * da mere am klasis meini gaushvit wesit unda daabrunos 3X4_ze cxrili.*/

public class TestBases {
	
	private static void displaySet(ResultSet set) throws SQLException{
		
		String temp = "";
		while(set.next()){
			
			temp += "|";
			for (int i = 1; i < 5; i++)
				temp += set.getString(i) + "|";
			temp += "\n";
		}
		System.out.println(temp);
	}
	
	public static void getDataFromBases(Statement stmt){
		String query = "select users.ID, firstName, lastName, itemsHave.name from users join itemsHave on itemsHave.userID = users.ID join cycleInfo on cycleInfo.itemID = itemsHave.ID where cycleInfo.cycleID = '1'";
		ResultSet set;
		try {
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			set = stmt.executeQuery(query);
			displaySet(set);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		ConnectionPool pool = null;
		try {
			pool = ConnectionPool.getInstance();
			Connection con = pool.getConnectionFromPool();
			getDataFromBases(con.createStatement());
			pool.returnConnectionToPool(con);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			pool.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
