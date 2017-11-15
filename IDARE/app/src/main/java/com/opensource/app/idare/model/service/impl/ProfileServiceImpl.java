package com.opensource.app.idare.model.service.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.ProfileService;
import com.opensource.app.idare.model.service.URLs;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.utils.AuthType;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Session;
import com.opensource.app.idare.utils.Utility;

import java.util.HashMap;
import java.util.Map;

import static com.opensource.app.idare.utils.AuthType.APP_CREDENTIALS;
import static com.opensource.app.idare.utils.AuthType.MASTER_SECRET;
import static com.opensource.app.idare.utils.AuthType.USER_CREDENTIALS;

/**
 * Created by akokala on 11/6/2017.
 */

public class ProfileServiceImpl implements ProfileService {

    private static ProfileService profileService;
    private String userProfileRequestBody;

    private ProfileServiceImpl() {
    }


    public static ProfileService getInstance() {
        if (profileService == null) {
            profileService = new ProfileServiceImpl();
        }
        return profileService;
    }

    @Override
    public UserProfileRequestModel getRequestBody(String userName, String password, String name, String email, String mobile, String alternativeNum) {
        UserProfileRequestModel userProfile = new UserProfileRequestModel();

        userProfile.setUsername(userName);
        userProfile.setPassword(password);
        userProfile.setName(name);
        userProfile.setEmail(email);
        userProfile.setMobile(mobile);
        userProfile.setAlternate(alternativeNum);

        userProfileRequestBody = new Gson().toJson(userProfile);
        return userProfile;
    }

    @Override
    public void fetchUserDetails(Context context, String userName, final IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put(Utility.USERNAME, userName);
        ServiceLocatorImpl.getInstance().executeGetRequest(context, null, URLs.URL_FETCH_USERS, params, AuthType.USER_CREDENTIALS, null,
                new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
                    @Override
                    public void onSuccess(UserProfileResponseModel[] response) {
                        if (response != null) {
                            Session.getInstance().setUserProfileResponseModel(response[0]);
                            responseListener.onSuccess(response);
                        } else {
                            IDAREErrorWrapper idareErrorWrapper = new IDAREErrorWrapper("Unexpected Response null from Server", null);
                            errorListener.onError(idareErrorWrapper);
                        }
                    }
                }, new IDAREResponseHandler.ErrorListener() {
                    @Override
                    public void onError(IDAREErrorWrapper error) {
                        errorListener.onError(error);
                    }
                });
    }

    @Override
    public void postProfileDetails(Context context, UserProfileRequestModel userProfile, final IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        ServiceLocatorImpl.getInstance().executePostRequest(context, URLs.URL_CREATE_ACCOUNT, null, APP_CREDENTIALS, null, userProfileRequestBody, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel>() {
            @Override
            public void onSuccess(UserProfileResponseModel response) {
                if (response != null) {
                    Session.getInstance().setUserProfileResponseModel(response);
                    responseListener.onSuccess(response);
                } else {
                    IDAREErrorWrapper idareErrorWrapper = new IDAREErrorWrapper("Unexpected Response null from Server", null);
                    errorListener.onError(idareErrorWrapper);
                }
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                errorListener.onError(error);
            }
        });
    }

    @Override
    public void checkIfPasswordExists(final Context context,String userName, String password, final IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put(Utility.USERNAME, userName);
        ServiceLocatorImpl.getInstance().executeGetRequest(context, password, URLs.URL_IS_PASSWORD_EXISTS, params, USER_CREDENTIALS, null, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
            @Override
            public void onSuccess(UserProfileResponseModel[] response) {
                if (response != null && response.length > 0) {
                    Session.getInstance().setUserProfileResponseModel(response[0]);
                    PreferencesManager.getInstance(context).setUserProfileResponse(response[0]);
                    responseListener.onSuccess(response);
                } else {
                    IDAREErrorWrapper idareErrorWrapper = new IDAREErrorWrapper("Unexpected Response null from Server", null);
                    errorListener.onError(idareErrorWrapper);
                }
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                errorListener.onError(error);
            }
        });
    }

    @Override
    public void checkIfUserExists(final Context context, String username, final IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put(Utility.USERNAME, username);
        ServiceLocatorImpl.getInstance().executeGetRequest(context, null, URLs.URL_IS_USER_EXISTS, params, MASTER_SECRET, null, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
            @Override
            public void onSuccess(UserProfileResponseModel[] response) {
                if (response != null) {
                    if (response.length > 0) {
                        Session.getInstance().setUserProfileResponseModel(response[0]);
                        PreferencesManager.getInstance(context).setUserProfileResponse(response[0]);
                    }
                    responseListener.onSuccess(response);
                } else {
                    IDAREErrorWrapper idareErrorWrapper = new IDAREErrorWrapper("Unexpected Response null from Server", null);
                    errorListener.onError(idareErrorWrapper);
                }
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                errorListener.onError(error);
            }
        });
    }
}
