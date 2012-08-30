package de.g18.ubb.common.domain.enumType;

/**
 * Enum für die Unterscheidung zwischen Einnahmen ({@link #REVENUE}) und Ausgaben ({@link #SPENDING}).
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public enum BookingType {

    REVENUE,
    SPENDING,
    ;

    /**
     * Anzahl der Zeichen des längsten Enum-Namens.
     */
    public static final int MAX_ENTRY_LENGTH = 8;
}
