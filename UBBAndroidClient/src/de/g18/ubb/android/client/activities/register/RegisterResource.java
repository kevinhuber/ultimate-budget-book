package de.g18.ubb.android.client.activities.register;

import java.text.MessageFormat;

import de.g18.ubb.common.resource.Resource;

/**
 * {@link Resource} für die {@link RegisterActivity}.
 *
 * @author Sebastian.Kopatz
 */
public enum RegisterResource implements Resource {

    FIELD_USERNAME("Benutzername"),
    FIELD_EMAIL("EMail"),
    FIELD_PASSWORD("Passwort"),
    FIELD_PASSWORD_CHECK("Passwort-Wiederholung"),

    MESSAGE_REGISTRATION_FAILED("Registration fehlgeschlagen!"),
    MESSAGE_REGISTRATION_IN_PROGRESS("Registrierung wird abgeschlossen..."),

    VALIDATION_PASSWORDS_MUST_BE_EQUAL("Passwörter stimmen nicht überein!"),
    VALIDATION_EMAIL_ALREADY_USED("EMail wird bereits verwendet!"),
    ;


    private final String unformattedValue;


    private RegisterResource(String aUnformattedValue) {
        unformattedValue = aUnformattedValue;
    }

    public String formatted(Object... aParameters) {
        return MessageFormat.format(unformattedValue, aParameters);
    }
}
