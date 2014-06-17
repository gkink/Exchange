package guestSession;

import ModelClasses.Pair;
import ModelClasses.User;

public interface TransactionInterface {
	
	public int size();
	
	public Pair<User, ItemsChanged> getUserItemPair(int num);
	
	public void addToTheBases();
	
	public DateTime getDateTime();
	
	public int getID();
}
