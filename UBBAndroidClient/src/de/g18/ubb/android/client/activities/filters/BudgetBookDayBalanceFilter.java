package de.g18.ubb.android.client.activities.filters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;

public class BudgetBookDayBalanceFilter {
	
	public static List<BudgetBook> filterBudgetBooksDayBalance(List<BudgetBook> aBudgetBook){
		//key ist booking index und value ist BudgetBook index
		Map<Integer, Integer> posOfBooksAndBookings = new HashMap<Integer, Integer>(); 
		
		// suche alle bookings raus die nicht dem aktuellem tag entsprechen
		// speichere diese in der hashmap ab inkl. BudgetBook index
		for (BudgetBook budgetBook : aBudgetBook) {
			for (Booking booking : budgetBook.getBookings()) {
				if(!compareDates(booking.getBookingTime(), new Date())){
					posOfBooksAndBookings.put(budgetBook.getBookings().indexOf(booking) ,aBudgetBook.indexOf(budgetBook));
				} 
			}
		}
		
		for (Map.Entry<Integer, Integer> entry : posOfBooksAndBookings.entrySet()) { 
			aBudgetBook.get(entry.getValue()).getBookings().remove(entry.getKey());
		} 
		
		return aBudgetBook;
	}

	private static boolean compareDates(Date bookingDate, Date timeNow) {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(bookingDate);
			cal2.setTime(timeNow);
			boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
			                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
 
		return sameDay;
	}
}
