package de.g18.ubb.android.client.activities.booking;

import java.text.MessageFormat;

import de.g18.ubb.common.resource.Resource;

/** Enum mit den einzelnen Feldern des Formulares
 *
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public enum BookingResource implements Resource {

	FIELD_TYP("Buchungstyp"),
	FIELD_BETRAG("Betrag"),
	FIELD_KATEGORIE("Kategorie"),
	FIELD_DATUM("Datum"),
	FIELD_NAME("Bezeichnung"),

    VALIDATION_AMMOUNT_MUST_BE_POSITIV("Der Betrag muss positiv sein!"),
    ;

    private final String unformattedValue;

    private BookingResource(String aUnformattedValue) {
        unformattedValue = aUnformattedValue;
    }

    public String formatted(Object... aParameters) {
        return MessageFormat.format(unformattedValue, aParameters);
    }
}
