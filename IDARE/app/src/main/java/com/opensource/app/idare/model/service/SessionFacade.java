package com.opensource.app.idare.model.service;


import android.content.Context;

import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;

public interface SessionFacade {

    UserProfileRequestModel getRequestBody(String userName, String password, String name, String email, String mobile, String alternativeNum);

    void postProfileDetails(Context context, UserProfileRequestModel userProfile, IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, IDAREResponseHandler.ErrorListener errorListener);
}
