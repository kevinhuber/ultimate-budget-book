package de.g18.ubb.common.service;

import org.jboss.resteasy.client.ClientResponseFailure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.HashUtil;
import de.g18.ubb.integrationtests.communication.WebServiceProvider;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class UserServiceTest extends AbstractServiceTest<UserService> {

    @Before
    public void setUp() throws Exception {
        loginAsTestUser();
    }

    @Test
    public void testIsAuthenticated() {
        Assert.assertTrue(getService().isAuthenticated());
    }

    @Test(expected = ClientResponseFailure.class)
    public void testIsAuthenticatedFalse() {
        WebServiceProvider.resetUserData();
        Assert.assertFalse(getService().isAuthenticated());
    }

    @Test
    public void testExistsUserWithEMail() {
        Assert.assertFalse(getService().existsUserWithEMail("invalidEMailAddress.!"));
        Assert.assertTrue(getService().existsUserWithEMail(TEST_USER_EMAIL));
    }

    @Test
    public void testRegister() {
        String userEMail = generateRandomUserEMail();
        while (getService().existsUserWithEMail(userEMail)) {
            userEMail = generateRandomUserEMail();
        }
        String randomPassword = generateRandomPassword();

        boolean registrationSuccessfull = getService().register(userEMail, "IntegrationTestUser", randomPassword);
        Assert.assertTrue(registrationSuccessfull);
        Assert.assertTrue(WebServiceProvider.authentificate(userEMail, randomPassword));
    }

    private String generateRandomUserEMail() {
        return new String(HashUtil.generateSalt(User.EMAIL_LENGTH - TEST_USER_EMAIL_SUFFIX.length()));
    }

    private String generateRandomPassword() {
        return new String(HashUtil.generateSalt(User.PASSWORD_LENGTH));
    }

    @Override
    protected UserService getService() {
        return ServiceRepository.getUserService();
    }

}
