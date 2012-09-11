package de.g18.ubb.common.domain.enumType;

import java.text.MessageFormat;

import de.g18.ubb.common.resource.Resource;

/**
 * Enum für die Unterscheidung zwischen Einnahmen ({@link #REVENUE}) und Ausgaben ({@link #SPENDING}).
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public enum BookingType implements Resource {

    REVENUE("Einnahme"),
    SPENDING("Ausgabe"),
    ;

    /**
     * Anzahl der Zeichen des längsten Enum-Namens.
     */
    public static final int MAX_ENTRY_LENGTH = 8;


    private final String unformattedValue;


    private BookingType(String aUnformattedValue) {
        unformattedValue = aUnformattedValue;
    }

    @Override
    public String formatted(Object... aParameters) {
        return MessageFormat.format(unformattedValue, aParameters);
    }
}
