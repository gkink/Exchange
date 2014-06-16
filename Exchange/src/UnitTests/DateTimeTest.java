package UnitTests;

import static org.junit.Assert.*;
import guestSession.DateTime;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.sql.Time;

public class DateTimeTest {

	private DateTime dateTime;
	private DateTime dateTime2;
	private Date date, date2;
	private Time time, time2;
	
	@Before
	public void init(){
		date = mock(Date.class);
		when(date.toString()).thenReturn("1994/01/11");
		
		time = mock(Time.class);
		when(time.toString()).thenReturn("12:00:00"); 
		
		dateTime = new DateTime(date, time);
		
		date2 = new Date(10000);
		time2 = new Time(100000);
		
		dateTime2 = new DateTime(date2, time2);
	}
	
	@Test
	public void getTimeTest() {
		assertEquals(dateTime.getTime(), time);
	}
	
	@Test
	public void getDateTest() {
		assertEquals(dateTime.getDate(), date);
	}
	
	@Test
	public void equalsTest(){
		assertTrue(dateTime.equals(dateTime));
		assertFalse(dateTime.equals(new Integer(7)));
		assertFalse(dateTime.equals(dateTime2));
		
		dateTime2 = new DateTime(date, time2);
		assertFalse(dateTime.equals(dateTime2));
		
		dateTime2 = new DateTime(date, time);
		assertTrue(dateTime.equals(dateTime2));
		
		dateTime2 = new DateTime(date2, time);
		assertFalse(dateTime.equals(dateTime2));
	}
	
	@Test
	public void toStringTest(){
		assertEquals(dateTime.toString(), "1994/01/11 12:00:00");
	}

}
