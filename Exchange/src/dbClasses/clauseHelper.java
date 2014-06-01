package dbClasses;

/**
 * @author giorgi
 *	The clauseHelper helps generate the WhereClause for Sql queries.
 *	It is an object with three values, one of them is the String value, 
 *	another is a comparator("like", "=", "<=" ...), and the last one
 *  is a column name(id, username ...)
 */
public class clauseHelper {
	private String column;
	private String value;
	private String comparator;
	
	public clauseHelper(String column, String comparator, String value){
		this.value = value;
		this.comparator = comparator;
		this.column = column;
	}
	
	public String getValue(){
		return value;
	}
	
	public String getComparator(){
		return comparator;
	}
	
	public String getColumn(){
		return column;
	}
}
