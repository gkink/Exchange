package guestSession;

import java.util.Iterator;
import java.util.Map;

public interface TransactionInterface {
	
	public Iterator<String> getUsers();
	
	public Iterator<String> getItems();
	
	public Map<String, String> getItemsAndUsers();
	
	public void addToTheBases();
	
	public DateTime getDateTime();
	
	public int getID();
}
