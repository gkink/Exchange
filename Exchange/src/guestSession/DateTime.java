package guestSession;

import java.sql.Date;
import java.sql.Time;


public class DateTime {

	private Date date;
	private Time time;

	/**
	 * Creates DateTime object on java.sql.Date and java.sql.Time objects.
	 * @param dateTime
	 */

	public DateTime(Date date, Time time){
		this.time = time;
		this.date = date;
	}

	/**
	 * Returns Date
	 * @return Date
	 */
	public Date getDate(){
		return date;
	}

	/**
	 * Returns Time
	 * @return Time
	 */
	public Time getTime(){
		return time;
	}

	@Override
	public boolean equals(Object obj){
		if (obj == this) return true;

		if (!(obj instanceof DateTime)) return false;

		DateTime other = (DateTime)obj;
		
		return (date.equals(other.date)) && (time.equals(other.time));
	}

	@Override
	public String toString(){
		return "| Date: " + date + " | " + "Time: " + time + " |";
	}
}
