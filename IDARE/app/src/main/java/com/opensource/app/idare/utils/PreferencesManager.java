package com.opensource.app.idare.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.opensource.app.idare.model.data.entity.IDareLocation;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;

import static com.opensource.app.idare.utils.Constants.LATITUDE;
import static com.opensource.app.idare.utils.Constants.LONGITUDE;
import static com.opensource.app.idare.utils.Constants.PASSWORD;
import static com.opensource.app.idare.utils.Constants.USERNAME;

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
        UserProfileResponseModel userProfileResponseModel = new Gson().fromJson(preferences.getString(Constants.KEY_USER_CONTEXT, null), UserProfileResponseModel.class);
        return userProfileResponseModel;
    }

    public void setUserProfileResponse(UserProfileResponseModel userContext) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_USER_CONTEXT, new Gson().toJson(userContext));
        editor.apply();
    }

    public void rememberForSingleSignOn(String userName, String password) {
        preferences.edit().putString(USERNAME, userName).putString(Constants.PASSWORD, password).apply();
    }

    public boolean wasUserLoggedIn() {
        return preferences.contains(USERNAME) && preferences.contains(PASSWORD);
    }

    public String getUserPass() {
        return preferences.getString(PASSWORD, null);
    }

    public String getUsername() {
        return preferences.getString(USERNAME, null);
    }

    public void updateUserLocation(Location lastLocation) {
        preferences.edit().putString(LATITUDE, String.valueOf(lastLocation.getLatitude()))
                .putString(LONGITUDE, String.valueOf(lastLocation.getLongitude())).apply();
    }

    public IDareLocation getLastLocation() {
        IDareLocation iDareLocation = new IDareLocation();
        iDareLocation.setLatitude(Double.parseDouble(preferences.getString(LATITUDE, "0.0")));
        iDareLocation.setLongitude(Double.parseDouble(preferences.getString(LONGITUDE, "0.0")));
        return iDareLocation;
    }
}
