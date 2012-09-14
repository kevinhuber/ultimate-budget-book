package de.g18.ubb.android.client.shared.binding;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.domain.UserExtract;
import de.g18.ubb.common.util.StringUtil;

/**
 * Klasse zum anbinden einer Model-Property an einen {@link EditText}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
final class EditTextConnector extends AbstractPropertyConnector<Object, EditText> implements TextWatcher {

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
    	Class<?> propertyType = getPropertyAccessor().getGetter().getReturnType();
		if (Float.class.isAssignableFrom(propertyType)
		     || float.class.isAssignableFrom(propertyType)) {
			updateModel(StringUtil.isEmpty(aEditable.toString()) ? 0.0F : Float.parseFloat(aEditable.toString()));
		} else if (long.class.isAssignableFrom(propertyType)
			        || Long.class.isAssignableFrom(propertyType)) {
			updateModel(StringUtil.isEmpty(aEditable.toString()) ? 0L : Long.parseLong(aEditable.toString()));
		} else if (int.class.isAssignableFrom(propertyType)
				    || Integer.class.isAssignableFrom(propertyType)) {
			updateModel(StringUtil.isEmpty(aEditable.toString()) ? 0 : Integer.parseInt(aEditable.toString()));
		} else if (double.class.isAssignableFrom(propertyType)
				    || Double.class.isAssignableFrom(propertyType)) {
			updateModel(StringUtil.isEmpty(aEditable.toString()) ? 0.0D : Double.parseDouble(aEditable.toString()));
		} else if (UserExtract.class.isAssignableFrom(propertyType)) {
			throw new IllegalStateException("Benutzer können nicht editiert werden!");
		} else {
			updateModel(aEditable.toString());
		}
    }

    @Override
    void updateComponent(Object aNewValue) {
    	Class<?> propertyType = getPropertyAccessor().getGetter().getReturnType();
		if (UserExtract.class.isAssignableFrom(propertyType)) {
			getComponent().setText(aNewValue == null ? null : ((UserExtract) aNewValue).getEmail());
		} else if (Date.class.isAssignableFrom(propertyType)) {
			SimpleDateFormat sdf = new SimpleDateFormat(UBBConstants.DATE_FORMAT);
			getComponent().setText(aNewValue == null ? null : sdf.format( (Date) aNewValue));
		} else {
			getComponent().setText(aNewValue == null ? null : StringUtil.toString(aNewValue));
		}
    }
}
