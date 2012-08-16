package de.g18.ubb.client.communication;

import javax.naming.NamingException;

import de.g18.ubb.common.service.remote.TestServiceRemote;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class ServiceRepository {

    public static TestServiceRemote getTestService() {
        return lookup(TestServiceRemote.class);
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    private static <_Service> _Service lookup(Class<_Service> aServiceClass) {
        try {
            return ContextWrapper.getInstance().lookup(aServiceClass);
        } catch (NamingException e) {
            throw new IllegalStateException("Unable to lookup EJB for " + aServiceClass.getName(), e);
        }
    }
}
