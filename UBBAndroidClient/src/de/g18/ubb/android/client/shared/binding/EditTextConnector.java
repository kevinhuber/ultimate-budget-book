package de.g18.ubb.android.client.shared.binding;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import de.g18.ubb.android.client.shared.ValueModel;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
final class EditTextConnector extends AbstractPropertyConnector<Object, EditText> implements TextWatcher {

    private final EditTextType type;


    public EditTextConnector(EditText aComponent, ValueModel aModel, EditTextType aType) {
        super(aComponent, aModel);
        type = aType;

        getComponent().addTextChangedListener(this);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Ignore
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Ignore
    }

    public void afterTextChanged(Editable aEditable) {
		if (type == EditTextType.FLOAT) {
			updateModel(Float.parseFloat(aEditable.toString()));
		} else if (type == EditTextType.LONG) {
			updateModel(Long.parseLong(aEditable.toString()));
        } else if (type == EditTextType.INTEGER) {
			updateModel(Integer.parseInt(aEditable.toString()));
        } else if (type == EditTextType.DOUBLE) {
			updateModel(Double.parseDouble(aEditable.toString()));
		} else {
			updateModel(aEditable.toString());
		}
    }

    @Override
    void updateComponent(Object aNewValue) {
        getComponent().setText(aNewValue == null ? null : StringUtil.toString(aNewValue));
    }
}
