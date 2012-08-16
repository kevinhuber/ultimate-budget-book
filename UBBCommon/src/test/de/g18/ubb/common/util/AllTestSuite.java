package de.g18.ubb.common.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@RunWith(Suite.class)
@SuiteClasses({
    EntityUtilTest.class,
    ObjectUtilTest.class,
    StringUtilTest.class
})
public class AllTestSuite {
}
