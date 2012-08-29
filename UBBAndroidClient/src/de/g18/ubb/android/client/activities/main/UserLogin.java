package de.g18.ubb.android.client.activities.main;

import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class UserLogin extends AbstractModel {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_EMAIL = "eMail";
    public static final String PROPERTY_PASSWORD = "password";

    private String eMail;
    private String password;


    public UserLogin() {
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
}
