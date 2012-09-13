package de.g18.ubb.android.client.activities.booking;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.g18.ubb.android.client.utils.UBBConstants;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;


/**
 * Erzeugt einen eigenen {@link DatePickerDialog } über den sich das Datum eines {@link DatePicker} festlegen lässt
 * 
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public class BookingDatePickerDialog extends DatePickerDialog {
	
	private final int minYear = 1960;
	private final int minMonth = 0;
	private final int minDay = 1;

	private int maxYear;
	private int maxMonth;
	private int maxDay;

	private final Calendar mCalendar;
	private final SimpleDateFormat formatter;
	
/**
 * Über den Konstruktor der Klasse lässt sich ein Startdatum, 
 * der Context und ein Callback festlegen
 * 
 * @param context
 * @param callBack
 * @param year
 * @param monthOfYear
 * @param dayOfMonth
 */
	public BookingDatePickerDialog(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);

		mCalendar = Calendar.getInstance();

		mCalendar.setTime(new Date());
		// maximales Datum setzen
		maxYear = mCalendar.get(Calendar.YEAR);
		maxMonth = mCalendar.get(Calendar.MONTH);
		maxDay = mCalendar.get(Calendar.DATE);

		// initiales Datum setzen
		mCalendar.set(Calendar.YEAR, year);
		mCalendar.set(Calendar.MONTH, monthOfYear);
		mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		// legt das Datumsformat fest
		formatter = new SimpleDateFormat(UBBConstants.DATE_FORMAT);
		setTitle(formatter.format(mCalendar.getTime()));
	}

	/**
	 * Wenn sich das Datum ändert, wird überprüft ob es noch innerhalb der definierten zeitlichen Grenze liegt
	 */
	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		boolean beforeMinDate = false;
		boolean afterMaxDate = false;

		if (year < minYear) {
			beforeMinDate = true;
		} else if (year == minYear) {
			if (month < minMonth) {
				beforeMinDate = true;
			} else if (month == minMonth) {
				if (day < minDay) {
					beforeMinDate = true;
				}
			}
		}

		if (!beforeMinDate) {
			if (year > maxYear) {
				afterMaxDate = true;
			} else if (year == maxYear) {
				if (month > maxMonth) {
					afterMaxDate = true;
				} else if (month == maxMonth) {
					if (day > maxDay) {
						afterMaxDate = true;
					}
				}
			}
		}

		// ungültige Datumseingaben werden auf min oder max datum gesetzt
		if (beforeMinDate || afterMaxDate) {
			if (beforeMinDate) {
				year = minYear;
				month = minMonth;
				day = minDay;
			} else {
				year = maxYear;
				month = maxMonth;
				day = maxDay;
			}
			view.updateDate(year, month, day);
		}

		// zeige im Dialog das Datum als Titel an
		mCalendar.set(Calendar.YEAR, year);
		mCalendar.set(Calendar.MONTH, month);
		mCalendar.set(Calendar.DAY_OF_MONTH, day);
		setTitle(formatter.format(mCalendar.getTime()));
	}
}
