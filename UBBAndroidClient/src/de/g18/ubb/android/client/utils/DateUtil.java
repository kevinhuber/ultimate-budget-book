package de.g18.ubb.android.client.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Util-Klasse für {@link Date}-Objekte.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class DateUtil {

    private DateUtil() {
        // prevent instantiation
    }

    /**
     * Erstellt aus dem übergebenen {@link Date}-Objekt ein neues {@link Date}-Objekt, in welchem der Zeit-Teil auf
     * 00:00:00 gesetzt ist.
     * Bei übergabe von <code>null</code> wird <code>null</code> zurückgegeben.
     */
    public static Date getMinDate(Date aDate) {
        if (aDate == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * Erstellt aus dem übergebenen {@link Date}-Objekt ein neues {@link Date}-Objekt, in welchem der Zeit-Teil auf
     * 23:59:59 gesetzt ist.
     * Bei übergabe von <code>null</code> wird <code>null</code> zurückgegeben.
     */
    public static Date getMaxDate(Date aDate) {
        if (aDate == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * Prüft ob das überebene {@link Date}-Objekt Zeitlich zwischen den beiden anderen {@link Date}-Objekten liegt.
     * Sollte eine der drei Parameter <code>null</code> sein, wird <code>false</code> zurückgegeben.
     */
    public static boolean isBetween(Date aDate, Date aMin, Date aMax) {
        return isBefore(aDate, aMax) && isAfter(aDate, aMin);
    }

    /**
     * Null-sicherer aufruf der Methode {@link Date#before(Date)}.
     * Sollte eine der beiden Parameter <code>null</code> sein, wird <code>false</code> zurückgegeben.
     */
    public static boolean isBefore(Date aFirstDate, Date aSecondDate) {
        if (aFirstDate == null || aSecondDate == null) {
            return false;
        }
        return aFirstDate.before(aSecondDate);
    }

    /**
     * Null-sicherer aufruf der Methode {@link Date#after(Date)}.
     * Sollte eine der beiden Parameter <code>null</code> sein, wird <code>false</code> zurückgegeben.
     */
    public static boolean isAfter(Date aFirstDate, Date aSecondDate) {
        if (aFirstDate == null || aSecondDate == null) {
            return false;
        }
        return aFirstDate.after(aSecondDate);
    }
}
