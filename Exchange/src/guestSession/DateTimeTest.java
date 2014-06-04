package guestSession;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DateTimeTest {

	private DateTime dateTime1, dateTime2;
	
	@Before
	public void init(){
		dateTime1 = new DateTime("2014/06/01 04:32");
		dateTime2 = new DateTime("2010/08/1405:17");
	}
	
	@Test
	public void getTimeTest() {
		assertEquals(dateTime1.getTime(), "04:32");
		assertEquals(dateTime2.getTime(), "Incorrect Data");
	}
	
	@Test
	public void getDateTest() {
		assertEquals(dateTime1.getDate(), "2014/06/01");
		assertEquals(dateTime2.getDate(), "Incorrect Data");
	}

}
