package de.g18.ubb.common.domain;

import org.junit.Test;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class CategoryTest extends AbstractEntityTest<Category> {

    @Override
    protected Category createEntity() {
        return new Category();
    }

    @Test
    public void testEquals() {
        // TODO implementieren!
    }
}
