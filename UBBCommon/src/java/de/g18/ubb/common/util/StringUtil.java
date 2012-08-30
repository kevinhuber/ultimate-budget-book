package de.g18.ubb.common.util;

/**
 * Util-Klasse für {@link String} -Objekte
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class StringUtil {

    /**
     * String der einen <code>null</code> Wert darstellt.
     */
    public static final String NULL = "null";

    /**
     * Leerer String
     */
    public static final String EMPTY = "";


    /**
     * Entfernt den zweiten {@link String}s vom ende des ersten {@link String}s.
     */
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
     * @return <code>true</code> falls der übergebene String <code>null</code> oder <code>""</code> ist.
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
     * Null-Safe Aufruf der Methode {@link String#endsWith(String)}.
     */
    public static boolean endsWith(String aString, String aEnd) {
        if (aString == null || aEnd == null) {
            return false;
        }
        return aString.endsWith(aEnd);
    }

    /**
     * @see #endsWith(String, String)
     */
    public static boolean endsWithIgnoreCase(String aString, String aEnd) {
        if (aString == null || aEnd == null) {
            return false;
        }
        return endsWith(aString.toLowerCase(), aEnd.toLowerCase());
    }

    /**
     * @see #startsWith(String, String)
     */
    public static boolean startsNotWith(String aString, String aStart) {
        return !startsWith(aString, aStart);
    }

    /**
     * Null-Safe Aufruf der Methode {@link String#startsWith(String)}
     */
    public static boolean startsWith(String aString, String aStart) {
        if (aString == null || aStart == null) {
            return false;
        }
        return aString.startsWith(aStart);
    }

    /**
     * @see #startsWith(String, String)
     */
    public static boolean startsWithIgnoreCase(String aString, String aStart) {
        if (aString == null || aStart == null) {
            return false;
        }
        return startsWith(aString.toLowerCase(), aStart.toLowerCase());
    }

    /**
     * Gibt den Teil des erstens {@link String}s zurück, der vor dem zweiten steht.
     */
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


    /**
     * Null-Safe Aufruf der Methode {@link String#indexOf(String)}
     */
    public static int indexOf(String aString, String aToFind) {
        if (isEmpty(aString) || isEmpty(aToFind)) {
            return -1;
        }
        return aString.indexOf(aToFind);
    }

    /**
     * Null-Safe Aufruf der Methode {@link Object#toString()}.
     * Wird der Methode <code>null</code> übergeben, wird {@link #NULL} zurückgegeben.
     */
    public static String toString(Object aObject) {
        if (aObject == null) {
            return NULL;
        }
        return aObject.toString();
    }
}
