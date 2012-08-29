package de.g18.ubb.android.client.binding;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
final class EditTextConnector extends AbstractPropertyConnector<String, EditText> implements TextWatcher {

    public EditTextConnector(EditText aComponent, AbstractModel aModel, String aPropertyname) {
        super(aComponent, aModel, aPropertyname);
        getComponent().addTextChangedListener(this);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Ignore
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Ignore
    }

    public void afterTextChanged(Editable aEditable) {
        updateModel(aEditable.toString());
    }

    @Override
    void updateComponent(String aNewValue) {
        getComponent().setText(aNewValue);
    }
}
