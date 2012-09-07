package de.g18.ubb.android.client.binding;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.ObjectUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
final class SpinnerConnector extends AbstractPropertyConnector<Object, Spinner> implements OnItemSelectedListener {

    public SpinnerConnector(Spinner aComponent, AbstractModel aModel, String aPropertyname) {
        super(aComponent, aModel, aPropertyname);
        getComponent().setOnItemSelectedListener(this);
    }

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		updateModel(getComponent().getSelectedItem());
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		updateModel(null);
	}

	@Override
	void updateComponent(Object aNewValue) {
		if (getComponent().getAdapter() != null) {
			for (int i = 0; i < getComponent().getAdapter().getCount(); i++) {
				if (ObjectUtil.equals(aNewValue, getComponent().getAdapter().getItem(i))) {
					getComponent().setSelection(i);
					return;
				}
			}
		}
		getComponent().setSelection(0);
	}
}
