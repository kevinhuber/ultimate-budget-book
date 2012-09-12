package de.g18.ubb.android.client.binding;

import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import de.g18.ubb.android.client.shared.ValueModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class BindingUtils {

    private BindingUtils() {
        // prevent instantiation
    }

    public static void bindEditText(ValueModel aModel, EditText aComponent, EditTextType aType) {
        new EditTextConnector(aComponent, aModel, aType);
    }

    public static void bindCheckBox(ValueModel aModel, CheckBox aComponent) {
        new CheckBoxConnector(aComponent, aModel);
    }

    public static void bindAdapterView(ValueModel aModel, AdapterView<?> aComponent) {
        new AdapterViewConnector(aComponent, aModel);
    }

    public static void bindTextView(ValueModel aModel, TextView aComponent) {
        new TextViewConnector(aComponent, aModel);
    }
}
