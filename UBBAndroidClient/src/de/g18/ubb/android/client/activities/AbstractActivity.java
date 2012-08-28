package de.g18.ubb.android.client.activities;

import android.app.Activity;
import android.content.Intent;
import de.g18.ubb.android.client.preferences.Preferences;
import de.g18.ubb.android.client.utils.UBBConstants;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractActivity extends Activity {

    private Preferences preferences;


    protected final Preferences getPreferences() {
        if (preferences == null) {
            preferences = new Preferences(getSharedPreferences(UBBConstants.PREFERENCES_FILENAME, MODE_PRIVATE));
        }
        return preferences;
    }

    protected final void switchActivity(Class<? extends Activity> aActivityClass) {
        Intent myIntent = new Intent(getApplicationContext(), aActivityClass);
        startActivityForResult(myIntent, 0);
    }
}
