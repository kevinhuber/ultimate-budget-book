package de.g18.ubb.common.service.repository;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface ServiceProvider {

    <_Service> _Service lookup(Class<_Service> aServiceClass);
}
