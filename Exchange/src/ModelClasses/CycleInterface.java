package ModelClasses;

import guestSession.DateTime;
import guestSession.Item;

public interface CycleInterface {
	
	public int cycleSize();
	
	public Pair<User, Item> getUserItemPair(int num);
	
	public void addToTheBases();
	
	//es jer gaugebaria ra dro iqneba an sachiroa tu ara saertod.
	public DateTime getDateTime();
	
	public int getID();
}
