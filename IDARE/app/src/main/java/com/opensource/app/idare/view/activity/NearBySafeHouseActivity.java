package com.opensource.app.idare.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
public class NearBySafeHouseActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    private static final String TAG = "NearBySafeHouseActivity";
    private static final float SMALLEST_DISPLACEMENT = 250.0f;

    protected Location mCurrentLocation;
    protected Location mLastLocation;

    protected String mLastUpdateTime;

    private GoogleMap googleMap;
    private String myLocation;
    private List<Marker> mMarkers;
    private Session session;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, NearBySafeHouseActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_safe_house);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Safe House");
        session = Session.getInstance();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLastUpdateTime = "";

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);
        myLocation = String.format("%s,%s", session.getLastLocation().getLatitude(), session.getLastLocation().getLongitude());
        getNearBySafeHouses("5000");
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

    private void getNearBySafeHouses(String radius) {
        SessionFacadeImpl.getInstance().getNearBySafeHouses(getApplicationContext(), getString(R.string.google_map_api_key), myLocation, radius, getSearchPlace(), new IDAREResponseHandler.ResponseListener<NearBySafeHouseListEntity>() {
            @Override
            public void onSuccess(NearBySafeHouseListEntity nearBySafeHouses) {
                System.out.println("RESULT " + nearBySafeHouses);
                if (nearBySafeHouses.getNearBySafeHouseResultEntities() != null && !nearBySafeHouses.getNearBySafeHouseResultEntities().isEmpty()) {
                    googleMap.clear();
                    addMarkers(nearBySafeHouses);
                } else {
                    getNearBySafeHouses("10000");
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
                SHOPPING_MALL.getValue(),POLICE_STATION.getValue(), CAFE.getValue(),
                BUS_STATION.getValue(), HOSPITAL.getValue(), RESTAURANT.getValue(), CONVENIENCE_STORE.getValue());
        return searchPlace;
    }

    private String getSearchRadius() {
        int radius = session.getRadius() * 1000;
        return Integer.toString(radius == 0 ? 5000 : radius);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
    }

    public void addMarkers(NearBySafeHouseListEntity nearBySafeHouses) {
        mMarkers = new ArrayList<>();
        for (NearBySafeHouseResultEntity entity : nearBySafeHouses.getNearBySafeHouseResultEntities()) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(entity.getGeometry().getLocation().getLatitude(), entity.getGeometry().getLocation().getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(getIconForType(entity.getTypes())))
                    .title(entity.getName())
                    .snippet(entity.getVicinity());
            mMarkers.add(googleMap.addMarker(markerOptions));
        }
        setBounds();
    }

    private int getIconForType(List<String> types) {
        if (types.contains("shopping_mall") || types.contains("convenience_store")) {
            return R.mipmap.shopping;
        } else if (types.contains("police")){
            return R.mipmap.police;
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

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = mCurrentLocation = session.getLastLocation();
        if (mLastLocation != null) {
            myLocation = String.format("%s,%s", mLastLocation.getLatitude(), mLastLocation.getLongitude());
            Toast.makeText(this, myLocation, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public boolean onMyLocationButtonClick() {
        mGoogleApiClient.connect();
        return true;
    }
}
