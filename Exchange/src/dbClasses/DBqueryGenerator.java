package dbClasses;

import guestSession.DateTime;





/**
 * 
 * @author giorgi
 * this class does not yet include the update and delete queries.
 */

public class DBqueryGenerator {
	/**
	 * method String
	 * @param tablename - name of a valid table(this should be a final string from SqlConstants interface - not yet created)
	 * @param coloumns - array of table column names user wants to insert values in
	 * @param values - array of values user wants to insert in columns(expected with the same order as columns)
	 * @return - returns an insert query of values into tablename's columns
	 * @returns - null if the parameters are invalid(the size of columns is not equal to the size of values)
	 */
	public String generateInsert(String tablename, String[] columns, String[] values){
		if(columns.length != values.length)
			return null;

		StringBuilder ret = new StringBuilder("insert into ");
		ret.append(tablename);

		String toAppend = generateStringList(columns, false);
		ret.append(" (");
		ret.append(toAppend);
		ret.append(")");


		ret.append(" values");

		toAppend = generateStringList(values, true);
		ret.append(" (");
		ret.append(toAppend);
		ret.append(")");

		return ret.toString();
	}

	/*
	 * generates two types of String list without braces, depending on the value of Val boolean:
	 * if it is false then generates a String like this one: id, name, password
	 * else, generates a String like this one: '2', 'giorgi', 'ghambashidze'
	 */
	private String generateStringList(String[] list, boolean Val){
		StringBuilder ret = new StringBuilder();

		if(Val){
			ret.append(toValue(list[0]));

			for(int i = 1 ; i < list.length ; i++){
				ret.append(", ");
				ret.append(toValue(list[i]));
			}
		}else{
			ret.append(list[0]);

			for(int i = 1 ; i < list.length ; i++){
				ret.append(", ");
				ret.append(list[i]);
			}
		}


		return ret.toString();
	}

	/*
	 * takes an ordinary String, for instance: giorgi. and returns the same String in 
	 * single quoteslike this: 'giorgi'
	 */
	private String toValue(String rawValue){
		return "'" + rawValue + "'";
	}


	/**
	 * method String
	 * @param tableName - name of a table user wants to select from
	 * @param columns - a String array of names of the columns of the tablename
	 * Note: if client passes null as columns parameter, then it will automatically generate select * query.
	 * @param values - an array ofclauseCouple objects that carry information about the values of the columns,
	 * and comparators("<", "=", "like" ....). the order of elements is expected to be the same as column array
	 * colnames.
	 * @return - returns a select query if the information given is valid
	 */
	public String generateSelect(String tableName, String[] columns, clauseHelper[] values, String connector){
		StringBuilder ret = new StringBuilder("select ");
		String cols = generateStringList(columns, false);
		ret.append(cols);
		ret.append(" from ");
		ret.append(tableName);

		String where = generateWhereClause(columns, values, connector);
		ret.append(where);

		return ret.toString();
	}

	/*
	 * generates where clause using array of clauseCouples and connector String("and", "or" ..)
	 */
	private String generateWhereClause(String[] columns, clauseHelper[] values, String connector){
		StringBuilder ret = new StringBuilder(" where ");
		String curr = singleConditionString(values[0]);
		ret.append(curr);

		for(int i = 1 ; i < columns.length ; i++){
			curr = singleConditionString(values[i]);
			ret.append(connector + " " + curr);
		}

		return ret.toString();
	}

	/*
	 * given a columnName and clauseCouple object, generates single condition
	 * For instance: given a columnName='name' and couple with value='gi' and comparator='like',
	 * the result String will look like this: "name like '%gi%';
	 */
	private String singleConditionString(clauseHelper couple){
		String colName = couple.getColumn();
		String cmp = couple.getComparator();
		String val = "";

		if(cmp.equals("like"))
			val = "%" + couple.getValue() + "%";
		else
			val =  couple.getValue();

		val = toValue(val);// transfers val to 'val'

		String res = colName + " " + cmp + " " + val + " ";
		return res;
	}


	/**
	 * @author Gio
	 * Generates query string for getting last x transaction from databases.
	 * Needs to be changed. Also all the methods below are temporary.
	 */
	public String getTransactionsQuery(int num){
		return "select * from transactions order by ID desc limit " + num;
	}

	public String getTransactionInfoQuery(int ID){
		return "select * from transactionInfo where transactionID = " + ID;
	}

	public String getUserQuery(int ID){
		return "select * from users where ID = " + ID;
	}

	/**Gio
	 * returns select query for the itemsChanget table
	 * @param ID
	 * @return String
	 */
	public String getItemQuery(int ID){
		return "select * from itemsChanged where ID = " + ID;
	}

	public String getUserUserQuery(int id){
		return "select * from users where ID = " + toValue("" + id);
	}
	
	public String getUserByEmail(String email){
		return "select * from users where email = " + toValue("" + email);		
	}

	public String insertIntoUsers(int ranking, String firstName, String lastName, String email,String password){
		String res = generateInsert("users", 
				new String[]{"firstName", "lastName", "email", "ranking","password"}, 
				new String[]{firstName, lastName, email, "" + ranking, password});

		return res;
	}
	
	public String deleteFromUsers(int id){
		return "delete * from users where ID = " + toValue("" + id);
	}
	
	public String getTransactionQuery(int iD) {
		

		return "select userID, itemId from (select itemID from transactionInfo where transactionID = 2) as A join itemsChanged on A.itemID = id;";

	}
	
