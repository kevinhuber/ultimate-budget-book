package de.g18.ubb.android.client.shared.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.view.View;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.PropertyAccessor;

/**
 * Abstrakte-Klasse zum "binden" einer Model-Property an eine Komponente, mit hilfe eines {@link PropertyAccessor}s.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
abstract class AbstractPropertyConnector<_PropertyType, _ComponentType extends View> implements PropertyChangeListener {

    private final _ComponentType component;
    private final PropertyAccessor<_PropertyType> propertyAccessor;

    private boolean running;


    public AbstractPropertyConnector(_ComponentType aComponent, AbstractModel aModel, String aPropertyname) {
        component = aComponent;
        propertyAccessor = new PropertyAccessor<_PropertyType>(aModel, aPropertyname);
        aModel.addPropertyChangeListener(aPropertyname, this);

        updateComponent();
    }

    /**
     * Gibt die Komponente zurück, mit der die Model-Property gebunden sein soll.
     */
    protected final _ComponentType getComponent() {
        return component;
    }

    public final void propertyChange(PropertyChangeEvent event) {
        if (running) {
            return;
        }
        running = true;

        try {
            updateComponent();
        } finally {
            running = false;
        }
    }

    /**
     * Aktualisiert die Komponente mit dem im Model gespeicherten Wert.
     */
    protected final void updateComponent() {
        _PropertyType value = getPropertyAccessor().invokeGetter();
        updateComponent(value);
    }

    /**
     * Schreibt den übergebenen Wert in das Model.
     */
    protected final void updateModel(_PropertyType aNewValue) {
        if (running) {
            return;
        }
        running = true;

        try {
        	getPropertyAccessor().invokeSetter(aNewValue);
        } finally {
            running = false;
        }
    }

    /**
     * Gibt den {@link PropertyAccessor} zurück, der benutzt wird, um zugriff auf die Property im Model zu erhalten.
     */
    protected final PropertyAccessor<_PropertyType> getPropertyAccessor() {
    	return propertyAccessor;
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    /**
     * Schreibt den übergebenen Wert in die Komponente.
     */
    abstract void updateComponent(_PropertyType aNewValue);
}
