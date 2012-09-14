package de.g18.ubb.android.client.shared.binding;

import android.widget.TextView;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.StringUtil;

/**
 * Klasse zum anbinden einer Model-Property an eine {@link TextView}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
final class TextViewConnector extends AbstractPropertyConnector<Object, TextView> {

    public TextViewConnector(TextView aComponent, AbstractModel aModel, String aPropertyname) {
        super(aComponent, aModel, aPropertyname);
    }

    @Override
    void updateComponent(Object aNewValue) {
        getComponent().setText(aNewValue == null ? null : StringUtil.toString(aNewValue));
    }
}
