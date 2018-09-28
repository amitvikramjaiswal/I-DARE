package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opensource.app.idare.R;
import com.opensource.app.idare.model.data.entity.NearBySafeHouseListEntity;
import com.opensource.app.idare.model.data.entity.NearBySafeHouseResultEntity;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.Session;

import java.util.ArrayList;
import java.util.List;

import static com.opensource.app.idare.utils.SearchPlace.BUS_STATION;
import static com.opensource.app.idare.utils.SearchPlace.CAFE;
import static com.opensource.app.idare.utils.SearchPlace.CONVENIENCE_STORE;
import static com.opensource.app.idare.utils.SearchPlace.HOSPITAL;
import static com.opensource.app.idare.utils.SearchPlace.POLICE_STATION;
import static com.opensource.app.idare.utils.SearchPlace.RESTAURANT;
import static com.opensource.app.idare.utils.SearchPlace.SHOPPING_MALL;

/**
 * Created by ajaiswal on 4/4/2016.
 */
public class NearBySafeHouseActivity extends MapActivity {

    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    private static final String TAG = "NearBySafeHouseActivity";

    protected Location mCurrentLocation;

    protected String mLastUpdateTime;

    private String myLocation;
    private List<NearBySafeHouseResultEntity> nearBySafeHouses;
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
        nearBySafeHouses = new ArrayList<>();
        if (session.getLastLocation() != null) {
            myLocation = String.format("%s,%s", session.getLastLocation().getLatitude(), session.getLastLocation().getLongitude());
            getNearBySafeHouses("5000", null);
        }
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

    private void getNearBySafeHouses(String radius, final String nextPageToken) {
        SessionFacadeImpl.getInstance().getNearBySafeHouses(getApplicationContext(), getString(R.string.google_map_api_key), myLocation, radius, getSearchPlace(), nextPageToken, new IDAREResponseHandler.ResponseListener<NearBySafeHouseListEntity>() {
            @Override
            public void onSuccess(final NearBySafeHouseListEntity nearBySafeHouses) {
                if (nearBySafeHouses != null && !nearBySafeHouses.getNearBySafeHouseResultEntities().isEmpty()) {
                    if (nextPageToken == null) {
                        googleMap.clear();
                        mMarkers = new ArrayList<>();
                    }
                    addMarkers(nearBySafeHouses.getNearBySafeHouseResultEntities());
                    if (nearBySafeHouses.getNextPageToken() != null && !nearBySafeHouses.getNextPageToken().isEmpty()) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getNearBySafeHouses("5000", nearBySafeHouses.getNextPageToken());
                            }
                        }, 2000);
                    }
                }
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    private String getSearchPlace() {
        String searchPlace = String.format("%s|%s|%s|%s|%s|%s|%s",
                SHOPPING_MALL.getValue(), POLICE_STATION.getValue(), CAFE.getValue(),
                BUS_STATION.getValue(), HOSPITAL.getValue(), RESTAURANT.getValue(), CONVENIENCE_STORE.getValue());
        return searchPlace;
    }

    private String getSearchRadius() {
        int radius = session.getRadius() * 1000;
        return Integer.toString(radius == 0 ? 5000 : radius);
    }

    public void addMarkers(List<NearBySafeHouseResultEntity> nearBySafeHouses) {
        for (NearBySafeHouseResultEntity entity : nearBySafeHouses) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(entity.getGeometry().getLocation().getLatitude(), entity.getGeometry().getLocation().getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(getIconForType(entity.getTypes())))
                    .title(entity.getName())
                    .snippet(entity.getVicinity());
            mMarkers.add(addMarkers(markerOptions));
        }
        setBounds();
    }

    private int getIconForType(List<String> types) {
        if (types.contains("police")) {
            return R.mipmap.police;
        } else if (types.contains("shopping_mall") || types.contains("convenience_store")) {
            return R.mipmap.shopping;
        } else if (types.contains("cafe")) {
            return R.mipmap.cafe;
        } else if (types.contains("bus_station")) {
            return R.mipmap.bus;
        } else if (types.contains("hospital")) {
            return R.mipmap.hospital;
        } else if (types.contains("restaurant")) {
            return R.mipmap.restaurant;
        } else {
            return R.mipmap.safe_house;
        }
    }

    public void setBounds() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : mMarkers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 50;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cameraUpdate);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }
}
