package de.g18.ubb.android.client.activities.login;

import java.text.MessageFormat;

import de.g18.ubb.common.resource.Resource;

/**
 * {@link Resource} für die {@link LoginActivity}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public enum LoginResource implements Resource {

    FIELD_EMAIL("EMail"),
    FIELD_PASSWORD("Passwort"),

    MESSAGE_LOGIN("Anmeldung läuft..."),
    MESSAGE_LOGIN_FAILED("Login fehlgeschlagen!"),
    ;


    private final String unformattedValue;


    private LoginResource(String aUnformattedValue) {
        unformattedValue = aUnformattedValue;
    }

    public String formatted(Object... aParameters) {
        return MessageFormat.format(unformattedValue, aParameters);
    }
}
