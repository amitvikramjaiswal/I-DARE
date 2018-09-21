package com.opensource.app.idare.component.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Session;

import static com.opensource.app.idare.utils.Constants.ACTION_LOCATION_BROADCAST;
import static com.opensource.app.idare.utils.Constants.EXTRA_LATITUDE;
import static com.opensource.app.idare.utils.Constants.EXTRA_LONGITUDE;
import static com.opensource.app.idare.utils.Constants.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;
import static com.opensource.app.idare.utils.Constants.SMALLEST_DISPLACEMENT;
import static com.opensource.app.idare.utils.Constants.UPDATE_INTERVAL_IN_MILLISECONDS;

/**
 * Created by ajaiswal on 1/25/2018.
 */

public class IDareLocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = IDareLocationService.class.getSimpleName();
    private static boolean isRunning;
    private GoogleApiClient mLocationClient;
    private LocationRequest mLocationRequest = new LocationRequest();
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Log.d(TAG, "Location changed");


            if (locationResult.getLastLocation() != null) {
                Log.d(TAG, "== location != null");

                //Send result to activities
                sendMessageToUI(locationResult.getLastLocation());
            }
        }
    };

    public static boolean isIsRunning() {
        return isRunning;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);


        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY; //by default
        //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes


        mLocationRequest.setPriority(priority);
        mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
        mLocationClient.connect();

        //Make it stick to the notification panel so it is less prone to get cancelled by the Operating System.
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * LOCATION CALLBACKS
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d(TAG, "== Error On onConnected() Permission not granted");
            //Permission not granted by user so cancel the further execution.

            return;
        }
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());

        Log.d(TAG, "Connected to Google API");
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    private void sendMessageToUI(Location location) {

        Log.d(TAG, "Sending info...");

        Session.getInstance().setLocation(location);
        PreferencesManager.getInstance(this).updateUserLocation(location);

//        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
//        intent.putExtra(EXTRA_LATITUDE, lat);
//        intent.putExtra(EXTRA_LONGITUDE, lng);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//
//        LocalBroadcastManager.getInstance(this).registerReceiver(
//                new BroadcastReceiver() {
//                    @Override
//                    public void onReceive(Context context, Intent intent) {
//                        String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
//                        String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);
//
//                        if (latitude != null && longitude != null) {
//                            mMsgView.setText(getString(R.string.msg_location_service_started) + "\n Latitude : " + latitude + "\n Longitude: " + longitude);
//                        }
//                    }
//                }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)
//        );
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Failed to connect to Google API");

    }

    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }
}
