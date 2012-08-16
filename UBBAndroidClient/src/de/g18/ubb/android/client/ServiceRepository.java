package de.g18.ubb.android.client;

import org.jboss.resteasy.client.ProxyFactory;

import de.g18.ubb.common.service.TestService;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class ServiceRepository {

    public static final String SERVER_ADDRESS = "10.0.2.2:8080";
    public static final String CONTEXT = "UBBServer";


    public static TestService getTestService() {
        return getService(TestService.class);
    }

    private static <_Service> _Service getService(Class<_Service> aService) {
        return ProxyFactory.create(aService, createServiceURL(aService));
    }

    private static String createServiceURL(Class<?> aService) {
        return "http://" + SERVER_ADDRESS + "/" + CONTEXT + "/" + aService.getSimpleName();
    }
}
