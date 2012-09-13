package de.g18.ubb.android.client.activities.booking;

import java.util.ArrayList;
import java.util.List;

import de.g18.ubb.android.client.utils.BookingHelper;
import de.g18.ubb.common.domain.Booking;

/**
 * Helfer Klasse um eine Liste mit Buchungen für die entsprechenden Ansichten
 * (Tag, Woche, Monat, Jahr und Gesamt) aufzubereiten
 *
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public class ContextDrivenBookingsLists {

	private List<Booking> bookingList;

	/**
	 * Akzeptiert eine Liste vom Typ {@link Booking}
	 *
	 */
	public ContextDrivenBookingsLists(List<Booking> aBookingList) {
		bookingList = new ArrayList<Booking>();
		bookingList.addAll(aBookingList);
	}

	/**
	 * Liefert eine Liste vom Typ {@link Booking} zurück.
	 * In dieser Liste befinden sich nur noch Buchungen des aktuellen Tages
	 *
	 * @return List<Booking>
	 */
	public List<Booking> getDayBookingsList() {
		return BookingHelper.getBookingsForCurrentDay(bookingList);
	}

	/**
	 * Liefert eine Liste vom Typ {@link Booking} zurück.
	 * In dieser Liste befinden sich nur noch Buchungen der aktuellen Woche
	 *
	 * @return List<Booking>
	 */
	public List<Booking> getWeekyBookingsList() {
		return BookingHelper.getBookingsForCurrentWeek(bookingList);
	}

	/**
	 * Liefert eine Liste vom Typ {@link Booking} zurück.
	 * In dieser Liste befinden sich nur noch Buchungen des aktuellen Monats
	 *
	 * @return List<Booking>
	 */
	public List<Booking> getMonthBookingsList() {
		return BookingHelper.getBookingsForCurrentMonth(bookingList);
	}

	/**
	 * Liefert eine Liste vom Typ {@link Booking} zurück.
	 * In dieser Liste befinden sich nur noch Buchungen des aktuellen Jahres
	 *
	 * @return List<Booking>
	 */
	public List<Booking> getYearBookingsList() {
		return BookingHelper.getBookingsForCurrentYear(bookingList);
	}
}
