package de.g18.ubb.android.client.shared.binding;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * Klasse zum binden einer Model-Property an eine {@link View}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class Bindings {

    private Bindings() {
        // prevent instantiation
    }

    /**
     * Bindet die übergebene {@link View} an die Property des übergebenen {@link AbstractModel}s,
     * falls ein passendes Binding definiert wurde.
     *
     * @throws IllegalStateException falls kein passendes Binding gefunden wurde.
     */
    public static void bind(View aComponent, AbstractModel aModel, String aPropertyname) {
        if (aComponent instanceof EditText) {
            bind((EditText) aComponent, aModel, aPropertyname);
            return;
        }
        if (aComponent instanceof CheckBox) {
            bind((CheckBox) aComponent, aModel, aPropertyname);
            return;
        }
        if (aComponent instanceof AdapterView<?>) {
            bind((Spinner) aComponent, aModel, aPropertyname);
            return;
        }
        if (aComponent instanceof TextView) {
            bind((TextView) aComponent, aModel, aPropertyname);
            return;
        }
        throw new IllegalStateException("Can not bind instances of " + aComponent.getClass().getName());
    }

    /**
     * Bindet den übergebenen {@link EditText} an die Property des übergebenen {@link AbstractModel}s.
     */
    public static void bind(EditText aComponent, AbstractModel aModel, String aPropertyname) {
        new EditTextConnector(aComponent, aModel, aPropertyname);
    }

    /**
     * Bindet die übergebene {@link CheckBox} an die Property des übergebenen {@link AbstractModel}s.
     */
    public static void bind(CheckBox aComponent, AbstractModel aModel, String aPropertyname) {
        new CheckBoxConnector(aComponent, aModel, aPropertyname);
    }

    /**
     * Bindet die übergebene {@link AdapterView} an die Property des übergebenen {@link AbstractModel}s.
     */
    public static void bind(AdapterView<?> aComponent, AbstractModel aModel, String aPropertyname) {
        new AdapterViewConnector(aComponent, aModel, aPropertyname);
    }

    /**
     * Bindet die übergebene {@link TextView} an die Property des übergebenen {@link AbstractModel}s.
     */
    public static void bind(TextView aComponent, AbstractModel aModel, String aPropertyname) {
        new TextViewConnector(aComponent, aModel, aPropertyname);
    }
}
