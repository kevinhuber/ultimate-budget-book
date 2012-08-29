package de.g18.ubb.android.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import de.g18.ubb.android.client.binding.BindingUtils;
import de.g18.ubb.android.client.preferences.Preferences;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractActivity<_Model extends AbstractModel> extends Activity {

    private _Model model;
    private Preferences preferences;


    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(getLayoutId());
    }

    public final _Model getModel() {
        if (model == null) {
            model = createModel();
        }
        return model;
    }

    protected final Preferences getPreferences() {
        if (preferences == null) {
            preferences = new Preferences(this);
        }
        return preferences;
    }

    protected final void switchActivity(Class<? extends Activity> aActivityClass) {
        Intent myIntent = new Intent(getApplicationContext(), aActivityClass);
        startActivityForResult(myIntent, 0);
    }

    protected final View bind(String aPropertyname, int aComponentId) {
        return bind(getModel(), aPropertyname, aComponentId);
    }

    protected final View bind(AbstractModel aModel, String aPropertyname, int aComponentId) {
        return bind(aModel, aPropertyname, aComponentId, View.class);
    }

    protected final <_ComponentType extends View> _ComponentType bind(String aPropertyname,
                                                                      int aComponentId,
                                                                      Class<? extends _ComponentType> aComponentType) {
        return bind(getModel(), aPropertyname, aComponentId, aComponentType);
    }

    @SuppressWarnings("unchecked")
    protected final <_ComponentType extends View> _ComponentType bind(AbstractModel aModel,
                                                                      String aPropertyname,
                                                                      int aComponentId,
                                                                      Class<? extends _ComponentType> aComponentType) {
        _ComponentType component = (_ComponentType) findViewById(aComponentId);
        BindingUtils.bind(component, getModel(), aPropertyname);
        return component;
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    protected abstract _Model createModel();

    protected abstract int getLayoutId();
}