	public String getCycleSelect(int id){
		String res = "select itemID, userID from cycleInfo join itemsHave on itemID = itemsHave.ID where cycleInfo.cycleID = " 
				+ toValue("" + id);
		
		return res;
	}
	
	public String getCycleByItem(int itemID){
		return "select cycleID from cycleInfo where itemID = " + toValue("" + itemID);
	}
	
	public String insertIntoCycles(){
		return "insert into cycles() values()";
	}
	
	public String cycleInfoInsert(int cycleID, int itemID){
		return "insert into cycleInfo (cycleID, itemId) values (" + toValue("" + cycleID) + "," + toValue("" + itemID) + ")";
	}

	public String getItemChangedWithUser(int itemID) {
		//TODO
		return null;
	}

	public String getTrasactionsForUserQuery(int userID) {
		return "select * from transactions where ID = " + userID;
	}
	/**
	 * @author Irakli
	 * Generates query string to insert an item into one of the 
	 * tables of the database.
	 * If type is 0 the itemsHave insert is generated, if type is 1
	 * itemsNeed insert is generated.
	 * @param createDate 
	 */
	public String getItemInsertQuery(int type, int userId, String name, String descr, String kw, DateTime createDate){
		String Date=",'"+createDate.getDate().toString()+" " + createDate.getTime().toString()+"'";
		return "insert into itemsHave "+"(name, description, keywords, userID,createDate"+")"+"\n"+
		"values ("+"'" +name+"',"+"'" +descr+"',"+"'" +kw+"'," +userId+Date+")";
	}
	public String getItemsNeedInsertQuery(int userId, String name,String keywords){
		return "insert into itemsNeed (userId, name, keywords) values("+userId+","+toValue(name)+","+toValue(keywords)+")";
	}
	public String getItemSelectQuery(String table, int ID){
		
		return "select * from "+table+" where ID=" +"'"+ID+"'";
	}
	public String getItemsChangedInsertQuery(int userId, String name){
		return "insert into itemsChanged(userId, name) values("+ userId+","+"'"+name+"'"+")";
	}
	
	
	public String getItemByUser(int userId){
		return "select itemsHave.ID from itemsHave where userID = " + toValue("" + userId);
	}
	
	public String getItemUpdateQuery(String table, String field, String update, int itemId){
		return "update "+table+ " Set " +field+"="+"'"+update+"'"+" WHERE ID="+ "'"+itemId+ "'";
	}
	public String getItemDeleteQuery(String table, int id){
		return 	"DELETE from "+table+" Where ID = " + "'"+id+ "';";
		
	}
	public String getRealItemInsertQuery(int userId, int itemID){
		return "insert into realItems(userId, itemId) values("+userId+","+itemID+")";
		
	}
	
	
	//returns String to get the latest items added by users
	public String getLatestItems() {
		return "SELECT * from itemsHave Order By createDate Limit 0, 10";
		
	}
	/*returns String to get the items with the given userId uses the type
	 *  parameter to specify which table it should use	to get the items
	*/
	public String getUserItems(int UserId, int type){
		String table="itemsHave";
		if(type==1) table ="itemsNeed";
		if (type==2) table= "realItems";
		return "Select * from "+ table+ " Where userId="+ "'"+UserId+ "'";
	}
	private String getUserRealItems(int userId){
		return "Select  name, description,ItemsHave.userId, keywords, itemsHave.ID, createDate from itemsHave"+
" join realItems on itemsHave.ID=realItems.itemID and realItems.UserID=" +userId;
		
	}
	public String searchItemsKeywords(String word){
		return "Select * from itemsHave where keywords like '%" +word+"%'";
		
		
	}
	
	
	/**@author Gio
	 * returns insert string into the transactions table
	 * @return String
	 */
	public String insertIntoTransactions(){
		return "insert into transactions(transDate) values(current_time)";
	}
	
	/**
	 * returns insert string into the transactionInfo table
	 * @return String
	 */
	public String insertIntoTransactionInfo(int transactionId, int itemID){
		return "insert into transactionInfo(transactionID, itemID) values(" + transactionId + ", " + itemID + ")";
	}
	
	/**
	 * returns insert string into the itemsChanged table
	 * @return String
	 */
	public String insertIntoItemsChanged(int userID, String name){
		return "insert into itemsChanged(" + userID + ") values(" + toValue(name) + ")";
	}
	public static void main(String[] args) {
		DBqueryGenerator d= new DBqueryGenerator();
		System.out.print(d.getItemsNeedInsertQuery(1, "me", "bla"));
	}
	
	public String deleteCycleInfo(int cycleID){
		return "delete from  cycleInfo where cycleID = " + cycleID;
	}
	
	public String deleteCycle(int cycleID){
		return "delete from Cycles where ID = " + cycleID;
	}
	
	public String insertAcceptCycle(int itemID){
		return "insert (accept) into cycleInfo Values (1) where itemID = " + itemID;
	}
	
	public String insertIntoCyclesHash(int size, int hashRemainder, String cycle){
		return "insert into cyclesHash (size, hashRemainder, cycle) select * from (select " + size + ", " + hashRemainder + ", '" + cycle + "') as temp where not exists (select size, hashRemainder, cycle from cyclesHash where (((size = " + size + ") and hashRemainder = " + hashRemainder + ") and cycle = '" + cycle + "')) limit 1";
	}
}
