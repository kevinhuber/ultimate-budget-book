package de.g18.ubb.common.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.g18.ubb.common.domain.User;

/**
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public class UserEntityTest {

	private static final String USER_NAME = "test";
	private static final String USER_EMAIL = "email@localhorst.de";

	public User testUser;
	public String password = "dsdsdfsd";

	@Before
	public void setUp() {
		testUser = new User();
		testUser.setEmail(USER_EMAIL.toCharArray());
		testUser.setName(USER_NAME);
		testUser.setPassword(password.toCharArray());
	}

	@Test
	public void testHashPassword() {

		char[] charPasswd = testUser.hashPassword(password.toCharArray(),
				testUser.getSalt());
		String expectedPassword = new String(charPasswd);
		String recievedPassword = new String(testUser.getPassword());

		Assert.assertEquals("Die Passwörter stimmen nicht über ein",
				expectedPassword, recievedPassword);
	}

	@Test
	public void testUserName() {
		testUser.setName(USER_NAME);
		testUser.getName();
		Assert.assertEquals("Der stimmt nicht überein", USER_NAME,
				testUser.getName());
	}

	@Test
	public void testUserPassword() {
		char[] charPasswd = testUser.hashPassword("password".toCharArray(),
				testUser.getSalt());
		String expectedPassword = new String(charPasswd);
		testUser.setPassword("password".toCharArray());
		String recievedPassword = new String(testUser.getPassword());
		Assert.assertEquals(expectedPassword, recievedPassword);
	}

	@Test
	public void testUserEmail() {
		String expectedEmail = new String(USER_EMAIL.toCharArray());
		testUser.setEmail(USER_EMAIL.toCharArray());
		String recievedEmail = new String(testUser.getEmail());
		Assert.assertEquals("", expectedEmail, recievedEmail);
	}
}
