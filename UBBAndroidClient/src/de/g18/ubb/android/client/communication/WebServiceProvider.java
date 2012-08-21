package de.g18.ubb.android.client.communication;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

import android.util.Log;
import de.g18.ubb.common.service.UserService;
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

    private String username;
    private String password;


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
        return ProxyFactory.create(aServiceClass, createServiceURL(aServiceClass), createAuthentificatedClientExecutor());
    }

    private static String createServiceURL(Class<?> aServiceClass) {
        String serviceName = StringUtil.removeEnd(aServiceClass.getSimpleName(), "Remote");
        return BASE_URL + serviceName;
    }

    private ClientExecutor createAuthentificatedClientExecutor() {
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            throw new IllegalStateException("ServiceProvider has not been initialized! "
                                          + "You may want to authentificate yourself first!");
        }

        HttpClient client = createAuthentificatedHttpClient(username, password);
        return new ApacheHttpClient4Executor(client);
    }

    public void resetAuthentificationData() {
        getInstance().setUsername(null);
        getInstance().setPassword(null);
    }

    private void setUsername(String aUsername) {
        username = aUsername;
    }

    private void setPassword(String aPassword) {
        password = aPassword;
    }

    public static boolean authentificate(String aUsername, String aPassword) {
        HttpClient httpClient = createAuthentificatedHttpClient(aUsername, aPassword);
        if (!isAuthentificatedClient(httpClient)) {
            getInstance().resetAuthentificationData();
            Log.e(WebServiceProvider.class.getSimpleName(), "Authentification for user '" + aUsername + "' failed.");
            return false;
        }

        getInstance().setUsername(aUsername);
        getInstance().setPassword(aPassword);
        return true;
    }

    private static boolean isAuthentificatedClient(HttpClient aClient) {
        HttpGet testRequest = new HttpGet(BASE_URL + UserService.AUTHENTIFICATION_TEST_PATH);
        int httpStatusCode;
        try {
            HttpResponse response = aClient.execute(testRequest);
            httpStatusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            Log.e(WebServiceProvider.class.getSimpleName(),
                  "Authentification failed due to unknown exception!", e);
            return false;
        }

        switch (httpStatusCode) {
            case HttpStatus.SC_OK:
                return true;

            case HttpStatus.SC_UNAUTHORIZED:
                return false;

            default:
                Log.e(WebServiceProvider.class.getSimpleName(),
                      "Authentification failed due to unknown status-code " + httpStatusCode + "!");
                return false;
        }
    }

    private static HttpClient createAuthentificatedHttpClient(String aUsername, String aPassword) {
        return createAuthentificatedHttpClient(new UsernamePasswordCredentials(aUsername, aPassword));
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
