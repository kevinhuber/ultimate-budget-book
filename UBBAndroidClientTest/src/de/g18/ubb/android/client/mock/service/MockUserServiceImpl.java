package de.g18.ubb.android.client.mock.service;

import de.g18.ubb.common.service.UserService;
import de.g18.ubb.common.util.ObjectUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class MockUserServiceImpl implements UserService {

    public static final String REGISTERED_USER_EMAIL = "already@register.ed";


    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public boolean existsUserWithEMail(String aEMail) {
        return ObjectUtil.equals(REGISTERED_USER_EMAIL, aEMail);
    }

    @Override
    public boolean register(String aEMail, String aUsername, String aPassword) {
        return true;
    }
}
