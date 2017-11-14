package com.opensource.app.idare.utils;

import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;

/**
 * Created by akokala on 11/10/2017.
 */

public class Session {

    private static Session session;
    private UserProfileResponseModel userProfileResponseModel;

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
}
