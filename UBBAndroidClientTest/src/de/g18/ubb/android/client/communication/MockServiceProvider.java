package de.g18.ubb.android.client.communication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.g18.ubb.common.service.repository.ServiceProvider;
import de.g18.ubb.common.service.repository.ServiceRepository;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class MockServiceProvider implements ServiceProvider {

    private static final String MOCK_SERVICE_BASE_PACKAGE = "de.g18.ubb.android.client.mock.service.";
    private static final String MOCK_SERVICE_PREFIX = "Mock";
    private static final String MOCK_SERVICE_SUFFIX = "Impl";

    private static MockServiceProvider instance;
    private static boolean registered;

    /**
     * Registriert den {@link MockServiceProvider} als {@link ServiceProvider} für das {@link ServiceRepository}.
     */
    public static void register() {
        if (registered) {
            return;
        }

        ServiceRepository.setProvider(getInstance());
        registered = true;
    }

    private static MockServiceProvider getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    private static synchronized void createInstance() {
        if (instance != null) {
            return;
        }
        instance = new MockServiceProvider();
    }

    @Override
    public <_Service> _Service lookup(Class<_Service> aServiceClass) {
        Class<_Service> mockedServiceClass = getMockedServiceClass(aServiceClass);
        if (mockedServiceClass == null) {
            return createMockedProxy(aServiceClass);
        }
        try {
            return mockedServiceClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Can not create new mocked instance for " + aServiceClass.getName() + "!", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <_Service> Class<_Service> getMockedServiceClass(Class<_Service> aServiceClass) {
        try {
            return (Class<_Service>) Class.forName(MOCK_SERVICE_BASE_PACKAGE
                                                 + MOCK_SERVICE_PREFIX
                                                 + aServiceClass.getSimpleName()
                                                 + MOCK_SERVICE_SUFFIX);
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <_Service> _Service createMockedProxy(Class<_Service> aServiceClass) {
        return (_Service) Proxy.newProxyInstance(aServiceClass.getClassLoader(),
                                                 new Class[] {aServiceClass},
                                                 new MockService());
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class MockService implements InvocationHandler {

        @Override
        public Object invoke(Object aProxy, Method aMethod, Object[] aArgs) throws Throwable {
            if (boolean.class.isAssignableFrom(aMethod.getReturnType())) {
                return false;
            }
            if (Boolean.class.isAssignableFrom(aMethod.getReturnType())) {
                return Boolean.FALSE;
            }
            if (List.class.isAssignableFrom(aMethod.getReturnType())) {
                return new ArrayList<Object>();
            }
            if (Collection.class.isAssignableFrom(aMethod.getReturnType())) {
                return new ArrayList<Object>();
            }
            return null;
        }
    }
}
