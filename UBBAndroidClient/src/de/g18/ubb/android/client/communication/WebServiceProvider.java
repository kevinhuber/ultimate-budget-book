package de.g18.ubb.android.client.communication;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

import android.util.Log;
import de.g18.ubb.common.service.repository.ServiceProvider;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class WebServiceProvider implements ServiceProvider {

    public static final String SERVER_ADDRESS = "10.0.2.2:8080";
    public static final String CONTEXT = "UBBServer";
    public static final String BASE_URL = "http://" + SERVER_ADDRESS + "/" + CONTEXT + "/";

    private static WebServiceProvider instance;

    private ClientExecutor clientExecutor;


    /**
     * Registriert den {@link WebServiceProvider} als {@link ServiceProvider} für das {@link ServiceRepository}.
     */
    public static void register() {
        ServiceRepository.setProvider(getInstance());
    }

    private static WebServiceProvider getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    private static synchronized void createInstance() {
        if (instance != null) {
            return;
        }
        instance = new WebServiceProvider();
    }


    private WebServiceProvider() {
    }

    public <_Service> _Service lookup(Class<_Service> aServiceClass) {
        return ProxyFactory.create(aServiceClass, createServiceURL(aServiceClass), getClientExecutor());
    }

    private static String createServiceURL(Class<?> aServiceClass) {
        String serviceName = StringUtil.removeEnd(aServiceClass.getSimpleName(), "Remote");
        return BASE_URL + serviceName;
    }

    private void setClientExecutor(ClientExecutor aExecutor) {
        clientExecutor = aExecutor;
    }

    private ClientExecutor getClientExecutor() {
        if (clientExecutor == null) {
            throw new IllegalStateException("Executor has not been initialized! "
                                          + "You may want to authentificate yourself first!");
        }
        return clientExecutor;
    }

    public static boolean authentificate(String aEMail, String aPassword) {
        getInstance().setClientExecutor(createAuthentificatedClientExecutor(aEMail, aPassword));
        if (!ServiceRepository.getUserService().isAuthenticated()) {
            Log.e(WebServiceProvider.class.getSimpleName(), "Authentification for user '" + aEMail + "' failed.");
            getInstance().clientExecutor = null;
            return false;
        }
        return true;
    }

    private static ClientExecutor createAuthentificatedClientExecutor(String aEMail, String aPassword) {
        HttpClient httpClient = createAuthentificatedHttpClient(aEMail, aPassword);
        return new ApacheHttpClient4Executor(httpClient);
    }

    private static HttpClient createAuthentificatedHttpClient(String aEMail, String aPassword) {
        return createAuthentificatedHttpClient(new UsernamePasswordCredentials(aEMail, aPassword));
    }

    private static HttpClient createAuthentificatedHttpClient(UsernamePasswordCredentials aCredentials) {
        DefaultHttpClient httpClient = createDefaultHttpClient();
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(null, -1), aCredentials);
        return httpClient;
    }

    private static DefaultHttpClient createDefaultHttpClient() {
        HttpParams httpParams = new BasicHttpParams();
        int connection_Timeout = 5000;
        HttpConnectionParams.setConnectionTimeout(httpParams, connection_Timeout);
        HttpConnectionParams.setSoTimeout(httpParams, connection_Timeout);
        return new DefaultHttpClient(httpParams);
    }
}
