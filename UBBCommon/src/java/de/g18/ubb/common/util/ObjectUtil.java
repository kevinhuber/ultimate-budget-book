package de.g18.ubb.common.util;

/**
 * Util-Klasse für alle Arten von Objekten.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class ObjectUtil {

    /**
     * Vergleicht die beiden übergebenen Objekte durch den aufruf von {@link #hashCode()}.
     * Sollten Objekte den selben hash code zurückgeben wird am ersten Objekt {@link #equals(Object)}
     * aufgerufen und das Ergebnis dieses Aufrufs zurückgegeben.
     *
     * @return true, falls die beiden Objekte den gleichen Hash-Code haben und {@link #equals(Object)} true zurückgiebt
     */
    public static boolean equals(Object aFirst, Object aSecond) {
        if (aFirst == aSecond) {
            return true;
        }
        if (aFirst == null && aSecond != null
             || aFirst != null && aSecond == null) {
            return false;
        }
        if (aFirst.hashCode() != aSecond.hashCode()) {
            return false;
        }
        return aFirst.equals(aSecond);
    }
}
