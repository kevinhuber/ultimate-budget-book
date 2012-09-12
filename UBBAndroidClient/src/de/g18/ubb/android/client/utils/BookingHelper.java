package de.g18.ubb.android.client.utils;

import java.util.ArrayList;
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
}
