package guestSession;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	
	String [] dateAndTime;
	
	private static final String incorrectData = "Incorrect Data";
	
	public DateTime(String dateTime){
		dateAndTime = dateTime.split(" ");
	}
	
	private String helper(int num){
		if (dateAndTime.length == 2)
			return dateAndTime[num];
		return incorrectData;
	}
	
	public String getDate(){
		return helper(0);
	}
	
	public String getTime(){
		return helper(1);
	}
}
