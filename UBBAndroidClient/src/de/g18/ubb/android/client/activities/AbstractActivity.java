package de.g18.ubb.android.client.activities;

import android.app.Activity;
import de.g18.ubb.android.client.utils.Preferences;
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
}
