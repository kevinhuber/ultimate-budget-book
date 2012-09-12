package de.g18.ubb.android.client.activities.booking;

import java.util.ArrayList;
import java.util.List;

import de.g18.ubb.android.client.utils.BookingHelper;
import de.g18.ubb.common.domain.Booking;

public class ContextDrivenBookingsLists {
	
	private List<Booking> bookingList;
	
	public ContextDrivenBookingsLists(List<Booking> aBookingList) {
		bookingList = new ArrayList<Booking>();
		bookingList.addAll(aBookingList);
	}

	public List<Booking> getDayBookingsList() {
		return BookingHelper.getBookingsForCurrentDay(bookingList);
	}

	public List<Booking> getWeekyBookingsList() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Booking> getMonthBookingsList() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Booking> getYearBookingsList() {
		// TODO Auto-generated method stub
		return null;
	}

}
