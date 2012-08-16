package de.g18.ubb.common.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class ObjectUtilTest {

    @Test
    public void testEqualsObjectObject() {
        String first = "TEST";
        String second = "Second";

        Assert.assertTrue(ObjectUtil.equals(first, first));
        Assert.assertFalse(ObjectUtil.equals(first, null));
        Assert.assertFalse(ObjectUtil.equals(null, first));
        Assert.assertTrue(ObjectUtil.equals(second, second));
        Assert.assertFalse(ObjectUtil.equals(second, null));
        Assert.assertFalse(ObjectUtil.equals(null, second));
        Assert.assertFalse(ObjectUtil.equals(first, second));
        Assert.assertFalse(ObjectUtil.equals(second, first));
        Assert.assertTrue(ObjectUtil.equals(null, null));
    }
}
