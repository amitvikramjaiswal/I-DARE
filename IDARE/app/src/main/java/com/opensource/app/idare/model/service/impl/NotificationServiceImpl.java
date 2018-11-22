package com.opensource.app.idare.model.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.opensource.app.idare.model.data.entity.IDareLocation;
import com.opensource.app.idare.model.data.entity.RegisterDevice;
import com.opensource.app.idare.model.data.entity.RegisterDeviceResponse;
import com.opensource.app.idare.model.data.entity.TriggerNotificationRequestModel;
import com.opensource.app.idare.model.data.entity.TriggerNotificationResponseModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.NotificationService;
import com.opensource.app.idare.model.service.URLs;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.pojo.NotificationItem;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ServiceLocatorImpl.getInstance().executePostRequest(context, URLs.URL_REGISTER_FOR_PUSH, null, USER_CREDENTIALS, null, body, (IDAREResponseHandler.ResponseListener<RegisterDeviceResponse>) response -> {
            Log.d(TAG, "****** SUCCESSFULLY REGISTERED " + response.getDeviceId() + " ******");
            Session.getInstance().setRegistered(true);
        }, error -> Log.e(TAG, "@@@@@@ ERROR REGISTERING " + error.getException().getMessage() + " @@@@@@"));
    }

    @Override
    public void unregisterDeviceToFCM(Context context, RegisterDevice registerDevice, IDAREResponseHandler.ResponseListener<RegisterDeviceResponse> responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        String body = gson.toJson(registerDevice);
        ServiceLocatorImpl.getInstance().executePostRequest(context, URLs.URL_UNREGISTER_FOR_PUSH, null, USER_CREDENTIALS, null, body, (IDAREResponseHandler.ResponseListener<RegisterDeviceResponse>) response -> Log.d(TAG, "****** SUCCESSFULLY UNREGISTERED " + response.getDeviceId() + " ******"), error -> Log.e(TAG, "@@@@@@ ERROR UNREGISTERING " + error.getException().getMessage() + " @@@@@@"));
    }

    @Override
    public void initiateNotification(final Context context, final IDAREResponseHandler.ResponseListener<TriggerNotificationResponseModel> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        SessionFacadeImpl.getInstance().fetchNearByUsers(context, (IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>) arrUsers -> {
            Log.d(TAG, Arrays.toString(arrUsers));
            triggerNotification(context, arrUsers, responseListener, errorListener);
        }, error -> Log.e(TAG, error.toString()));
    }

    private void triggerNotification(Context context, UserProfileResponseModel[] arrUsers, IDAREResponseHandler.ResponseListener<TriggerNotificationResponseModel> responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        TriggerNotificationRequestModel model = getTriggerNotificationBody(context, arrUsers);
        String body = gson.toJson(model);
        ServiceLocatorImpl.getInstance().executePostRequest(context, URLs.URL_TRIGGER_NOTIFICATION, null, USER_CREDENTIALS, null, body, (IDAREResponseHandler.ResponseListener<TriggerNotificationResponseModel>) response -> Log.d(TAG, "onSuccess(). " + response), error -> Log.e(TAG, "onError(). " + error.getMessage()));
    }

    private TriggerNotificationRequestModel getTriggerNotificationBody(Context context, UserProfileResponseModel[] arrUsers) {
        IDareLocation location = PreferencesManager.getInstance(context).getLastLocation();
        UserProfileResponseModel me = Session.getInstance().getUserProfileResponseModel();
        String name = me.getName();
        String id = me.getId();
        List<String> userIds = new ArrayList<>();
        for (UserProfileResponseModel user : arrUsers) {
            if (!user.getId().equals(me.getId()))
                userIds.add(user.getId());
        }

        return new TriggerNotificationRequestModel(userIds.toArray(new String[userIds.size()]), name, location, id, new Date().getTime());
    }

    @Override
    public void fetchNotifications(Context context, final IDAREResponseHandler.ResponseListener<List<NotificationItem>> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("recipientId", Session.getInstance().getUserProfileResponseModel().getId());
        ServiceLocatorImpl.getInstance().executeGetRequest(context, URLs.URL_FETCH_NOTIFICATIONS, params, USER_CREDENTIALS, null, (IDAREResponseHandler.ResponseListener<NotificationItem[]>) response -> {
            Log.d(TAG, "Notifications - " + Arrays.toString(response));
            responseListener.onSuccess(Arrays.asList(response));
        }, error -> {
            Log.e(TAG, "Error fetching notifications - " + error.getMessage());
            errorListener.onError(error);
        });
    }
}
