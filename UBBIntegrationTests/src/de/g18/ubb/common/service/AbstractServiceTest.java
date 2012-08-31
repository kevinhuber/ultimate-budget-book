package de.g18.ubb.common.service;

import org.junit.BeforeClass;

import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.integrationtests.communication.WebServiceProvider;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractServiceTest<_Service> {

    protected static final String TEST_USER_EMAIL_SUFFIX = "@ubb.de";
    protected static final String TEST_USER_EMAIL = "integrationtest" + TEST_USER_EMAIL_SUFFIX;

    private static final String TEST_USER_PASSWORD = "TestUserPassw0rd...";


    @BeforeClass
    public static final void setUpBeforeClass() throws Exception {
        WebServiceProvider.register();
        WebServiceProvider.setServerAddress("127.0.0.1:8080");
    }

    protected final void loginAsTestUser() {
        if (!ServiceRepository.getUserService().existsUserWithEMail(TEST_USER_EMAIL)) {
            ServiceRepository.getUserService().register(TEST_USER_EMAIL, TEST_USER_EMAIL, TEST_USER_PASSWORD);
        }
        WebServiceProvider.authentificate(TEST_USER_EMAIL, TEST_USER_PASSWORD);
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    protected abstract _Service getService();
}
