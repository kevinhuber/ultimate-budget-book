package de.g18.ubb.android.client.activities.register;

import de.g18.ubb.common.domain.AbstractModel;

/**
 * {@link AbstractModel} für die {@link RegisterActivity}.
 *
 * @author Sebastian.Kopatz
 */
public final class RegisterModel extends AbstractModel {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_USERNAME = "username";
    public static final String PROPERTY_EMAIL = "eMail";
    public static final String PROPERTY_PASSWORD = "password";
    public static final String PROPERTY_PASSWORD_CHECK = "passwordCheck";

    private String username;
    private String eMail;
    private String password;
    private String passwordCheck;


    public RegisterModel() {
    }

    public void setUsername(String aNewValue) {
        String oldValue = getUsername();
        username = aNewValue;
        fireChange(PROPERTY_USERNAME, oldValue, getUsername());
    }

    public String getUsername() {
        return username;
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

    public void setPasswordCheck(String aNewValue) {
        String oldValue = getPasswordCheck();
        passwordCheck = aNewValue;
        fireChange(PROPERTY_PASSWORD_CHECK, oldValue, getPasswordCheck());
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }
}
