package de.g18.ubb.android.client.activities.main;

import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class MainActivityModel extends AbstractModel {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_EMAIL = "eMail";
    public static final String PROPERTY_PASSWORD = "password";
    public static final String PROPERTY_SERVER_ADDRESS = "serverAddress";
    public static final String PROPERTY_STAY_LOGGED_IN = "stayLoggedInChecked";

    private String eMail;
    private String password;
    private String serverAddress;
    private boolean stayLoggedInChecked;


    public MainActivityModel() {
    }

    public void setEMail(String aNewValue) {
        String oldValue = getEMail();
        eMail = aNewValue;
        fireChange(PROPERTY_EMAIL, oldValue, getEMail());
    }

    public String getEMail() {
        return eMail;
    }

    public void setPassword(String aNewValue) {
        String oldValue = getPassword();
        password = aNewValue;
        fireChange(PROPERTY_PASSWORD, oldValue, getPassword());
    }

    public String getPassword() {
        return password;
    }

    public void setServerAddress(String aNewValue) {
        String oldValue = getServerAddress();
        serverAddress = aNewValue;
        fireChange(PROPERTY_SERVER_ADDRESS, oldValue, getServerAddress());
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setStayLoggedInChecked(boolean aNewValue) {
        boolean oldValue = isStayLoggedInChecked();
        stayLoggedInChecked = aNewValue;
        fireChange(PROPERTY_STAY_LOGGED_IN, oldValue, isStayLoggedInChecked());
    }

    public boolean isStayLoggedInChecked() {
        return stayLoggedInChecked;
    }
}
