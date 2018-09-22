package com.opensource.app.idare.model.service;

import android.content.Context;

import com.opensource.app.idare.model.data.entity.IDareLocation;
import com.opensource.app.idare.model.data.entity.ProfilePic;
import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;

/**
 * Created by akokala on 11/6/2017.
 */

public interface ProfileService {

    UserProfileRequestModel getRequestBody(String userName, String password, String name, String email, String mobile, String alternativeNum, IDareLocation iDareLocation);

    void fetchUserDetails(Context context, String userName, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void checkIfUserExists(Context context, String username, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void fetchNearByUsers(Context context, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void postProfileDetails(Context context, UserProfileRequestModel userProfile, IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void login(Context context, String userName, String password, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void updateProfile(Context context, String id, UserProfileRequestModel userProfileRequestModel, IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void uploadProfilePic(Context context, ProfilePic profilePic, IDAREResponseHandler.ResponseListener<ProfilePic> responseListener, IDAREResponseHandler.ErrorListener errorListener);
}
