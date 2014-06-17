package ModelClasses;

import guestSession.DateTime;
import guestSession.Item;
import guestSession.ItemInterface;
import guestSession.ItemsHaveObject;

public interface CycleInterface {
	
	public int cycleSize();
	
	public Pair<User, ItemInterface> getUserItemPair(int num);
	
	public void addToTheBases();
		
	public int getID();
}
