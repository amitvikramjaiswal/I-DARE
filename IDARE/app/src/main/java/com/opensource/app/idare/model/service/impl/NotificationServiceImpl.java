package com.opensource.app.idare.model.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.opensource.app.idare.model.data.entity.RegisterDevice;
import com.opensource.app.idare.model.data.entity.RegisterDeviceResponse;
import com.opensource.app.idare.model.service.NotificationService;
import com.opensource.app.idare.model.service.URLs;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.Session;
import com.opensource.app.idare.utils.Constants;

import static com.opensource.app.idare.utils.AuthType.USER_CREDENTIALS;

/**
 * Created by amitvikramjaiswal on 18/11/17.
 */

public class NotificationServiceImpl implements NotificationService {

    private static final String TAG = NotificationServiceImpl.class.getSimpleName();
    private static NotificationService notificationService;
    private static Gson gson;

    private NotificationServiceImpl() {
    }

    public static NotificationService getInstance() {
        if (notificationService == null) {
            notificationService = new NotificationServiceImpl();
            gson = new Gson();
        }
        return notificationService;
    }

    public static RegisterDevice getRequestBody(String regToken) {
        RegisterDevice registerDevice = new RegisterDevice();
        registerDevice.setDeviceId(regToken);
        registerDevice.setPlatform(Constants.ANDROID);
        registerDevice.setUserId(Session.getInstance().getUserProfileResponseModel().getId());
        return registerDevice;
    }

    @Override
    public void registerDeviceToFCM(Context context, RegisterDevice registerDevice, final IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        String body = gson.toJson(registerDevice);
        ServiceLocatorImpl.getInstance().executePostRequest(context, URLs.URL_REGISTER_FOR_PUSH, null, USER_CREDENTIALS, null, body, new IDAREResponseHandler.ResponseListener<RegisterDeviceResponse>() {
            @Override
            public void onSuccess(RegisterDeviceResponse response) {
                Log.d(TAG, "****** SUCCESSFULLY REGISTERED " + response.getDeviceId() + " ******");
                Session.getInstance().setRegistered(true);
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                Log.e(TAG, "@@@@@@ ERROR REGISTERING " + error.getException().getMessage() + " @@@@@@");
            }
        });
    }

    @Override
    public void unregisterDeviceToFCM(Context context, RegisterDevice registerDevice, IDAREResponseHandler.ResponseListener<RegisterDeviceResponse> responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        String body = gson.toJson(registerDevice);
        ServiceLocatorImpl.getInstance().executePostRequest(context, URLs.URL_UNREGISTER_FOR_PUSH, null, USER_CREDENTIALS, null, body, new IDAREResponseHandler.ResponseListener<RegisterDeviceResponse>() {
            @Override
            public void onSuccess(RegisterDeviceResponse response) {
                Log.d(TAG, "****** SUCCESSFULLY UNREGISTERED " + response.getDeviceId() + " ******");
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                Log.e(TAG, "@@@@@@ ERROR UNREGISTERING " + error.getException().getMessage() + " @@@@@@");
            }
        });
    }
}
