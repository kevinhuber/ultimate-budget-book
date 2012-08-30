package de.g18.ubb.common.domain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import javax.persistence.Transient;

/**
 * Abstrakte Klasse für Models, die zwischen Client und Server ausgetauscht werden können und bei Änderungen
 * einer Property-Value {@link PropertyChangeEvent}s feuert.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private final PropertyChangeSupport changeSupport;


    public AbstractModel() {
        changeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Feuert ein {@link PropertyChangeEvent} für die übergebene Property.
     */
    protected final void fireChange(String aPropertyName, Object aOldValue, Object aNewValue) {
        if (aOldValue == aNewValue) {
            return;
        }
        changeSupport.firePropertyChange(aPropertyName, aOldValue, aNewValue);
    }

    /**
     * Entfernt den übergebenen {@link PropertyChangeListener} aus der Liste der Listener.
     */
    public final void removePropertyChangeListener(String aPropertyName, PropertyChangeListener aListener) {
        changeSupport.removePropertyChangeListener(aPropertyName, aListener);
    }

    /**
     * Fügt den übergebenen {@link PropertyChangeListener} zur Liste der Listener hinzu.
     */
    public final void addPropertyChangeListener(String aPropertyName, PropertyChangeListener aListener) {
        changeSupport.addPropertyChangeListener(aPropertyName, aListener);
    }
}
