package com.opensource.app.idare.model.service;

import android.content.Context;

import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;

import java.util.Map;

public interface ServiceLocator {

    void executeGetRequest(Context context, URLs url, Map<String, String> params, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener, Map<String, String> headers);

    void executeGetRequest(Context context, URLs url, Map<String, String> params, Map<String, String> headers, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void executeGetStringRequest(Context context, URLs url, Map<String, String> params, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void executePostRequest(Context context, URLs url, Map<String, String> params, String body, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);

    void executePostRequest(Context context, URLs url, Map<String, String> params, Map<String, String> headers, String body, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);
}