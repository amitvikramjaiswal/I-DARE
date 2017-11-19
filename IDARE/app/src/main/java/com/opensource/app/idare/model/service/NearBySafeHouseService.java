package com.opensource.app.idare.model.service;

import android.content.Context;

import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;

/**
 * Created by amitvikramjaiswal on 19/11/17.
 */

public interface NearBySafeHouseService {

    void getNearBySafeHouses(Context context, String key, String location, String radius, String type, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener);

}
