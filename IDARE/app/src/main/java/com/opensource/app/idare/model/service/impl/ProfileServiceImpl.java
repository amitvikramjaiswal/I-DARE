package com.opensource.app.idare.model.service.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.opensource.app.idare.model.data.entity.IDareLocation;
import com.opensource.app.idare.model.data.entity.ProfilePic;
import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.ProfileService;
import com.opensource.app.idare.model.service.URLs;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.volley.MultipartRequest;
import com.opensource.app.idare.model.service.volley.VolleySingleton;
import com.opensource.app.idare.utils.AuthType;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.opensource.app.idare.utils.AuthType.APP_CREDENTIALS;
import static com.opensource.app.idare.utils.AuthType.MASTER_SECRET;
import static com.opensource.app.idare.utils.AuthType.USER_CREDENTIALS;
import static com.opensource.app.idare.utils.Constants.ID;
import static com.opensource.app.idare.utils.Constants.LATITUDE;
import static com.opensource.app.idare.utils.Constants.LONGITUDE;

/**
 * Created by akokala on 11/6/2017.
 */

public class ProfileServiceImpl implements ProfileService {

    private static final String TAG = ProfileServiceImpl.class.getSimpleName();
    private static ProfileService profileService;
    private String userProfileRequestBody;
    private String lineEnd = "\r\n";
    private String twoHyphens = "--";
    private String boundary = "*****";

    private ProfileServiceImpl() {
    }


    public static ProfileService getInstance() {
        if (profileService == null) {
            profileService = new ProfileServiceImpl();
        }
        return profileService;
    }

    @Override
    public UserProfileRequestModel getRequestBody(String userName, String password, String name, String email, String mobile, String alternativeNum, IDareLocation iDareLocation) {
        UserProfileRequestModel userProfile = new UserProfileRequestModel();

        userProfile.setUsername(userName);
        userProfile.setPassword(password);
        userProfile.setName(name);
        userProfile.setEmail(email);
        userProfile.setMobile(mobile);
        userProfile.setAlternate(alternativeNum);
        userProfile.setiDareLocation(iDareLocation);
        userProfile.setLonLat(new double[]{iDareLocation.getLongitude(), iDareLocation.getLatitude()});

        userProfileRequestBody = new Gson().toJson(userProfile);
        return userProfile;
    }

    @Override
    public void fetchUserDetails(Context context, String userName, final IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.USERNAME, userName);
        ServiceLocatorImpl.getInstance().executeGetRequest(context, URLs.URL_FETCH_USERS, params, AuthType.USER_CREDENTIALS, null,
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
    public void fetchNearByUsers(Context context, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        IDareLocation location = PreferencesManager.getInstance(context).getLastLocation();
        Map<String, Object> params = new HashMap<>();
        params.put(LONGITUDE, location.getLongitude());
        params.put(LATITUDE, location.getLatitude());
        ServiceLocatorImpl.getInstance().executeGetRequest(context, URLs.URL_FETCH_NEARBY_USERS, params, USER_CREDENTIALS, null, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
            @Override
            public void onSuccess(UserProfileResponseModel[] response) {
                Log.d(TAG, "onSuccess()");
                responseListener.onSuccess(response);
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                Log.e(TAG, "onError()");
                errorListener.onError(error);
            }
        });
    }

    @Override
    public void postProfileDetails(final Context context, final UserProfileRequestModel userProfile, final IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        ServiceLocatorImpl.getInstance().executePostRequest(context, URLs.URL_CREATE_ACCOUNT, null, APP_CREDENTIALS, null, userProfileRequestBody, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel>() {
            @Override
            public void onSuccess(UserProfileResponseModel response) {
                if (response != null) {
                    Session.getInstance().setUserProfileResponseModel(response);
                    PreferencesManager.getInstance(context).rememberForSingleSignOn(userProfile.getUsername(), userProfile.getPassword());
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
    public void login(final Context context, final String userName, final String password, final IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.USERNAME, userName);

        ServiceLocatorImpl.getInstance().login(context, userName, password, URLs.URL_IS_PASSWORD_EXISTS, params, USER_CREDENTIALS, null, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
            @Override
            public void onSuccess(UserProfileResponseModel[] response) {
                if (response != null && response.length > 0) {
                    Session.getInstance().setUserProfileResponseModel(response[0]);
                    PreferencesManager.getInstance(context).rememberForSingleSignOn(userName, password);
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
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.USERNAME, username);
        ServiceLocatorImpl.getInstance().executeGetRequest(context, URLs.URL_IS_USER_EXISTS, params, MASTER_SECRET, null, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
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

    @Override
    public void updateProfile(Context context, String id, UserProfileRequestModel userProfileRequestModel, final IDAREResponseHandler.ResponseListener<UserProfileResponseModel> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        Map<String, Object> params = new HashMap<>();
        params.put(ID, id);
        String body = new Gson().toJson(userProfileRequestModel);
        ServiceLocatorImpl.getInstance().executePutRequest(context, URLs.URL_UPDATE_PROFILE, params, USER_CREDENTIALS, null, body, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel>() {
            @Override
            public void onSuccess(UserProfileResponseModel response) {
                responseListener.onSuccess(response);
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                errorListener.onError(error);
            }
        });
    }

    @Override
    public void uploadProfilePic(Context context, ProfilePic profilePic, IDAREResponseHandler.ResponseListener<ProfilePic> responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream2);
        byte[] multipartBody;
        try {
            // the first file
            buildPart(dataOutputStream, profilePic.getArrByteImage(), profilePic.getId() + ".jpg");
            // send multipart form data necesssary after file data
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // pass to multipart body
            multipartBody = byteArrayOutputStream2.toByteArray();

            MultipartRequest multipartRequest = new MultipartRequest(URLs.URL_UPLOAD_PROFILE_PIC.fullURL(), ServiceLocatorImpl.getInstance().getCommonHeaders(USER_CREDENTIALS, context, null, null), "", multipartBody, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    Log.d(TAG, response.data.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        Log.e(TAG, error.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUserProfilePic() {

    }

    private void buildPart(DataOutputStream dataOutputStream, byte[] fileData, String fileName) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
                + fileName + "\"" + lineEnd);

        dataOutputStream.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileData);
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // read file and write it into form...
        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(lineEnd);
    }

}
