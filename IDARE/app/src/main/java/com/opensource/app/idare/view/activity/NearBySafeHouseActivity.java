package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.maps.android.clustering.ClusterManager;
import com.opensource.app.idare.model.data.entity.NearBySafeHouseResultEntity;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajaiswal on 4/4/2016.
 */
public class NearBySafeHouseActivity extends MapActivity {

    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    private static final String TAG = "NearBySafeHouseActivity";

    protected Location mCurrentLocation;

    protected String mLastUpdateTime;

    private Session session;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, NearBySafeHouseActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = Session.getInstance();

        setTitle("Safe House");
        mLastUpdateTime = "";

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);
        if (session.getLastLocation() != null) {
            getNearBySafeHouses();
        }
    }

    @Override
    protected void setUpClusterer() {
        clusterManager = new ClusterManager<NearBySafeHouseResultEntity>(this, googleMap);
        super.setUpClusterer();
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
        }
    }

    private void getNearBySafeHouses() {
        mMarkers = new ArrayList<>();
        SessionFacadeImpl.getInstance().initiateSafeHousesSearch(getApplicationContext(), new IDAREResponseHandler.ResponseListener<List<NearBySafeHouseResultEntity>>() {
            @Override
            public void onSuccess(final List<NearBySafeHouseResultEntity> nearBySafeHouses) {
                if (nearBySafeHouses != null && !nearBySafeHouses.isEmpty()) {
                    if (clusterManager == null) {
                        setUpClusterer();
                    }
                    addMarkers(nearBySafeHouses);
                }
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    public void addMarkers(List<NearBySafeHouseResultEntity> nearBySafeHouses) {
        addClusterItem(nearBySafeHouses);
        setBounds(nearBySafeHouses);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }
}
