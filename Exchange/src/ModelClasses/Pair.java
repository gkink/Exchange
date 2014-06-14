package ModelClasses;



/**
 * @author Gio
 *	Generic pair class. 
 * @param <T>
 * @param <U>
 */
public class Pair<T, U> {
	private T first;
	private U second;
	
	/**
	 * Takes two types of parameters and creates their pair.
	 * @param first
	 * @param second
	 */
	public Pair(T first, U second){
		this.first = first;
		this.second = second;
	}
	
	
	/**
	 * Returns first object from the pair
	 * @return
	 */
	public T getFirst(){
		return first;
	}
	
	/**
	 * Returns second object from the pair
	 * @return
	 */
	public U getSecond(){
		return second;
	}
}
