package guestSession;

import ModelClasses.Pair;
import ModelClasses.User;

public interface TransactionInterface {
	
	public int transactionSize();
	
	public Pair<User, String> getUserItemPair(int num);
	
	public void addToTheBases();
	
	public DateTime getDateTime();
	
	public int getID();
}
