package de.g18.ubb.android.client.shared;

import java.beans.PropertyChangeListener;

import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class ValueModel extends AbstractModel {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_VALUE = "value";

    private Object value;


    public ValueModel() {
        this(null);
    }

    public ValueModel(Object aValue) {
        value = aValue;
    }

    public void setValue(Object aNewValue) {
        Object oldValue = getValue();
        value = aNewValue;
        fireChange(PROPERTY_VALUE, oldValue, getValue());
    }

    public Object getValue() {
        return value;
    }

    public final void addValueChangeListener(PropertyChangeListener aListener) {
        addPropertyChangeListener(PROPERTY_VALUE, aListener);
    }
}
