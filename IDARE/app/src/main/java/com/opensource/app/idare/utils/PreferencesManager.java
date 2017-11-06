package com.opensource.app.idare.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.opensource.app.idare.application.IDareApp;

/**
 * Created by akokala on 11/6/2017.
 */

public class PreferencesManager {

    private static PreferencesManager preferencesManager;
    private SharedPreferences preferences;

    private PreferencesManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesManager getInstance(Context context) {
        if (preferencesManager == null) {
            preferencesManager = new PreferencesManager(context);
        }
        return preferencesManager;
    }

    public void setUserContext() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Utility.KEY_USER_CONTEXT, new Gson().toJson(IDareApp.getUserProfileResponseModel()));
        editor.commit();
    }
}
