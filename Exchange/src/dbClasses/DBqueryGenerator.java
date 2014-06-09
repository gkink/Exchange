package dbClasses;

import java.util.ArrayList;

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
	public String getTransactionsQuery(){
		return "select * from transactions order by ID desc limit 3";
	}
	
	public String getTransactionInfoQuery(int ID){
		return "select * from transactionInfo where transactionID = " + ID;
	}
	
	public String getUserQuery(int ID){
		return "select * from users where ID = " + ID;
	}
	
	public String getItemQuery(int ID){
		return "select * from itemsChanged where ID = " + ID;
	}
	
	public String getUserUserQuery(int id){
		return "select * from users where ID = " + toValue("" + id);
	}
	
	public String insertIntoUsers(int id, int ranking, String firstName, String lastName, String email){
		String res = generateInsert("users", 
				new String[]{"ID", "firstName", "lastName", "email", "ranking"}, 
				new String[]{"" + id, firstName, lastName, email, "" + ranking});
		
		return res;
	}
	
	public String deleteFromUsers(int id){
		return "delete * from users where ID = " + toValue("" + id);
	}
}