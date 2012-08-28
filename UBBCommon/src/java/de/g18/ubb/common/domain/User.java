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
	public static final String PROPERTY_EMAIL = "eMail";
	public static final String PROPERTY_PASSWORD_HASH = "passwordHash";
	public static final String PROPERTY_SALT = "salt";

	private String name;
	private String eMail;
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

	public void setEMail(String aNewValue) {
	    String oldValue = getEMail();
		eMail = aNewValue;
		fireChange(PROPERTY_EMAIL, oldValue, getEMail());
	}

	@Column(length = 32, nullable = false)
	public String getEMail() {
		return eMail;
	}

	public void setPasswordHash(String aNewValue) {
	    String oldValue = getPasswordHash();
		passwordHash = aNewValue;
		fireChange(PROPERTY_PASSWORD_HASH, oldValue, getPasswordHash());
	}

	@Column(length = 32)
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
	    if (getEMail() != null) {
	        hashCode ^= getEMail().hashCode();
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
		        && ObjectUtil.equals(getEMail(), other.getEMail());
	}

	@Override
	public String toString() {
	    return getClass().getSimpleName() + "[id=" + getId()
	                                      + ",name=" + StringUtil.toString(getName())
                                          + ",email=" + StringUtil.toString(getEMail())
                                          + ",passwordHash=" + StringUtil.toString(getPasswordHash())
                                          + ",salt=" + StringUtil.toString(getSalt()) + "]";
	}
}


