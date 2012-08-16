package de.g18.ubb.common.domain;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
@Entity
@Table(name = "DBUser")
public final class User extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_EMAIL = "email";
	public static final String PROPERTY_PASSWORD = "password";
	public static final String PROPERTY_SALT = "salt";
	public static final String PROPERTY_SESSION = "session";

	private String name;

	private char[] email;

	private char[] password;

	private byte[] salt = new byte[12];

	private char[] session;

	public User() {
	}

	public void setName(String aNewValue) {
		String oldValue = getName();
		name = aNewValue;
		fireChange(PROPERTY_NAME, oldValue, getName());
	}

	@Column(name = "name", length = 32)
	public String getName() {
		return name;
	}

	public void setEmail(char[] aNewValue) {
		char[] oldValue = getEmail();
		email = aNewValue;
		fireChange(PROPERTY_EMAIL, oldValue, getEmail());
	}

	@Column(name = "email", length = 32, columnDefinition = "VARCHAR(32)")
	public char[] getEmail() {
		return email;
	}

	public void setPassword(char[] aNewValue) {
		char[] oldValue = getPassword();
		password = hashPassword(aNewValue, this.salt);
		fireChange(PROPERTY_PASSWORD, oldValue, getPassword());
	}

	@Column(name = "password", length = 32, columnDefinition = "VARCHAR(32)")
	public char[] getPassword() {
		return password;
	}

	public char[] hashPassword(char[] password, byte[] salt) {
		char[] encoded = null;
		try {
			ByteBuffer passwdBuffer = Charset.defaultCharset().encode(
					CharBuffer.wrap(password));
			byte[] passwdBytes = passwdBuffer.array();
			MessageDigest mdEnc = MessageDigest.getInstance("MD5");
			mdEnc.update(salt, 0, salt.length);
			mdEnc.update(passwdBytes, 0, password.length);
			encoded = new BigInteger(1, mdEnc.digest()).toString(16)
					.toCharArray();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
		}

		return encoded;
	}

	public void setSalt(byte[] aNewValue) {
		byte[] oldValue = getSalt();
		salt = aNewValue;
		fireChange(PROPERTY_SALT, oldValue, getSalt());
	}

	@Column(name = "salt", length = 12, columnDefinition = "BYTE(12)")
	public byte[] getSalt() {
		if (salt == null) {
			salt = generateSalt();
		}
		return salt;
	}

	private byte[] generateSalt() {
		byte[] generatedSalt = new byte[0];
		SecureRandom random = new SecureRandom();
		random.nextBytes(generatedSalt);
		return generatedSalt;
	}

	public void setSession(char[] aNewValue) {
		char[] oldValue = getSession();
		session = aNewValue;
		fireChange(PROPERTY_SESSION, oldValue, getSession());
	}

	@Column(name = "session", length = 32, columnDefinition = "VARCHAR(32)")
	public char[] getSession() {
		return session;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if ((this.name == null) ? (other.name != null) : !this.name
				.equals(other.name)) {
			return false;
		}
		return true;
	}

}


