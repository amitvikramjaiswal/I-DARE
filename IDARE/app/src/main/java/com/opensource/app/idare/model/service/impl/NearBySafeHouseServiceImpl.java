package com.opensource.app.idare.model.service.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.opensource.app.idare.model.data.entity.NearBySafeHouseListEntity;
import com.opensource.app.idare.model.data.entity.NearBySafeHouseResultEntity;
import com.opensource.app.idare.model.service.NearBySafeHouseService;
import com.opensource.app.idare.model.service.URLs;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.utils.IDAREErrorWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by amitvikramjaiswal on 19/11/17.
 */

public class NearBySafeHouseServiceImpl implements NearBySafeHouseService {

    private static final String TAG = NotificationServiceImpl.class.getSimpleName();
    private static NearBySafeHouseService nearBySafeHouseService;
    private static Gson gson;
    private List<NearBySafeHouseResultEntity> resultEntities;

    private NearBySafeHouseServiceImpl() {
        resultEntities = new ArrayList<>();
    }

    public static NearBySafeHouseService getInstance() {
        if (nearBySafeHouseService == null) {
            nearBySafeHouseService = new NearBySafeHouseServiceImpl();
            gson = new Gson();
        }
        return nearBySafeHouseService;
    }

    @Override
    public void getNearBySafeHouses(final Context context, final String key, final String location, final String radius, final String type, final String nextPageToken, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        Map<String, String> params= new HashMap<>();
        params.put("key", key);
        params.put("location", location);
        params.put("radius", radius);
        params.put("type", type);
        if (!TextUtils.isEmpty(nextPageToken)) {
            params.put("pagetoken", nextPageToken);
        }
        ServiceLocatorImpl.getInstance().executeGetRequest(context, URLs.URL_NEAR_BY_SEARCH_BASE_URL, params, null, null, new IDAREResponseHandler.ResponseListener<NearBySafeHouseListEntity>() {
            @Override
            public void onSuccess(NearBySafeHouseListEntity safeHouses) {
                Log.d(TAG, safeHouses.getNearBySafeHouseResultEntities().size() + "");
                Log.d(TAG, safeHouses.getStatus());
                resultEntities.addAll(safeHouses.getNearBySafeHouseResultEntities());
                if (!TextUtils.isEmpty(safeHouses.getNextPageToken())) {
                    getNearBySafeHouses(context, key, location, radius, type, safeHouses.getNextPageToken(), responseListener, errorListener);
                } else {
                    responseListener.onSuccess(resultEntities);
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
