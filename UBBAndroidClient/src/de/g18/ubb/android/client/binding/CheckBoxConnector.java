package de.g18.ubb.android.client.binding;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
final class CheckBoxConnector extends AbstractPropertyConnector<Boolean, CheckBox> implements OnCheckedChangeListener {

    public CheckBoxConnector(CheckBox aComponent, AbstractModel aModel, String aPropertyname) {
        super(aComponent, aModel, aPropertyname);
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
