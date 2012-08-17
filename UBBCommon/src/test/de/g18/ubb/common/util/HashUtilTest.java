package de.g18.ubb.common.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public class HashUtilTest {

    private static final char[] PASSWORD = "password".toCharArray();
    private static final byte[] PASSWORD_SALT = new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private static final char[] PASSWORD_HASH = "5f24560c5c92ff0cea5cc7cc489cc97f".toCharArray();


    @Test
    public void testToMD5() {
        Assert.assertArrayEquals(PASSWORD_HASH, HashUtil.toMD5(PASSWORD, PASSWORD_SALT));
        Assert.assertArrayEquals(PASSWORD_HASH, HashUtil.toMD5(new String(PASSWORD), PASSWORD_SALT));

        byte[] cutSalt = Arrays.copyOf(PASSWORD_SALT, PASSWORD_SALT.length - 1);
        Assert.assertFalse(Arrays.equals(PASSWORD_HASH, HashUtil.toMD5(PASSWORD, cutSalt)));
        Assert.assertFalse(Arrays.equals(PASSWORD_HASH, HashUtil.toMD5("blub", PASSWORD_SALT)));

        Assert.assertEquals(0, HashUtil.toMD5("", PASSWORD_SALT).length);
        Assert.assertEquals(0, HashUtil.toMD5((String) null, PASSWORD_SALT).length);
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
