package com.opensource.app.idare.model.service.impl;


import android.content.Context;

import com.opensource.app.idare.model.data.entity.IDareLocation;
import com.opensource.app.idare.model.data.entity.RegisterDevice;
import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.NearBySafeHouseService;
import com.opensource.app.idare.model.service.NotificationService;
import com.opensource.app.idare.model.service.ProfileService;
import com.opensource.app.idare.model.service.SessionFacade;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;

public class SessionFacadeImpl implements SessionFacade {

    private static SessionFacade sessionFacade;
    private static ProfileService profileService;
    private static NotificationService notificationService;
    private static NearBySafeHouseService nearBySafeHouseService;

    public static synchronized SessionFacade getInstance() {
        // Thread safe singleton, avoiding the race condition

        if (sessionFacade == null) {
            sessionFacade = new SessionFacadeImpl();
            profileService = ProfileServiceImpl.getInstance();
            notificationService = NotificationServiceImpl.getInstance();
            nearBySafeHouseService = NearBySafeHouseServiceImpl.getInstance();
        }

        return sessionFacade;
    }

    @Override
    public UserProfileRequestModel getRequestBody(String userName, String password, String name, String email, String mobile, String alternativeNum, IDareLocation iDareLocation) {
        return profileService.getRequestBody(userName, password, name, email, mobile, alternativeNum, iDareLocation);
    }

    @Override
    public void login(Context context, String userName, String password, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        profileService.login(context, userName,password, responseListener, errorListener);
    }

    @Override
    public void fetchUserDetails(Context context, String userName, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        profileService.fetchUserDetails(context, userName, responseListener, errorListener);
    }

    @Override
    public void checkIfUserExists(Context context, String username, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        profileService.checkIfUserExists(context, username, responseListener, errorListener);
    }

    @Override
    public void postProfileDetails(Context context, UserProfileRequestModel userProfile, IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        profileService.postProfileDetails(context, userProfile, responseListener, errorListener);
    }

    @Override
    public void updateProfile(Context context, String id, UserProfileRequestModel userProfileRequestModel, final IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        profileService.updateProfile(context, id, userProfileRequestModel, responseListener, errorListener);
    }

    @Override
    public void registerDeviceToFCM(Context context, RegisterDevice registerDevice, final IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        notificationService.registerDeviceToFCM(context, registerDevice, responseListener, errorListener);
    }

    @Override
    public void unregisterDeviceToFCM(Context context, RegisterDevice registerDevice, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        notificationService.unregisterDeviceToFCM(context, registerDevice, responseListener, errorListener);
    }

    @Override
    public void getNearBySafeHouses(Context context, String key, String location, String radius, String type, String nextPageToken, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        nearBySafeHouseService.getNearBySafeHouses(context, key, location, radius, type, nextPageToken, responseListener, errorListener);
    }

}
