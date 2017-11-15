package com.opensource.app.idare.model.service.impl;


import android.content.Context;

import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.ProfileService;
import com.opensource.app.idare.model.service.SessionFacade;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;

public class SessionFacadeImpl implements SessionFacade {

    private static SessionFacade sessionFacade;
    private static ProfileService profileService;

    public static synchronized SessionFacade getInstance() {
        // Thread safe singleton, avoiding the race condition

        if (sessionFacade == null) {
            sessionFacade = (SessionFacade) new SessionFacadeImpl();
            profileService = ProfileServiceImpl.getInstance();

        }

        return sessionFacade;
    }

    @Override
    public UserProfileRequestModel getRequestBody(String userName, String password, String name, String email, String mobile, String alternativeNum) {
        return profileService.getRequestBody(userName, password, name, email, mobile, alternativeNum);
    }

    @Override
    public void checkIfPasswordExists(Context context,String userName, String password, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        profileService.checkIfPasswordExists(context, userName,password, responseListener, errorListener);
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

}
