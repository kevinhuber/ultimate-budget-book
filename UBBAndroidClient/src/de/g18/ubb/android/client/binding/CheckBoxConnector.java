package de.g18.ubb.android.client.binding;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.g18.ubb.android.client.shared.ValueModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
final class CheckBoxConnector extends AbstractPropertyConnector<Boolean, CheckBox> implements OnCheckedChangeListener {

    public CheckBoxConnector(CheckBox aComponent, ValueModel aModel) {
        super(aComponent, aModel);
        getComponent().setOnCheckedChangeListener(this);
    }

    @Override
    void updateComponent(Boolean aNewValue) {
        getComponent().setChecked(aNewValue);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        updateModel(isChecked);
    }
}
