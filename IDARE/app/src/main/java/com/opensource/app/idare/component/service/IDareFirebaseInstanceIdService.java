package com.opensource.app.idare.component.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.opensource.app.idare.model.service.impl.NotificationServiceImpl;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.Session;

/**
 * Created by amitvikramjaiswal on 05/11/17.
 */

public class IDareFirebaseInstanceIdService extends FirebaseInstanceIdService {


    private static final String TAG = IDareFirebaseInstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        if (Session.getInstance().getUserProfileResponseModel() != null)
            SessionFacadeImpl.getInstance().registerDeviceToFCM(getApplicationContext(), NotificationServiceImpl.getRequestBody(refreshedToken), null, null);
    }
}
