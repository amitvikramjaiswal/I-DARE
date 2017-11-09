package com.opensource.app.idare.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
}
