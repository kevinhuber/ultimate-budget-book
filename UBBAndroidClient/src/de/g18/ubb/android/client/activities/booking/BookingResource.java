package de.g18.ubb.android.client.activities.booking;

import java.text.MessageFormat;

import de.g18.ubb.common.resource.Resource;

public enum BookingResource implements Resource {

	PROPERTY_TYPE("type"),
	PROPERTY_AMOUNT("amount"),
	PROPERTY_CATEGORY("category"),
	PROPERTY_BOOKING_TIME("bookingTime"),
	PROPERTY_BOOKING_NAME("bookingName"),

    MESSAGE_CREATE_BOOKING_FAILED("Buchungs Erstellung fehlgeschlagen!"),
    MESSAGE_CREATE_AMOUNT_REVENUE_NEGATIVE("Der Betrag darf bei einer Einzahlung nicht negativ sein!"),
    MESSAGE_CREATE_AMOUNT_SPENDING_POSITIVE("Der Betrag darf bei einer Auszahlung nicht positiv sein!"),
    
    ;

    private final String unformattedValue;

    private BookingResource(String aUnformattedValue) {
        unformattedValue = aUnformattedValue;
    }

    public String formatted(Object... aParameters) {
        return MessageFormat.format(unformattedValue, aParameters);
    }
}
