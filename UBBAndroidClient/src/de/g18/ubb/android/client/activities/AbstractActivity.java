package de.g18.ubb.android.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import de.g18.ubb.android.client.binding.BindingUtils;
import de.g18.ubb.android.client.preferences.Preferences;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * Abstrakte Oberklasse für alle Activities, die ein {@link AbstractModel} an die {@link Activity} bindet und einige
 * "helfer"-Metohden für Ableitende Klassen zu verfügung stellt.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractActivity<_Model extends AbstractModel> extends FragmentActivity {

    private _Model model;
    private Preferences preferences;


    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(getLayoutId());
    }

    /**
     * Erstellt das Model der Activity "Lazy" durch den aufruf von {@link #createModel()} und gibt es zurück.
     */
    public final _Model getModel() {
        if (model == null) {
            model = createModel();
        }
        return model;
    }

    /**
     * Gibt eine "Lazy" erstellte Instanz der {@link Preferences} zurück.
     */
    protected final Preferences getPreferences() {
        if (preferences == null) {
            preferences = new Preferences(this);
        }
        return preferences;
    }

    /**
     * Öffnet die übergebene {@link Activity}.
     */
    protected final void switchActivity(Class<? extends Activity> aActivityClass) {
        Intent myIntent = new Intent(getApplicationContext(), aActivityClass);
        startActivityForResult(myIntent, 0);
    }

    /**
     * Bindet eine Property des Models anhand ihres Namens an eine {@link View} anhand ihrer id
     * und gibt die gebundene {@link View} zurück.
     */
    protected final View bind(String aPropertyname, int aViewId) {
        return bind(getModel(), aPropertyname, aViewId);
    }

    /**
     * Bindet eine Property des Models anhand ihres Namens an eine {@link View} anhand ihrer id
     * und gibt die gebundene {@link View} zurück.
     */
    protected final View bind(AbstractModel aModel, String aPropertyname, int aComponentId) {
        return bind(aModel, aPropertyname, aComponentId, View.class);
    }

    /**
     * Bindet eine Property des Models anhand ihres Namens an eine {@link View} anhand ihrer id
     * und gibt die gebundene und gecastete {@link View} zurück.
     */
    protected final <_ComponentType extends View> _ComponentType bind(String aPropertyname, int aComponentId,
                                                                      Class<? extends _ComponentType> aComponentType) {
        return bind(getModel(), aPropertyname, aComponentId, aComponentType);
    }

    /**
     * Bindet eine Property des Models anhand ihres Namens an eine {@link View} anhand ihrer id
     * und gibt die gebundene und gecastete {@link View} zurück.
     */
    @SuppressWarnings("unchecked")
    protected final <_ComponentType extends View> _ComponentType bind(AbstractModel aModel, String aPropertyname, int aComponentId,
                                                                      Class<? extends _ComponentType> aComponentType) {
        _ComponentType component = (_ComponentType) findViewById(aComponentId);
        BindingUtils.bind(component, getModel(), aPropertyname);
        return component;
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    /**
     * Erstellt das {@link AbstractModel}, dass in mit dieser {@link Activity} gebunden werden soll.
     */
    protected abstract _Model createModel();

    /**
     * Gibt die Id des Layouts der {@link Activity} zurück.
     */
    protected abstract int getLayoutId();
}
