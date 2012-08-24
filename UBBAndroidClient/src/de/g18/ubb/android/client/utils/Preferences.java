package de.g18.ubb.android.client.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences {

	
	private final SharedPreferences sp;
	
	public Preferences(SharedPreferences sp) {
		this.sp = sp;
	}
	
	public void savePreferences(String username, String password){
    	Editor edit = sp.edit();
    	edit.clear();
    	edit.putString("username", username == null ? null : username.trim());
    	edit.putString("password", password == null ? null : password.trim());
    	edit.commit();
	}
	
	public void clearPreferences(){
		savePreferences(null, null);
	}

	public String getUserNamePreferences(){
		return sp.getString("username", "E-Mail");
	}
	
	public String getPasswordPreferences(){
		return sp.getString("password", "Passwort");
	}

}
