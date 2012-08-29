package de.g18.ubb.android.client.binding;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class BindingUtils {

    private BindingUtils() {
        // prevent instantiation
    }

    public static void bind(View aComponent, AbstractModel aModel, String aPropertyname) {
        if (aComponent instanceof EditText) {
            bind((EditText) aComponent, aModel, aPropertyname);
            return;
        }
        if (aComponent instanceof CheckBox) {
            bind((CheckBox) aComponent, aModel, aPropertyname);
            return;
        }
        throw new IllegalStateException("Can not bind instances of " + aComponent.getClass().getName());
    }

    public static void bind(EditText aComponent, AbstractModel aModel, String aPropertyname) {
        new EditTextConnector(aComponent, aModel, aPropertyname);
    }

    public static void bind(CheckBox aComponent, AbstractModel aModel, String aPropertyname) {
        new CheckBoxConnector(aComponent, aModel, aPropertyname);
    }
}
