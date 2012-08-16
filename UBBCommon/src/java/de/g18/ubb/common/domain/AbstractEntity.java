package de.g18.ubb.common.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@MappedSuperclass
public abstract class AbstractEntity implements Identifiable, Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private final PropertyChangeSupport changeSupport;

    private Long id;


    protected AbstractEntity() {
        changeSupport = new PropertyChangeSupport(this);
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

    protected final void fireChange(String aPropertyName, Object aOldValue, Object aNewValue) {
        if (aOldValue == aNewValue) {
            return;
        }
        changeSupport.firePropertyChange(aPropertyName, aOldValue, aNewValue);
    }

    public final void removePropertyChangeListener(String aPropertyName, PropertyChangeListener aListener) {
        changeSupport.removePropertyChangeListener(aPropertyName, aListener);
    }

    public final void addPropertyChangeListener(String aPropertyName, PropertyChangeListener aListener) {
        changeSupport.addPropertyChangeListener(aPropertyName, aListener);
    }
}
