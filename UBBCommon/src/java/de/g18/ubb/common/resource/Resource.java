package de.g18.ubb.common.resource;

/**
 * Interface für Resourcen zum auslagern von Strings, etc...
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface Resource {

    /**
     * Formatiert die {@link Resource} mit den angegebenen Parametern und gibt diesen zurück.
     */
    String formatted(Object... aParameters);
}
