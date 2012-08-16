package de.g18.ubb.common.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.domain.Identifiable;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class EntityUtilTest {

    private Identifiable persistedEntity;

    @Before
    public void setUp() {
        persistedEntity = new Category();
        persistedEntity.setId(0L);
    }

    @Test
    public void testIsPersistent() {
        Assert.assertTrue(EntityUtil.isPersistent(persistedEntity));
        Assert.assertFalse(EntityUtil.isPersistent(new Category()));
        Assert.assertFalse(EntityUtil.isPersistent(null));
    }

    @Test
    public void testIsNotPersistent() {
        Assert.assertFalse(EntityUtil.isNotPersistent(persistedEntity));
        Assert.assertTrue(EntityUtil.isNotPersistent(new Category()));
        Assert.assertTrue(EntityUtil.isNotPersistent(null));
    }
}
