package com.opensource.app.idare.utils;

import android.location.Location;

import com.opensource.app.idare.model.data.entity.ProfilePic;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;

/**
 * Created by akokala on 11/10/2017.
 */

public class Session {

    private static Session session;
    private UserProfileResponseModel userProfileResponseModel;
    private boolean isRegisteredToFCM;
    private boolean isShoppingMallChecked, isPoliceStationChecked, isCafeChecked;
    private int radius;
    private ProfilePic profilePic;
    private Location lastLocation;

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    //Use resetSession() instead of destroySessionInfo()
    public static void destroySessionInfo() {
        Session.session = null;
    }

    public UserProfileResponseModel getUserProfileResponseModel() {
        return userProfileResponseModel;
    }

    public void setUserProfileResponseModel(UserProfileResponseModel userProfileResponseModel) {
        this.userProfileResponseModel = userProfileResponseModel;
    }

    public void setRegistered(boolean isRegisteredToFCM) {
        this.isRegisteredToFCM = isRegisteredToFCM;
    }

    public boolean isRegisteredToFCM() {
        return isRegisteredToFCM;
    }

    public void setSettingsData(boolean isShoppingMallChecked, boolean isPoliceStationChecked, boolean isCafeChecked, int radius) {
        this.isShoppingMallChecked = isShoppingMallChecked;
        this.isPoliceStationChecked = isPoliceStationChecked;
        this.isCafeChecked = isCafeChecked;
        this.radius = radius;
    }

    public ProfilePic getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(ProfilePic profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isShoppingMallChecked() {
        return isShoppingMallChecked;
    }

    public boolean isPoliceStationChecked() {
        return isPoliceStationChecked;
    }

    public boolean isCafeChecked() {
        return isCafeChecked;
    }

    public int getRadius() {
        return radius;
    }

    public void setLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Location getLastLocation() {
        return lastLocation;
    }
}
