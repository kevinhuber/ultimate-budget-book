package de.g18.ubb.android.client.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.util.StringUtil;

/**
 * Klasse zum abspeichern und lesen von Einstellungen aus den {@link SharedPreferences}.
 *
 * @author Daniel Fels
 */
public class Preferences {

	private final SharedPreferences sp;


	public Preferences(Activity aActivity) {
		sp = aActivity.getSharedPreferences(UBBConstants.PREFERENCES_FILENAME, Context.MODE_PRIVATE);
	}

    /**
     * Speichert die angegebenen Benuterdaten in den {@link SharedPreferences} ab.
     */
	public void saveLoginData(String username, String password) {
    	putString(PreferenceKey.USERNAME, username);
    	putString(PreferenceKey.PASSWORD, password);
	}

	/**
	 * Gibt den in den {@link SharedPreferences} gespeicherten Benutzernamen zurück.
	 */
	public String getUsername() {
		return getString(PreferenceKey.USERNAME);
	}

    /**
     * Gibt das in den {@link SharedPreferences} gespeicherte Passwort zurück.
     */
	public String getPassword() {
        return getString(PreferenceKey.PASSWORD);
	}

    /**
     * Speichert die angegebenen Serveradresse in den {@link SharedPreferences} ab.
     */
	public void saveServerAddress(String aNewValue) {
	    putString(PreferenceKey.SERVER_ADDRESS, aNewValue);
	}

    /**
     * Gibt die in den {@link SharedPreferences} gespeicherte Serveradresse zurück.
     */
	public String getServerAddress() {
	    return getString(PreferenceKey.SERVER_ADDRESS);
	}

	// -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------


    /**
     * Speichert die angegebenen String unter dem angegebenen Key in den {@link SharedPreferences} ab.
     */
	protected final void putString(final PreferenceKey aKey, final String aValue) {
        Editor edit = sp.edit();
        edit.putString(aKey.getKey(), StringUtil.isEmpty(aValue) ? null : aValue);
        edit.commit();
	}

	/**
	 * Gibt den String, der unter dem angegebenen Key in den {@link SharedPreferences} gespeichert wurde zurück.
	 */
	protected final String getString(PreferenceKey aKey) {
	    return sp.getString(aKey.getKey(), aKey.getDefaultValue());
	}
}
