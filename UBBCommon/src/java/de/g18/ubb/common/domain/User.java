package de.g18.ubb.common.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.g18.ubb.common.util.HashUtil;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;

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
	private String email;
	private String password;
	private byte[] salt;
	private String session;


	public User() {
	}

	public void setName(String aNewValue) {
		String oldValue = getName();
		name = aNewValue;
		fireChange(PROPERTY_NAME, oldValue, getName());
	}

	@Column(length = 32)
	public String getName() {
		return name;
	}

	public void setEmail(String aNewValue) {
	    String oldValue = getEmail();
		email = aNewValue;
		fireChange(PROPERTY_EMAIL, oldValue, getEmail());
	}

	@Column(length = 32, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setPassword(String aNewValue) {
	    String oldValue = getPassword();
		password = new String(HashUtil.toMD5(aNewValue, getSalt()));
		fireChange(PROPERTY_PASSWORD, oldValue, getPassword());
	}

	@Column(length = 32)
	public String getPassword() {
		return password;
	}

	public void setSalt(byte[] aNewValue) {
		byte[] oldValue = getSalt();
		salt = aNewValue;
		fireChange(PROPERTY_SALT, oldValue, getSalt());
	}

	@Column(length = HashUtil.DEFAULT_SALT_LENGTH)
	public byte[] getSalt() {
		if (salt == null) {
			salt = HashUtil.generateSalt();
		}
		return salt;
	}

	public void setSession(String aNewValue) {
	    String oldValue = getSession();
		session = aNewValue;
		fireChange(PROPERTY_SESSION, oldValue, getSession());
	}

	@Column(length = 32)
	public String getSession() {
		return session;
	}

	// -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

	@Override
	public int hashCode() {
	    int hashCode = super.hashCode();
	    if (getEmail() != null) {
	        hashCode ^= getEmail().hashCode();
	    }
	    return hashCode;
	}

	@Override
	public boolean equals(Object aObject) {
		if (!(aObject instanceof User)) {
		    return false;
		}
		User other = (User) aObject;
		return super.equals(other)
		        && ObjectUtil.equals(getEmail(), other.getEmail());
	}

	@Override
	public String toString() {
	    return getClass().getSimpleName() + "[id=" + getId()
	                                      + ",name=" + StringUtil.toString(getName())
                                          + ",email=" + StringUtil.toString(getEmail())
                                          + ",password=" + StringUtil.toString(getPassword())
                                          + ",salt=" + StringUtil.toString(getSalt())
                                          + ",session=" + StringUtil.toString(getSession()) + "]";
	}
}


