package de.g18.ubb.android.client.activities.booking;

import java.util.Date;

public class BookingStateBucket {
	
	private static BookingStateBucket instance;
	
	private Date bookingDate;
	
	private BookingStateBucket(){
		//prevent instantiation
	}
	
	public static BookingStateBucket getInstance(){
		if(instance == null){
			instance = new BookingStateBucket();
		}
		return instance;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	
}
