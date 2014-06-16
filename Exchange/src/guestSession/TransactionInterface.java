package guestSession;

import ModelClasses.ItemInterface;
import ModelClasses.Pair;
import ModelClasses.User;

public interface TransactionInterface {
	
	public int transactionSize();
	
	public Pair<User, ItemInterface> getUserItemPair(int num);
	
	public void addToTheBases();
	
	public DateTime getDateTime();
	
	public int getID();
}
