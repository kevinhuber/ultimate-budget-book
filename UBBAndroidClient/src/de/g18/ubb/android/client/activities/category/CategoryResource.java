package de.g18.ubb.android.client.activities.category;

import java.text.MessageFormat;

import de.g18.ubb.common.resource.Resource;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public enum CategoryResource implements Resource {

    FIELD_NAME("Name"),
    VALIDATION_NAME_ALREADY_USED("Name wird bereits verwendet!"),
    ;


    private final String unformattedValue;


    private CategoryResource(String aUnformattedValue) {
        unformattedValue = aUnformattedValue;
    }

    public String formatted(Object... aParameters) {
        return MessageFormat.format(unformattedValue, aParameters);
    }
}
