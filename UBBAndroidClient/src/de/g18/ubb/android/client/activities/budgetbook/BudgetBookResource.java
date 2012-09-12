package de.g18.ubb.android.client.activities.budgetbook;

import java.text.MessageFormat;

import de.g18.ubb.common.resource.Resource;

public enum BudgetBookResource implements Resource {

	FIELD_NAME("Name"),
    FIELD_CATEGORIES("Kategorien"),
    FIELD_BOOKINGS("Buchungen"),
    FIELD_ASSIGNED_USER("Zugewiesene Benutzer"),

    MESSAGE_ADD_BUDGET_BOOK_USER("Der angegebene Benutzer ist diesem Haushaltsbuch bereits zugewiesen!"),
    MESSAGE_ADD_NOT_EXISTING_USER("Es existiert kein nutzer mit der EMail-Adress {0}!"),
    ;

    private final String unformattedValue;

    private BudgetBookResource(String aUnformattedValue) {
        unformattedValue = aUnformattedValue;
    }

    public String formatted(Object... aParameters) {
        return MessageFormat.format(unformattedValue, aParameters);
    }
}
