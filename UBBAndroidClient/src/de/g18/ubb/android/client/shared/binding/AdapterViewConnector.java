package de.g18.ubb.android.client.shared.binding;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.ObjectUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
final class AdapterViewConnector extends AbstractPropertyConnector<Object, AdapterView<?>> implements OnItemSelectedListener {

    public AdapterViewConnector(AdapterView<?> aComponent, AbstractModel aModel, String aPropertyname) {
        super(aComponent, aModel, aPropertyname);
        getComponent().setOnItemSelectedListener(this);
    }

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		updateModel(getComponent().getSelectedItem());
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		updateModel(null);
	}

	@Override
	void updateComponent(Object aNewValue) {
		int index = getItemIndex(aNewValue);
		getComponent().setSelection(index == -1 ? 0 : index);
	}

	private int getItemIndex(Object aItem) {
        return getItemIndex(aItem, getComponent().getAdapter());
	}

    private int getItemIndex(Object aItem, Adapter aAdapter) {
        if (aAdapter != null) {
            for (int i = 0; i < aAdapter.getCount(); i++) {
                if (ObjectUtil.equals(aItem, aAdapter.getItem(i))) {
                    return i;
                }
            }
        }
        return -1;
    }
}
