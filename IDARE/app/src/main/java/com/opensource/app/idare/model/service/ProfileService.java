package com.opensource.app.idare.model.service;

import android.content.Context;

import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;

/**
 * Created by akokala on 11/6/2017.
 */

public interface ProfileService {

    UserProfileRequestModel getRequestBody(String userName, String password, String name, String email, String mobile, String alternativeNum);

    void fetchUserDetails(Context context, String userName, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void checkIfUserExists(Context context, String username, IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void postProfileDetails(Context context, UserProfileRequestModel userProfile, IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void checkIfPasswordExists(Context context,String userName, String password,IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, IDAREResponseHandler.ErrorListener errorListener);
}
