package de.g18.ubb.common.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import javax.persistence.Transient;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private final PropertyChangeSupport changeSupport;


    public AbstractModel() {
        changeSupport = new PropertyChangeSupport(this);
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
