package ModelClasses;

import guestSession.DateTime;
import guestSession.ItemInterface;
import guestSession.ItemsHaveObject;

public interface CycleInterface {
	
	public int cycleSize();
	
	public Pair<User, ItemsHaveObject> getUserItemPair(int num);
	
	public void addToTheBases();
		
	public int getID();
}
