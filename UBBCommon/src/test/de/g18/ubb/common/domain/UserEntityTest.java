package de.g18.ubb.common.domain;

import org.junit.Test;

/**
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public class UserEntityTest extends AbstractEntityTest<User> {

    @Override
    protected User createEntity() {
        return new User();
    }

    @Test
    public void testEquals() {
    }
}
