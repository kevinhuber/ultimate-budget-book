package de.g18.ubb.common.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * Abgespeckte Version der {@link User} Entität.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Entity
@Table(name = "DBUser")
public final class UserExtract extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_EMAIL = "email";

    private String name;
    private String email;


    public UserExtract(User aUser) {
        setId(aUser.getId());
        setName(aUser.getName());
        setEmail(aUser.getEmail());
    }

    public UserExtract() {
    }

    public void setName(String aNewValue) {
        String oldValue = getName();
        name = aNewValue;
        fireChange(PROPERTY_NAME, oldValue, getName());
    }

    public String getName() {
        return name;
    }

    public void setEmail(String aNewValue) {
        String oldValue = getEmail();
        email = aNewValue;
        fireChange(PROPERTY_EMAIL, oldValue, getEmail());
    }

    public String getEmail() {
        return email;
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
        if (!(aObject instanceof UserExtract)) {
            return false;
        }
        UserExtract other = (UserExtract) aObject;
        return super.equals(other)
                && ObjectUtil.equals(getEmail(), other.getEmail());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + getId()
                                          + ",name=" + StringUtil.toString(getName())
                                          + ",email=" + StringUtil.toString(getEmail()) + "]";
    }
}
