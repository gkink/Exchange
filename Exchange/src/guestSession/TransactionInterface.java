package guestSession;

import ModelClasses.MyItemInterface;
import ModelClasses.Pair;
import ModelClasses.User;

public interface TransactionInterface {
	
	public int transactionSize();
	
	public Pair<User, MyItemInterface> getUserItemPair(int num);
	
	public void addToTheBases();
	
	public DateTime getDateTime();
	
	public int getID();
}
