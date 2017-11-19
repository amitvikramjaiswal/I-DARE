package com.opensource.app.idare.model.service;


import android.content.Context;

import com.opensource.app.idare.model.data.entity.IDareLocation;
import com.opensource.app.idare.model.data.entity.RegisterDevice;
import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.view.activity.NearBySafeHouseActivity;

public interface SessionFacade {

    UserProfileRequestModel getRequestBody(String userName, String password, String name, String email, String mobile, String alternativeNum, IDareLocation iDareLocation);

    void login(Context context, String userName, String password, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void fetchUserDetails(Context context, String userName, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void checkIfUserExists(Context context, String username, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void postProfileDetails(Context context, UserProfileRequestModel userProfile, IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void updateProfile(Context context, String id, UserProfileRequestModel userProfileRequestModel, IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void registerDeviceToFCM(Context context, RegisterDevice registerDevice, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void unregisterDeviceToFCM(Context context, RegisterDevice registerDevice, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void getNearBySafeHouses(Context context, String key, String location, String radius, String type, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);
}
