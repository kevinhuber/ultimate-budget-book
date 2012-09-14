package de.g18.ubb.android.client.preferences;

import de.g18.ubb.android.client.utils.UBBConstants;

/**
 * Enum zum definiern der Schlüßel der Werte, die in den {@link Preferences} gespeichert werden können.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public enum PreferenceKey {

    USERNAME("EMail"),
    PASSWORD("Password"),
    SERVER_ADDRESS(UBBConstants.EMULATOR_SERVER_ADDRESS);
    ;

    private final String key;
    private final String defaultValue;


    private PreferenceKey(String aDefaultValue) {
        key = name().toLowerCase();
        defaultValue = aDefaultValue;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
