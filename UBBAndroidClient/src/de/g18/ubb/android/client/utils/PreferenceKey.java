package de.g18.ubb.android.client.utils;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public enum PreferenceKey {

    USERNAME("EMail"),
    PASSWORD("Password"),
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
