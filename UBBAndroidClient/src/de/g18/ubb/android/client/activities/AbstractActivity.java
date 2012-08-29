package de.g18.ubb.android.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import de.g18.ubb.android.client.preferences.Preferences;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractActivity extends Activity {

    private Preferences preferences;


    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(getLayoutId());
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

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    protected abstract int getLayoutId();
}
