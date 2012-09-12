package de.g18.ubb.android.client.shared.binding;

import android.widget.TextView;
import de.g18.ubb.android.client.shared.ValueModel;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
final class TextViewConnector extends AbstractPropertyConnector<Object, TextView> {

    public TextViewConnector(TextView aComponent, ValueModel aModel) {
        super(aComponent, aModel);
    }

    @Override
    void updateComponent(Object aNewValue) {
        getComponent().setText(aNewValue == null ? null : StringUtil.toString(aNewValue));
    }
}
