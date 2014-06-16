package guestSession;

import java.sql.Date;
import java.sql.Time;

import org.junit.runners.model.InitializationError;


public class DateTime {
	
	String [] dateAndTime;
	private Date date;
	private Time time;
	
	/**
	 * Creates DateTime object on string of that kind: "12/12/12 20:02:11".
	 * If there is no exactly one space in the parameter, throws IllegalArgumentException.
	 * @param dateTime
	 */
	public DateTime(String dateTime) throws IllegalArgumentException{
		dateAndTime = dateTime.split(" ");
		
		if(dateAndTime.length != 2){
			throw new IllegalArgumentException();
		}
	}
	
	//es tu gadavakete mashin getData da getTime_ic unda gadavaketo.
	public DateTime(Date date, Time time){
		this.time = time;
		this.date = date;
	}
	
	/**
	 * Returns Date
	 * @return String
	 */
	public String getDate(){
		return dateAndTime[0];
	}
	
	/**
	 * Returns Time
	 * @return String
	 */
	public String getTime(){
		return dateAndTime[1];
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == this) return true;
		
		if (!(obj instanceof DateTime)) return false;
		
		DateTime other = (DateTime)obj;
		
		return (dateAndTime[0] == other.dateAndTime[0]) && (dateAndTime[1] == other.dateAndTime[1]);
	}
	
	@Override
	public String toString(){
		return dateAndTime[0] + " " + dateAndTime[1];
	}
}
