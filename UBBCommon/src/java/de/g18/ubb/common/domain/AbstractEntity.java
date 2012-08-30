package de.g18.ubb.common.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstrakte Entität die in einer Datenbank persistiert werden kann.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@MappedSuperclass
public abstract class AbstractEntity extends AbstractModel implements Identifiable {

    private static final long serialVersionUID = 1L;

    private Long id;


    public AbstractEntity() {
    }

    @Override
    public final void setId(Long aNewValue) {
        Long oldValue = getId();
        id = aNewValue;
        fireChange(PROPERTY_ID, oldValue, aNewValue);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public final Long getId() {
        return id;
    }


    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return getClass().getName() + "[id=" + getId() + "]";
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            return 0;
        }
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object aObject) {
        if (!(aObject instanceof AbstractEntity)) {
            return false;
        }

        Identifiable otherEntity = (Identifiable) aObject;
        return getId() == otherEntity.getId();
    }
}
