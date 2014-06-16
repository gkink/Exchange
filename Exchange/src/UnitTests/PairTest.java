package UnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ModelClasses.Pair;

public class PairTest {
	
	private Pair<String, Integer> pair;
	private Pair<String, String> pair2;

	@Before
	public void init() {
		String st = "testPair";
		Integer i = new Integer(12);
		pair = new Pair<String, Integer>(st, i);
		pair2 = new Pair<String, String>("", "");
	}
	
	@Test
	public void getFirstTest(){
		assertEquals("testPair", pair.getFirst());
		assertEquals("", pair2.getFirst());
	}
	
	@Test
	public void getSecondTest(){
		assertEquals(new Integer(12), pair.getSecond());
		assertEquals("", pair2.getSecond());
	}
	
	@Test
	public void equalsTest(){
		assertFalse(pair.equals(new Integer(3)));
		assertTrue(pair.equals(pair));
		
		assertFalse(pair.equals(new Pair<String, String>("", "")));
		assertTrue(pair.equals(new Pair<String, Integer>("testPair", 12)));
		assertFalse(pair.equals(new Pair<String, Integer>("", 12)));
		assertFalse(pair.equals(new Pair<String, Integer>("testPair", 11)));
	}
	
	@Test
	public void toStringTest(){
		assertEquals(pair.toString(), "(testPair, 12)");
		assertEquals(pair2.toString(), "(, )");
	}

}
