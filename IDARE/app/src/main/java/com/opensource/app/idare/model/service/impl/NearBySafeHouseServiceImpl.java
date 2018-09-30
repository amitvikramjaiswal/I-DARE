package com.opensource.app.idare.model.service.impl;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.opensource.app.idare.R;
import com.opensource.app.idare.model.data.entity.NearBySafeHouseListEntity;
import com.opensource.app.idare.model.data.entity.NearBySafeHouseResultEntity;
import com.opensource.app.idare.model.service.NearBySafeHouseService;
import com.opensource.app.idare.model.service.URLs;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.opensource.app.idare.utils.SearchPlace.BUS_STATION;
import static com.opensource.app.idare.utils.SearchPlace.CAFE;
import static com.opensource.app.idare.utils.SearchPlace.CONVENIENCE_STORE;
import static com.opensource.app.idare.utils.SearchPlace.HOSPITAL;
import static com.opensource.app.idare.utils.SearchPlace.POLICE_STATION;
import static com.opensource.app.idare.utils.SearchPlace.RESTAURANT;
import static com.opensource.app.idare.utils.SearchPlace.SHOPPING_MALL;

/**
 * Created by amitvikramjaiswal on 19/11/17.
 */

public class NearBySafeHouseServiceImpl implements NearBySafeHouseService {

    private static final String TAG = NearBySafeHouseServiceImpl.class.getSimpleName();
    private static NearBySafeHouseService nearBySafeHouseService;
    private static Gson gson;
    private int position = 0;
    private List<NearBySafeHouseResultEntity> arlSafeHouses;

    private NearBySafeHouseServiceImpl() {

    }

    public static NearBySafeHouseService getInstance() {
        if (nearBySafeHouseService == null) {
            nearBySafeHouseService = new NearBySafeHouseServiceImpl();
            gson = new Gson();
        }
        return nearBySafeHouseService;
    }

    private String getNextType(int position) {
        String[] types = new String[]{
                SHOPPING_MALL.getValue(), POLICE_STATION.getValue(), CAFE.getValue(),
                BUS_STATION.getValue(), HOSPITAL.getValue(), RESTAURANT.getValue(), CONVENIENCE_STORE.getValue()
        };
        return types[position];
    }

    @Override
    public void initiateSafeHousesSearch(final Context context, boolean isFreshCall, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        arlSafeHouses = arlSafeHouses == null ? new ArrayList<NearBySafeHouseResultEntity>() : arlSafeHouses;
        if (isFreshCall) {
            position = 0;
            arlSafeHouses.clear();
        }
        String myLocation = String.format("%s,%s", PreferencesManager.getInstance(context).getLastLocation().getLatitude(), PreferencesManager.getInstance(context).getLastLocation().getLongitude());
        final Map<String, Object> params = new HashMap<>();
        params.put("key", context.getString(R.string.google_map_api_key));
        params.put("location", myLocation);
        params.put("radius", Constants.SEARCH_RADIUS);
        params.put("type", getNextType(position));
        Log.d(TAG, "$%$%$% " + params.get("type") + " $%$%$%");
        List<NearBySafeHouseResultEntity> arlSafeHousesForType = new ArrayList<>();
        getNearBySafeHouses(context, arlSafeHousesForType, params, null, new IDAREResponseHandler.ResponseListener<List<NearBySafeHouseResultEntity>>() {
            @Override
            public void onSuccess(List<NearBySafeHouseResultEntity> safeHouses) {
                position++;
                if (position <= 5) {
                    initiateSafeHousesSearch(context, false, responseListener, errorListener);
                }
                responseListener.onSuccess(safeHouses);
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                Log.e(TAG, error.getMessage());
                errorListener.onError(error);
            }
        });
    }

    public void getNearBySafeHouses(final Context context, final List<NearBySafeHouseResultEntity> arlSafeHousesForType, final Map<String, Object> params, final String nextPageToken, final IDAREResponseHandler.ResponseListener<List<NearBySafeHouseResultEntity>> responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        if (!TextUtils.isEmpty(nextPageToken)) {
            params.put("pagetoken", nextPageToken);
        }
        ServiceLocatorImpl.getInstance().executeGetRequest(context, URLs.URL_NEAR_BY_SEARCH_BASE_URL, params, null, null, new IDAREResponseHandler.ResponseListener<NearBySafeHouseListEntity>() {
            @Override
            public void onSuccess(final NearBySafeHouseListEntity safeHouses) {
                Log.d(TAG, "RESULT SIZE : " + safeHouses.getNearBySafeHouseResultEntities().size() + "");
                Log.d(TAG, safeHouses.getStatus());
                arlSafeHousesForType.addAll(safeHouses.getNearBySafeHouseResultEntities());
                if (safeHouses.getNextPageToken() != null && !safeHouses.getNextPageToken().isEmpty()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getNearBySafeHouses(context, arlSafeHousesForType, params, safeHouses.getNextPageToken(), responseListener, errorListener);
                        }
                    }, 2000);
                } else {
                    responseListener.onSuccess(arlSafeHousesForType);
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
