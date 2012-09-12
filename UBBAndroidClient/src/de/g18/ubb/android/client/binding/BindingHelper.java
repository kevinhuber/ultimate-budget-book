package de.g18.ubb.android.client.binding;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import de.g18.ubb.android.client.shared.ValueModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class BindingHelper {

    private final Activity activity;


    public BindingHelper(Activity aActivity) {
        activity = aActivity;
    }

    public void bindEditText(ValueModel aModel, int aComponentId) {
        bindEditText(aModel, aComponentId, EditTextType.STRING);
    }

    public void bindEditText(ValueModel aModel, int aComponentId, EditTextType aType) {
        EditText view = findViewById(aComponentId, EditText.class);
        BindingUtils.bindEditText(aModel, view, aType);
    }

    public void bindCheckBox(ValueModel aModel, int aComponentId) {
        CheckBox view = findViewById(aComponentId, CheckBox.class);
        BindingUtils.bindCheckBox(aModel, view);
    }

    public void bindAdapterView(ValueModel aModel, int aComponentId) {
        AdapterView<?> view = findViewById(aComponentId, AdapterView.class);
        BindingUtils.bindAdapterView(aModel, view);
    }

    public void bindTextView(ValueModel aModel, int aComponentId) {
        TextView view = findViewById(aComponentId, TextView.class);
        BindingUtils.bindTextView(aModel, view);
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    private <_ViewType extends View> _ViewType findViewById(int aComponentId, Class<_ViewType> aComponentClass) {
        return (_ViewType) activity.findViewById(aComponentId);
    }
}
