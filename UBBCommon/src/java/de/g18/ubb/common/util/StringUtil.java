package de.g18.ubb.common.util;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class StringUtil {

    /**
     * String representing a <code>null</code> value.
     */
    public static final String NULL = "null";

    /**
     * Representiert einen leeren String.
     */
    public static final String EMPTY = "";


    // TODO (huber): Add docs
    public static String removeEnd(String aString, String aToRemove) {
        if (isEmpty(aString) || !endsWith(aString, aToRemove)) {
            return aString;
        }
        return subStringBefore(aString, aToRemove);
    }

    /**
     * @see #isEmpty(String)
     */
    public static boolean isNotEmpty(String aString) {
        return !isEmpty(aString);
    }

    /**
     * @return <code>true</code> falls der übergebene String <code>null</code> oder <code>""</code> ist
     */
    public static boolean isEmpty(String aString) {
        return aString == null || ObjectUtil.equals(EMPTY, aString);
    }

    /**
     * @see #endsWith(String, String)
     */
    public static boolean endsNotWith(String aString, String aEnd) {
        return !endsWith(aString, aEnd);
    }

    /**
     * Null-Safe Aufruf der Methode {@link String#endsWith(String)}
     */
    public static boolean endsWith(String aString, String aEnd) {
        if (aString == null || aEnd == null) {
            return false;
        }
        return aString.endsWith(aEnd);
    }

    public static boolean endsWithIgnoreCase(String aString, String aEnd) {
        if (aString == null || aEnd == null) {
            return false;
        }
        return endsWith(aString.toLowerCase(), aEnd.toLowerCase());
    }

    /**
     * @see #startsWith(String, String)
     */
    public static boolean startsNotWith(String aString, String aEnd) {
        return !startsWith(aString, aEnd);
    }

    /**
     * Null-Safe Aufruf der Methode {@link String#startsWith(String)}
     */
    public static boolean startsWith(String aString, String aEnd) {
        if (aString == null || aEnd == null) {
            return false;
        }
        return aString.startsWith(aEnd);
    }

    // TODO (huber): Add docs
    public static String subStringBefore(String aString, String aDelimiter) {
        if (isEmpty(aString) || isEmpty(aDelimiter) ) {
            return aString;
        }

        int delimiterIndex = indexOf(aString, aDelimiter);
        if (delimiterIndex <= 0) {
            return aString;
        }
        return aString.substring(0, delimiterIndex);
    }

    // TODO (huber): Add docs
    public static int indexOf(String aString, String aToFind) {
        if (isEmpty(aString) || isEmpty(aToFind)) {
            return -1;
        }
        return aString.indexOf(aToFind);
    }

    /**
     * Null-Safe call of {@link Object#toString()}.
     * @return result of {@link Object#toString()} or "null" on <code>null</code> input
     */
    public static String toString(Object aObject) {
        if (aObject == null) {
            return NULL;
        }
        return aObject.toString();
    }
}
