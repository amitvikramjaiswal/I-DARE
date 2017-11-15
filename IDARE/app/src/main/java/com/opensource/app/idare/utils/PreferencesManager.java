package com.opensource.app.idare.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;

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

    public void clearAllPreferences() {
        preferences.edit().clear().commit();
    }

    public UserProfileResponseModel getUserDetails() {
        UserProfileResponseModel userProfileResponseModel = new Gson().fromJson(preferences.getString(Utility.KEY_USER_CONTEXT, null), UserProfileResponseModel.class);
        return userProfileResponseModel;
    }

    public void setUserProfileResponse(UserProfileResponseModel userContext) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Utility.KEY_USER_CONTEXT, new Gson().toJson(userContext));
        editor.commit();
    }
}
