package de.g18.ubb.android.client.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.enumType.BookingType;

public final class BookingHelper {

    private BookingHelper() {
        // prevent instantiation
    }
    
    public static List<Booking> getBookingsForCurrentDay(List<Booking> aBookingList) {
        Date currentDate = new Date();
        return getBookingsBetween(aBookingList,
                                       DateUtil.getMinDate(currentDate),
                                       DateUtil.getMaxDate(currentDate));
    }
    
    public static List<Booking> getBookingsForCurrentWeek(List<Booking> aBookingList) {
        return getBookingsListForCurrentWeek(aBookingList);
    }
    
    public static List<Booking> getBookingsForCurrentMonth(List<Booking> aBookingList) {
        return getBookingsListForCurrentMonth(aBookingList);
    }
    
    public static List<Booking> getBookingsForCurrentYear(List<Booking> aBookingList) {
        return getBookingsWithGivenYear(aBookingList);
    }
    
    public static float calculateAmmountForCurrentDay(BudgetBook aBudgetBook) {
        return calculateAmmountForCurrentDay(aBudgetBook.getBookings());
    }

    public static float calculateAmmountForCurrentDay(List<Booking> aBookingList) {
        Date currentDate = new Date();
        return calculateAmmountBetween(aBookingList,
                                       DateUtil.getMinDate(currentDate),
                                       DateUtil.getMaxDate(currentDate));
    }

    public static float calculateAmmountBetween(List<Booking> aBookingList, Date aMinDate, Date aMaxDate) {
        float currentAmmount = 0.0f;
        for (Booking booking : aBookingList) {
            if (DateUtil.isBetween(booking.getBookingTime(), aMinDate, aMaxDate)) {
                if (booking.getType() == BookingType.REVENUE) {
                    currentAmmount += booking.getAmount();
                } else {
                    currentAmmount -= booking.getAmount();
                }
            }
        }
        return currentAmmount;
    }
    
    public static List<Booking> getBookingsBetween(List<Booking> aBookingList, Date aMinDate, Date aMaxDate) {
    	List<Booking> tmpBookingList = new ArrayList<Booking>();
        for (Booking booking : aBookingList) {
            if (DateUtil.isBetween(booking.getBookingTime(), aMinDate, aMaxDate)) {
            	tmpBookingList.add(booking);
            }
        }
        return tmpBookingList;
    }
    
    public static List<Booking> getBookingsWithGivenYear(List<Booking> aBookingList) {
    	List<Booking> tmpBookingList = new ArrayList<Booking>();
    	
    	Calendar beginningYearDate = Calendar.getInstance();
    	beginningYearDate.set(Calendar.DAY_OF_YEAR, 1);
    	beginningYearDate.set(Calendar.MONTH, 1);
    	
    	Date currentDate = new Date();
    	
        for (Booking booking : aBookingList) {
            if (DateUtil.isBetween(booking.getBookingTime(), beginningYearDate.getTime(), currentDate)) {
            	tmpBookingList.add(booking);
            }
        }
        return tmpBookingList;
    }
    
    public static List<Booking> getBookingsListForCurrentWeek(List<Booking> aBookingList) {
    	List<Booking> tmpBookingList = new ArrayList<Booking>();
    	
    	Calendar beginningWeekDate = Calendar.getInstance();
    	beginningWeekDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	
    	Date currentDate = new Date();
    	
        for (Booking booking : aBookingList) {
            if (DateUtil.isBetween(booking.getBookingTime(), beginningWeekDate.getTime(), currentDate)) {
            	tmpBookingList.add(booking);
            }
        }
        return tmpBookingList;
    }
    
    public static List<Booking> getBookingsListForCurrentMonth(List<Booking> aBookingList) {
    	List<Booking> tmpBookingList = new ArrayList<Booking>();
    	
    	Calendar beginningYearDate = Calendar.getInstance();
    	beginningYearDate.set(Calendar.DAY_OF_MONTH, 1);
    	
    	Date currentDate = new Date();
    	
        for (Booking booking : aBookingList) {
            if (DateUtil.isBetween(booking.getBookingTime(), beginningYearDate.getTime(), currentDate)) {
            	tmpBookingList.add(booking);
            }
        }
        return tmpBookingList;
    }
}
