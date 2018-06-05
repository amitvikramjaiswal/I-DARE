package com.opensource.app.idare.component.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Session;

/**
 * Created by ajaiswal on 1/25/2018.
 */

public class IDareLocationService extends Service {

    private static final String TAG = IDareLocationService.class.getSimpleName();
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1500;
    private static final float SMALLEST_DISPLACEMENT = 50.0f;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            updateUserLocation(locationResult.getLastLocation());
        }
    };

    public IDareLocationService() {
        super();
    }

    private void updateUserLocation(Location lastLocation) {
        Log.d(TAG, "**** LAT : " + lastLocation.getLatitude() + " **** LNG : " + lastLocation.getLongitude());
        Session.getInstance().setLocation(lastLocation);
        PreferencesManager.getInstance(this).updateUserLocation(lastLocation);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startLocationUpdates();
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // don't do anything if the app is in background
        } else {
            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
        Log.d(TAG, "@@@@ onDestroy() @@@@");
    }

    protected void stopLocationUpdates() {
        Log.d(TAG, "#### stopLocationUpdates() ####");
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
