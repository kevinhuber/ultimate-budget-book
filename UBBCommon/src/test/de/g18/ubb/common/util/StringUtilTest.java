package de.g18.ubb.common.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class StringUtilTest {

    @Test
    public void testRemoveEnd() {
        String testString = "TestString";
        String firstSubString = "Test";
        String secondSubString = "String";

        Assert.assertEquals(firstSubString, StringUtil.removeEnd(testString, secondSubString));
        Assert.assertEquals(testString, StringUtil.removeEnd(testString, "asdd"));
        Assert.assertEquals(testString, StringUtil.removeEnd(testString, ""));
        Assert.assertEquals(testString, StringUtil.removeEnd(testString, null));
        Assert.assertEquals(null, StringUtil.removeEnd(null, null));
        Assert.assertEquals(null, StringUtil.removeEnd(null, ""));
        Assert.assertEquals(null, StringUtil.removeEnd(null, testString));
    }

    @Test
    public void testIsNotEmpty() {
        Assert.assertFalse(StringUtil.isNotEmpty(""));
        Assert.assertFalse(StringUtil.isNotEmpty(null));
        Assert.assertTrue(StringUtil.isNotEmpty("A"));
    }

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(StringUtil.isEmpty(""));
        Assert.assertTrue(StringUtil.isEmpty(null));
        Assert.assertFalse(StringUtil.isEmpty("A"));
    }

    @Test
    public void testEndsNotWith() {
        String testString = "TestString";

        Assert.assertFalse(StringUtil.endsNotWith(testString, "String"));
        Assert.assertTrue(StringUtil.endsNotWith(testString, "Strings"));
        Assert.assertFalse(StringUtil.endsNotWith(testString, ""));
        Assert.assertTrue(StringUtil.endsNotWith(testString, null));
        Assert.assertTrue(StringUtil.endsNotWith(null, "Strings"));
        Assert.assertTrue(StringUtil.endsNotWith("", "Strings"));
    }

    @Test
    public void testEndsWith() {
        String testString = "TestString";

        Assert.assertTrue(StringUtil.endsWith(testString, "String"));
        Assert.assertFalse(StringUtil.endsWith(testString, "Strings"));
        Assert.assertTrue(StringUtil.endsWith(testString, ""));
        Assert.assertFalse(StringUtil.endsWith(testString, null));
        Assert.assertFalse(StringUtil.endsWith(null, "Strings"));
        Assert.assertFalse(StringUtil.endsWith("", "Strings"));
    }

    @Test
    public void testEndsWithIgnoreCase() {
        String testString = "TestString";

        Assert.assertTrue(StringUtil.endsWithIgnoreCase(testString, "StrIng"));
        Assert.assertFalse(StringUtil.endsWithIgnoreCase(testString, "StrIngs"));
        Assert.assertTrue(StringUtil.endsWithIgnoreCase(testString, ""));
        Assert.assertFalse(StringUtil.endsWithIgnoreCase(testString, null));
        Assert.assertFalse(StringUtil.endsWithIgnoreCase(null, "StrIngs"));
        Assert.assertFalse(StringUtil.endsWithIgnoreCase("", "StrIngs"));
    }

    @Test
    public void testStartsNotWith() {
        String testString = "TestString";

        Assert.assertFalse(StringUtil.startsNotWith(testString, "Test"));
        Assert.assertTrue(StringUtil.startsNotWith(testString, "Tests"));
        Assert.assertFalse(StringUtil.startsNotWith(testString, ""));
        Assert.assertTrue(StringUtil.startsNotWith(testString, null));
        Assert.assertTrue(StringUtil.startsNotWith(null, "Tests"));
        Assert.assertTrue(StringUtil.startsNotWith("", "Tests"));
    }

    @Test
    public void testStartsWith() {
        String testString = "TestString";

        Assert.assertTrue(StringUtil.startsWith(testString, "Test"));
        Assert.assertFalse(StringUtil.startsWith(testString, "Tests"));
        Assert.assertTrue(StringUtil.startsWith(testString, ""));
        Assert.assertFalse(StringUtil.startsWith(testString, null));
        Assert.assertFalse(StringUtil.startsWith(null, "Tests"));
        Assert.assertFalse(StringUtil.startsWith("", "Tests"));
    }

    @Test
    public void testSubStringBefore() {
        String testString = "TestString";
        String firstSubString = "Test";
        String secondSubString = "String";

        Assert.assertEquals(firstSubString, StringUtil.subStringBefore(testString, secondSubString));
        Assert.assertEquals(testString, StringUtil.subStringBefore(testString, "babababab"));
        Assert.assertEquals(testString, StringUtil.subStringBefore(testString, ""));
        Assert.assertEquals(testString, StringUtil.subStringBefore(testString, null));
        Assert.assertEquals(null, StringUtil.subStringBefore(null, null));
        Assert.assertEquals(null, StringUtil.subStringBefore(null, ""));
        Assert.assertEquals(null, StringUtil.subStringBefore(null, testString));
    }

    @Test
    public void testIndexOf() {
        String testString = "TestString";
        Assert.assertEquals(2, StringUtil.indexOf(testString, "s"));
        Assert.assertEquals(4, StringUtil.indexOf(testString, "String"));
        Assert.assertEquals(-1, StringUtil.indexOf(testString, "Strings"));
        Assert.assertEquals(-1, StringUtil.indexOf(testString, ""));
        Assert.assertEquals(-1, StringUtil.indexOf(testString, null));
    }

    @Test
    public void testToString() {
        String testString = "TestString";
        Assert.assertEquals(testString, StringUtil.toString(testString));
        Assert.assertEquals(StringUtil.NULL, StringUtil.toString(null));

    }
}
