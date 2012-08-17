package de.g18.ubb.common;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.g18.ubb.common.domain.UserEntityTest;
import de.g18.ubb.common.util.EntityUtilTest;
import de.g18.ubb.common.util.HashUtilTest;
import de.g18.ubb.common.util.ObjectUtilTest;
import de.g18.ubb.common.util.StringUtilTest;


/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@RunWith(Suite.class)
@SuiteClasses({
    EntityUtilTest.class,
    ObjectUtilTest.class,
    StringUtilTest.class,
    HashUtilTest.class,
    UserEntityTest.class,
})
public class AllTestSuite {
}
