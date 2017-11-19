package com.opensource.app.idare.model.service;

import android.content.Context;

import com.opensource.app.idare.model.data.entity.RegisterDevice;
import com.opensource.app.idare.model.data.entity.RegisterDeviceResponse;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;

/**
 * Created by amitvikramjaiswal on 18/11/17.
 */

public interface NotificationService {

    void registerDeviceToFCM(Context context, RegisterDevice registerDevice, IDAREResponseHandler.ResponseListener<RegisterDeviceResponse> responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void unregisterDeviceToFCM(Context context, RegisterDevice registerDevice, IDAREResponseHandler.ResponseListener<RegisterDeviceResponse> responseListener, IDAREResponseHandler.ErrorListener errorListener);

}
