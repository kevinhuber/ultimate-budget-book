package de.g18.ubb.android.client.shared;

import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class ValueModel<_Value> extends AbstractModel {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_VALUE = "value";

    private _Value value;


    public ValueModel() {
        this(null);
    }

    public ValueModel(_Value aValue) {
        value = aValue;
    }

    public void setValue(_Value aNewValue) {
        _Value oldValue = getValue();
        value = aNewValue;
        fireChange(PROPERTY_VALUE, oldValue, getValue());
    }

    public _Value getValue() {
        return value;
    }
}
