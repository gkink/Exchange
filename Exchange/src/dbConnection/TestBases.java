package dbConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbClasses.MyDBInfo;

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
	
	public static void getDataFromBases(Connection con){
		//String query = "select users.ID, firstName, lastName, itemsHave.name from users join itemsHave on itemsHave.userID = users.ID join cycleInfo on cycleInfo.itemID = itemsHave.ID where cycleInfo.cycleID = '1'";
		ResultSet set;
		
		try {
			PreparedStatement preparedStatementInsert = con.prepareStatement("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			preparedStatementInsert.executeUpdate();
			con.setAutoCommit(false);
			preparedStatementInsert = con.prepareStatement("insert into transactions (transDate) values (current_time)");
			preparedStatementInsert.executeUpdate();
			
			PreparedStatement select = con.prepareStatement("select max(ID) from transactions");
			set = select.executeQuery();
			set.next();
			System.out.println(set.getString(1));
			//displaySet(set);
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
