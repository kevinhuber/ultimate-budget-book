package de.g18.ubb.common.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public class HashUtilTest {

    private static final String PASSWORD = "password";
    private static final byte[] PASSWORD_SALT = new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private static final String PASSWORD_HASH = "5f24560c5c92ff0cea5cc7cc489cc97f";


    @Test
    public void testToMD5() {
        Assert.assertEquals(PASSWORD_HASH, HashUtil.toMD5(PASSWORD, PASSWORD_SALT));
        Assert.assertEquals(PASSWORD_HASH, HashUtil.toMD5(new String(PASSWORD), PASSWORD_SALT));

        byte[] cutSalt = Arrays.copyOf(PASSWORD_SALT, PASSWORD_SALT.length - 1);
        Assert.assertFalse(ObjectUtil.equals(PASSWORD_HASH, HashUtil.toMD5(PASSWORD, cutSalt)));
        Assert.assertFalse(ObjectUtil.equals(PASSWORD_HASH, HashUtil.toMD5("blub", PASSWORD_SALT)));

        Assert.assertEquals(StringUtil.EMPTY, HashUtil.toMD5("", PASSWORD_SALT));
        Assert.assertEquals(StringUtil.EMPTY, HashUtil.toMD5((String) null, PASSWORD_SALT));
    }

    @Test
    public void testGenerateSalt() {
        byte[] generatedSalt = HashUtil.generateSalt();
        Assert.assertEquals(HashUtil.DEFAULT_SALT_LENGTH, generatedSalt.length);

        for (int i = 0; i < 100; i++) {
            Assert.assertFalse(Arrays.equals(generatedSalt, HashUtil.generateSalt()));
        }
    }
}
