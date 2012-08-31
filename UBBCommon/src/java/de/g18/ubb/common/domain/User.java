package de.g18.ubb.common.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.g18.ubb.common.util.HashUtil;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * Entität für Benutzer.
 *
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
@Entity
@Table(name = "DBUser")
public final class User extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final int EMAIL_LENGTH = 32;
    public static final int PASSWORD_LENGTH = 32;

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_EMAIL = "email";
	public static final String PROPERTY_PASSWORD_HASH = "passwordHash";
	public static final String PROPERTY_SALT = "salt";

	private String name;
	private String email;
	private String passwordHash;
	private byte[] salt;


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

	@Column(length = EMAIL_LENGTH, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setPasswordHash(String aNewValue) {
	    String oldValue = getPasswordHash();
		passwordHash = aNewValue;
		fireChange(PROPERTY_PASSWORD_HASH, oldValue, getPasswordHash());
	}

	@Column(length = PASSWORD_LENGTH)
	public String getPasswordHash() {
		return passwordHash;
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
                                          + ",passwordHash=" + StringUtil.toString(getPasswordHash())
                                          + ",salt=" + StringUtil.toString(getSalt()) + "]";
	}
}


