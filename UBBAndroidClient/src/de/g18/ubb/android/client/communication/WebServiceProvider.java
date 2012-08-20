package de.g18.ubb.android.client.communication;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

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
        return ProxyFactory.create(aServiceClass, createServiceURL(aServiceClass), getClientExecutor());
    }

    private static String createServiceURL(Class<?> aServiceClass) {
        String serviceName = StringUtil.removeEnd(aServiceClass.getSimpleName(), "Remote");
        return "http://" + SERVER_ADDRESS + "/" + CONTEXT + "/" + serviceName;
    }

    private static ClientExecutor getClientExecutor() {
        HttpParams httpParams = new BasicHttpParams();
        int connection_Timeout = 5000;
        HttpConnectionParams.setConnectionTimeout(httpParams, connection_Timeout);
        HttpConnectionParams.setSoTimeout(httpParams, connection_Timeout);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(null, -1),
                new UsernamePasswordCredentials("admin","admin"));

        return new ApacheHttpClient4Executor(httpClient);
    }
}
