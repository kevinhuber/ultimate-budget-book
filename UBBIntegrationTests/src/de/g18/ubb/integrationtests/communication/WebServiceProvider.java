package de.g18.ubb.integrationtests.communication;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;

import de.g18.ubb.common.service.UserService;
import de.g18.ubb.common.service.repository.ServiceProvider;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class WebServiceProvider implements ServiceProvider {

    public static final String CONTEXT = "UBBServer";

    private static WebServiceProvider instance;
    private static boolean registered;

    private String serverAddress;
    private String username;
    private String password;


    /**
     * Registriert den {@link WebServiceProvider} als {@link ServiceProvider} für das {@link ServiceRepository}.
     */
    public static void register() {
        if (registered) {
            return;
        }
        registerThreadSafe();
    }

    private static synchronized void registerThreadSafe() {
        if (registered) {
            return;
        }

        ServiceRepository.setProvider(getInstance());
        registered = true;
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

    @Override
    public <_Service> _Service lookup(Class<_Service> aServiceClass) {
        return ProxyFactory.create(aServiceClass,
                                   createServiceURL(aServiceClass),
                                   createClientExecutor());
    }

    private static String createServiceURL(Class<?> aServiceClass) {
        String serviceName = StringUtil.removeEnd(aServiceClass.getSimpleName(), "Remote");
        return getBaseURL() + serviceName;
    }

    public static String getBaseURL() {
        return "http://" + getInstance().getServerAddress() + "/" + CONTEXT + "/";
    }

    private ClientExecutor createClientExecutor() {
        if (isUserAuthentificationDataSet()) {
            return createAuthentificatedClientExecutor();
        }
        return new ApacheHttpClientExecutor(createDefaultHttpClient());
    }

    private boolean isUserAuthentificationDataSet() {
        return StringUtil.isNotEmpty(getUsername()) && StringUtil.isNotEmpty(getPassword());
    }

    private ClientExecutor createAuthentificatedClientExecutor() {
        HttpClient client = createAuthentificatedHttpClient(getUsername(), getPassword());
        return new ApacheHttpClientExecutor(client);
    }

    public void resetAuthentificationData() {
        setAuthentificationData(null, null);
    }

    public void setAuthentificationData(String aUsername, String aPassword) {
        setUsername(aUsername);
        setPassword(aPassword);
    }

    private void setUsername(String aUsername) {
        username = aUsername;
    }

    public static String getUsername() {
    	return getInstance().username;
    }

    private void setPassword(String aPassword) {
        password = aPassword;
    }

    private String getPassword() {
    	return password;
    }

    private String getServerAddress() {
        return serverAddress;
    }

    public static void setServerAddress(String aNewServerAddress) {
        getInstance().serverAddress = aNewServerAddress;
    }

    public static void resetUserData() {
        getInstance().resetAuthentificationData();
    }

    public static boolean authentificate(String aUsername, String aPassword) {
        HttpClient client = createAuthentificatedHttpClient(aUsername, aPassword);
        if (!isAuthentificatedClient(client)) {
            getInstance().resetAuthentificationData();
            System.err.println("Authentification for user '" + aUsername + "' failed.");
            return false;
        }

        getInstance().setAuthentificationData(aUsername, aPassword);
        return true;
    }

    private static boolean isAuthentificatedClient(HttpClient aClient) {
        HttpMethod testRequest = new GetMethod(getBaseURL() + UserService.AUTHENTIFICATION_TEST_PATH);
        int httpStatusCode;
        try {
            httpStatusCode = aClient.executeMethod(testRequest);
        } catch (Exception e) {
            System.err.println("Authentification failed due to unknown exception! " + e.getLocalizedMessage());
            return false;
        }

        switch (httpStatusCode) {
            case HttpStatus.SC_OK:
                return true;

            case HttpStatus.SC_UNAUTHORIZED:
                return false;

            default:
                System.err.println("Authentification failed due to unknown status-code " + httpStatusCode + "!");
                return false;
        }
    }

    private static HttpClient createAuthentificatedHttpClient(String aUsername, String aPassword) {
        return createAuthentificatedHttpClient(new UsernamePasswordCredentials(aUsername, aPassword));
    }

    private static HttpClient createAuthentificatedHttpClient(UsernamePasswordCredentials aCredentials) {
        HttpClient httpClient = createDefaultHttpClient();
        httpClient.getState().setCredentials(new AuthScope(null, -1), aCredentials);
        return httpClient;
    }

    private static HttpClient createDefaultHttpClient() {
        HttpClientParams httpParams = new HttpClientParams();
        int connection_Timeout = 10000;
        httpParams.setConnectionManagerTimeout(connection_Timeout);
        httpParams.setSoTimeout(connection_Timeout);

        HttpClient client = new HttpClient();
        client.setParams(httpParams);
        return client;
    }
}
