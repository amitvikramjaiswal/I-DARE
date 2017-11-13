package com.opensource.app.idare.model.service;

import android.content.Context;

import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.utils.AuthType;

import java.util.Map;

public interface ServiceLocator {

    void executeGetRequest(Context context, URLs url, Map<String, String> params, AuthType authType, Map<String, String> headers, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void executePostRequest(Context context, URLs url, Map<String, String> params, AuthType authType, Map<String, String> headers, String body, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);

}
