package de.g18.ubb.android.client.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import de.g18.ubb.common.util.StringUtil;

public class Preferences {

	private final SharedPreferences sp;

	public Preferences(SharedPreferences sp) {
		this.sp = sp;
	}

	public void clearLoginData() {
	    saveLoginData(null, null);
	}

	public void saveLoginData(String username, String password) {
    	putString(PreferenceKey.USERNAME, username);
    	putString(PreferenceKey.PASSWORD, password);
	}

	public String getUsername() {
		return getString(PreferenceKey.USERNAME);
	}

	public String getPassword() {
        return getString(PreferenceKey.PASSWORD);
	}

	public void saveServerAddress(String aNewValue) {
	    putString(PreferenceKey.SERVER_ADDRESS, aNewValue);
	}

	public String getServerAddress() {
	    return getString(PreferenceKey.SERVER_ADDRESS);
	}

	// -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

	protected final void putString(PreferenceKey aKey, String aValue) {
        Editor edit = sp.edit();
        edit.clear();
        edit.putString(aKey.getKey(), StringUtil.isEmpty(aValue) ? null : aValue);
        edit.commit();
	}

	protected final String getString(PreferenceKey aKey) {
	    return sp.getString(aKey.getKey(), aKey.getDefaultValue());
	}
}
