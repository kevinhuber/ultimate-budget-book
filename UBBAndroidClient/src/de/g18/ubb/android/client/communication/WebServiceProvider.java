package de.g18.ubb.android.client.communication;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutionException;

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
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

import android.os.AsyncTask;
import android.util.Log;
import de.g18.ubb.common.service.UserService;
import de.g18.ubb.common.service.repository.ServiceProvider;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * Implementierung des {@link ServiceProvider}-Interface, welche Services über einen WebService zu verfügung stellt.
 * Services-Calls werden in einen seperaten Thread ausgelagert, um den Main-Thread nicht zu überlasten.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class WebServiceProvider implements ServiceProvider {

    public static final String CONTEXT = "UBBServer";

    private static WebServiceProvider instance;

    private String serverAddress;
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

    public <_Service> _Service lookup(Class<_Service> aServiceClass) {
        _Service serviceProxy = ProxyFactory.create(aServiceClass,
                                                    createServiceURL(aServiceClass),
                                                    createClientExecutor());
        return dispatchService(aServiceClass, serviceProxy);
    }

    @SuppressWarnings({"unchecked"})
    private <_Service> _Service dispatchService(Class<_Service> aServiceClass, _Service aService) {
        return (_Service) Proxy.newProxyInstance(aService.getClass().getClassLoader(),
                                                 new Class[] {aServiceClass},
                                                 new WebServiceDispatcher(aService));
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
        return ClientRequest.getDefaultExecutor();
    }

    private boolean isUserAuthentificationDataSet() {
        return StringUtil.isNotEmpty(getUsername()) && StringUtil.isNotEmpty(getPassword());
    }

    private ClientExecutor createAuthentificatedClientExecutor() {
        HttpClient client = createAuthentificatedHttpClient(getUsername(), getPassword());
        return new ApacheHttpClient4Executor(client);
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

    /**
     * Versucht sich mit den übergebenen Daten am Web-Service zu authentifizieren und gibt true zurück, falls die
     * authentifizierung erfolgreich war.
     */
    public static boolean authentificate(String aUsername, String aPassword) {
        HttpClient client = createAuthentificatedHttpClient(aUsername, aPassword);
        if (!isAuthentificatedClient(client)) {
            getInstance().resetAuthentificationData();
            Log.e(WebServiceProvider.class.getSimpleName(), "Authentification for user '" + aUsername + "' failed.");
            return false;
        }

        getInstance().setAuthentificationData(aUsername, aPassword);
        return true;
    }

    private static boolean isAuthentificatedClient(HttpClient aClient) {
        AsyncTask<Void, Void, Boolean> dispatchedTask = new AsyncAuthentificationTest(aClient).execute();
        try {
            return ObjectUtil.equals(dispatchedTask.get(), Boolean.TRUE);
        } catch (InterruptedException e) {
            Log.e(WebServiceProvider.class.getSimpleName(), "Login failed due to interruption!", e);
        } catch (ExecutionException e) {
            Log.e(WebServiceProvider.class.getSimpleName(), "Login failed due to unknown error!", e);
        }
        return false;
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
        int connection_Timeout = 30000;
        HttpConnectionParams.setConnectionTimeout(httpParams, connection_Timeout);
        HttpConnectionParams.setSoTimeout(httpParams, connection_Timeout);
        return new DefaultHttpClient(httpParams);
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private static final class AsyncAuthentificationTest extends AsyncTask<Void, Void, Boolean> {

        private final HttpClient clientToCheck;


        public AsyncAuthentificationTest(HttpClient aClientToCheck) {
            clientToCheck = aClientToCheck;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpGet testRequest = new HttpGet(getBaseURL() + UserService.AUTHENTIFICATION_TEST_PATH);
            int httpStatusCode;
            try {
                HttpResponse response = clientToCheck.execute(testRequest);
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
    }
}
