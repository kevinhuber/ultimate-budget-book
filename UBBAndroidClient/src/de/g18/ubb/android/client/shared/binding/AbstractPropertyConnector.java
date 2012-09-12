package de.g18.ubb.android.client.shared.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.view.View;
import de.g18.ubb.android.client.shared.ValueModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
abstract class AbstractPropertyConnector<_PropertyType, _ComponentType extends View> implements PropertyChangeListener {

    private final _ComponentType component;
    private final ValueModel model;

    private boolean running;


    public AbstractPropertyConnector(_ComponentType aComponent, ValueModel aModel) {
        component = aComponent;
        model = aModel;
        model.addValueChangeListener(this);

        updateComponent();
    }

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

    @SuppressWarnings("unchecked")
    protected final void updateComponent() {
        _PropertyType value = (_PropertyType) model.getValue();
        updateComponent(value);
    }

    protected final void updateModel(_PropertyType aNewValue) {
        if (running) {
            return;
        }
        running = true;

        try {
        	model.setValue(aNewValue);
        } finally {
            running = false;
        }
    }

    abstract void updateComponent(_PropertyType aNewValue);
}
