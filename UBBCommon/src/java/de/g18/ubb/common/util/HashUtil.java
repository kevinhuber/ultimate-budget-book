package de.g18.ubb.common.util;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class HashUtil {

    public static final int DEFAULT_SALT_LENGTH = 12;


    public static String toMD5(String aPassword, byte[] aSalt) {
        if (StringUtil.isEmpty(aPassword)) {
            return StringUtil.EMPTY;
        }
        return toMD5(aPassword.toCharArray(), aSalt);
    }

    public static String toMD5(char[] aPassword, byte[] aSalt) {
        if (aPassword == null) {
            return StringUtil.EMPTY;
        }

    	try {
    		ByteBuffer passwdBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(aPassword));
    		byte[] passwdBytes = passwdBuffer.array();
    		MessageDigest mdEnc = MessageDigest.getInstance("MD5");
    		mdEnc.update(aSalt, 0, aSalt.length);
    		mdEnc.update(passwdBytes, 0, aPassword.length);
    		return new BigInteger(1, mdEnc.digest()).toString(16);
    	} catch (NoSuchAlgorithmException e) {
    	    throw new IllegalStateException("MD5 is not available!", e);
    	}
    }

    public static byte[] generateSalt() {
        return generateSalt(DEFAULT_SALT_LENGTH);
    }

    public static byte[] generateSalt(int aSize) {
    	byte[] generatedSalt = new byte[aSize];
    	SecureRandom random = new SecureRandom();
    	random.nextBytes(generatedSalt);
    	return generatedSalt;
    }
}
