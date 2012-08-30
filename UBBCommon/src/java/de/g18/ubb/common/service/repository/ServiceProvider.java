package de.g18.ubb.common.service.repository;

/**
 * Interface für Klassen die eine Verbindun zu Services auf einem Server herstellen können.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface ServiceProvider {

    /**
     * Gibt eine Instanz zurück, mit der auf die übergebene Service-Klasse zugegriffen werden kann.
     */
    <_Service> _Service lookup(Class<_Service> aServiceClass);
}
