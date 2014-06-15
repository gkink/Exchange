package ModelClasses;


/**
 * Generic pair class. 
 * @author Gio
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
	 * @return <T>
	 */
	public T getFirst(){
		return first;
	}
	
	/**
	 * Returns second object from the pair
	 * @return <U>
	 */
	public U getSecond(){
		return second;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == this) return true;
		
		if (!(obj instanceof Pair<?,?>)) return false;
		
		Pair<?, ?> pair = (Pair<?, ?>) obj;
		
		return (pair.first.equals(first)) && (pair.second.equals(second));
	}
	
	@Override
	public String toString(){
		return "(" + first + ", " + second + ")";
	}
}
