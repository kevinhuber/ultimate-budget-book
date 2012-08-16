package de.g18.ubb.android.client.communication;

import org.jboss.resteasy.client.ProxyFactory;

import de.g18.ubb.common.service.repository.ServiceProvider;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class WebServiceProvider implements ServiceProvider {

    public static final String SERVER_ADDRESS = "10.0.2.2:8080";
    public static final String CONTEXT = "UBBServer";


    public WebServiceProvider() {
    }

    public <_Service> _Service lookup(Class<_Service> aServiceClass) {
        return ProxyFactory.create(aServiceClass, createServiceURL(aServiceClass));
    }

    private static String createServiceURL(Class<?> aServiceClass) {
        String serviceName = StringUtil.removeEnd(aServiceClass.getSimpleName(), "Remote");
        return "http://" + SERVER_ADDRESS + "/" + CONTEXT + "/" + serviceName;
    }
}
