package de.g18.ubb.common.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.util.HashUtil;

/**
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public class UserEntityTest {

	private static final String USER_NAME = "test";
	private static final String USER_EMAIL = "email@localhorst.de";
	private static final String USER_PASSWORD = "dsdsdfsd";
    private static final String USER_PASSWORD2 = "password";

	public User testUser;

	@Before
	public void setUp() {
		testUser = new User();
		testUser.setEmail(USER_EMAIL);
		testUser.setName(USER_NAME);
		testUser.setPassword(USER_PASSWORD);
	}

	@Test
	public void testUserName() {
		testUser.setName(USER_NAME);
		testUser.getName();
		Assert.assertEquals("Der stimmt nicht überein", USER_NAME, testUser.getName());
	}

	@Test
	public void testUserPassword() {
		char[] charPasswd = HashUtil.toMD5(USER_PASSWORD2, testUser.getSalt());
		String expectedPassword = new String(charPasswd);
		testUser.setPassword(USER_PASSWORD2);
		String recievedPassword = new String(testUser.getPassword());
		Assert.assertEquals(expectedPassword, recievedPassword);
	}

	@Test
	public void testUserEmail() {
		testUser.setEmail(USER_EMAIL);
		Assert.assertEquals(USER_EMAIL, testUser.getEmail());
	}
}
