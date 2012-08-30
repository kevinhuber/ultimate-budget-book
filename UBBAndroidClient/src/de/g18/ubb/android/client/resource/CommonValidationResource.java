package de.g18.ubb.android.client.resource;

import java.text.MessageFormat;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public enum CommonValidationResource implements Resource {

    MUST_NOT_BE_EMPTY("{0} darf nicht leer sein!"),
    INVALID_EMAIL_FORMAT("{0} entspricht nicht dem EMail-Format name@host.domain"),
    ;

    private final String unformattedValue;


    private CommonValidationResource(String aUnformattedValue) {
        unformattedValue = aUnformattedValue;
    }

    public String formatted(Object... aParameters) {
        return MessageFormat.format(unformattedValue, aParameters);
    }
}
