package de.g18.ubb.common.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public class UserEntityTest {

	private static final String USER_NAME = "test";
	private static final String USER_EMAIL = "email@localhorst.de";

	public User testUser;

	@Before
	public void setUp() {
		testUser = new User();
		testUser.setEmail(USER_EMAIL);
		testUser.setName(USER_NAME);
	}

	@Test
	public void testUserName() {
		testUser.setName(USER_NAME);
		testUser.getName();
		Assert.assertEquals("Der stimmt nicht überein", USER_NAME, testUser.getName());
	}

	@Test
	public void testUserEmail() {
		testUser.setEmail(USER_EMAIL);
		Assert.assertEquals(USER_EMAIL, testUser.getEmail());
	}
}
