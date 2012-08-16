package de.g18.ubb.client.communication;

import java.util.Hashtable;

import javax.ejb.Remote;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import de.g18.ubb.common.util.StringUtil;


/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class ContextWrapper implements Context {

    private static final String SERVICE_IMPLEMENTATION_SUFFIX = "Impl";

    /**
     * Name of the deployed application
     */
    public static final String APPLICATION_NAME = "UBBServerEAR";

    /**
     * Name of the .jar file, that contains all EJB's
     */
    public static final String MODULE_NAME = "UBBServer";

    private static ContextWrapper instance;


    public static ContextWrapper getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    private static synchronized void createInstance() {
        if (instance == null) {
            try {
                instance = new ContextWrapper();
            } catch (NamingException e) {
                throw new IllegalStateException("Can not create ContextWrapper!", e);
            }
        }
    }


    private final Context delegate;


    public ContextWrapper() throws NamingException {
        Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        delegate = new InitialContext(jndiProperties);
    }

    @Override
    public Object addToEnvironment(String aPropertyName, Object aPropertyValue) throws NamingException {
        return delegate.addToEnvironment(aPropertyName, aPropertyValue);
    }

    @Override
    public void bind(Name aName, Object aObject) throws NamingException {
        delegate.bind(aName, aObject);
    }

    @Override
    public void bind(String aName, Object aObject) throws NamingException {
        delegate.bind(aName, aObject);
    }

    @Override
    public void close() throws NamingException {
        delegate.close();
    }

    @Override
    public Name composeName(Name aName, Name aPrefix) throws NamingException {
        return delegate.composeName(aName, aPrefix);
    }

    @Override
    public String composeName(String aName, String aPrefix) throws NamingException {
        return delegate.composeName(aName, aPrefix);
    }

    @Override
    public Context createSubcontext(Name aName) throws NamingException {
        return delegate.createSubcontext(aName);
    }

    @Override
    public Context createSubcontext(String aName) throws NamingException {
        return delegate.createSubcontext(aName);
    }

    @Override
    public void destroySubcontext(Name aName) throws NamingException {
        delegate.destroySubcontext(aName);
    }

    @Override
    public void destroySubcontext(String aName) throws NamingException {
        delegate.destroySubcontext(aName);
    }

    @Override
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return delegate.getEnvironment();
    }

    @Override
    public String getNameInNamespace() throws NamingException {
        return delegate.getNameInNamespace();
    }

    @Override
    public NameParser getNameParser(Name aName) throws NamingException {
        return delegate.getNameParser(aName);
    }

    @Override
    public NameParser getNameParser(String aName) throws NamingException {
        return delegate.getNameParser(aName);
    }

    @Override
    public NamingEnumeration<NameClassPair> list(Name aName) throws NamingException {
        return delegate.list(aName);
    }

    @Override
    public NamingEnumeration<NameClassPair> list(String aName) throws NamingException {
        return delegate.list(aName);
    }

    @Override
    public NamingEnumeration<Binding> listBindings(Name aName) throws NamingException {
        return delegate.listBindings(aName);
    }

    @Override
    public NamingEnumeration<Binding> listBindings(String aName) throws NamingException {
        return delegate.listBindings(aName);
    }

    @SuppressWarnings("unchecked")
    public <_Service> _Service lookup(Class<_Service> aServiceClass) throws NamingException {
        if (!aServiceClass.isAnnotationPresent(Remote.class)) {
            throw new IllegalArgumentException(aServiceClass.getName() + " is not a Remote-Service-Interface!");
        }

        // Simple class name of the EJB-implementation
        String ejbName = StringUtil.removeEnd(aServiceClass.getSimpleName(), "Remote") + SERVICE_IMPLEMENTATION_SUFFIX;

        return (_Service) delegate.lookup("ejb:" + APPLICATION_NAME + "/"
                                        + MODULE_NAME + "//" + ejbName + "!" + aServiceClass.getName());
    }

    @Override
    public Object lookup(Name aName) throws NamingException {
        return delegate.lookup(aName);
    }

    @Override
    public Object lookup(String aName) throws NamingException {
        return delegate.lookup(aName);
    }

    @Override
    public Object lookupLink(Name aName) throws NamingException {
        return delegate.lookupLink(aName);
    }

    @Override
    public Object lookupLink(String aName) throws NamingException {
        return delegate.lookupLink(aName);
    }

    @Override
    public void rebind(Name aName, Object aObject) throws NamingException {
        delegate.rebind(aName, aObject);
    }

    @Override
    public void rebind(String aName, Object aObject) throws NamingException {
        delegate.rebind(aName, aObject);
    }

    @Override
    public Object removeFromEnvironment(String aPropertyName) throws NamingException {
        return delegate.removeFromEnvironment(aPropertyName);
    }

    @Override
    public void rename(Name aOldName, Name aNewName) throws NamingException {
        delegate.rename(aOldName, aNewName);
    }

    @Override
    public void rename(String aOldName, String aNewName) throws NamingException {
        delegate.rename(aOldName, aNewName);
    }

    @Override
    public void unbind(Name aName) throws NamingException {
        delegate.unbind(aName);
    }

    @Override
    public void unbind(String aName) throws NamingException {
        delegate.unbind(aName);
    }
}
