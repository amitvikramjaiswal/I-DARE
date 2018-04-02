package com.opensource.app.idare.application;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Session;
import com.opensource.app.idare.view.activity.SplashActivity;

import io.fabric.sdk.android.Fabric;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public class IDareApp extends Application {
    public static IDareApp application;
    private static boolean isActive;

    private static Activity activity;
    private static Context context;
    private static Context applicationContext;

    public static IDareApp getInstance() {
        if (application == null) {
            application = new IDareApp();
            applicationContext = application.getApplicationContext();
        }
        return application;
    }

    public static boolean isActive() {
        return isActive;
    }

    public static void setIsActive(boolean isActive) {
        IDareApp.isActive = isActive;
    }

    public static Context getContext() {
        if (context != null) {
            return context;
        }
        return applicationContext;
    }

    public static Activity getCurrentActivity() {
        return activity;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = this.getApplicationContext();
        registerActivityLifecycleCallbacks(new IDareLifeCycle());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
    }

    private static class IDareLifeCycle implements ActivityLifecycleCallbacks {
        private static final String TAG = IDareLifeCycle.class.getSimpleName();
        private static int resumed;
        private static int paused;
        private static int started;
        private static int stopped;

        private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1234;
        private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1 * 60 * 60 * 1000; // 1 hour
        private static final float SMALLEST_DISPLACEMENT_METERS = 1.0f * 1000.0f; // 1 kilometer

        private FusedLocationProviderClient fusedLocationProviderClient;
        private LocationRequest locationRequest;
        private LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                updateUserLocation(locationResult.getLastLocation());
            }
        };

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            // No-op
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            // No-op
        }

        @Override
        public void onActivityResumed(Activity activity) {
            ++resumed;
            IDareApp.activity = activity;
            if (activity instanceof SplashActivity) {
                startLocationUpdates();
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            ++paused;
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            // No-op
        }

        @Override
        public void onActivityStarted(Activity activity) {
            ++started;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            ++stopped;
        }

        private void startLocationUpdates() {
            if (fusedLocationProviderClient == null) {
                fusedLocationProviderClient = new FusedLocationProviderClient(getContext());
            }
            if (locationRequest == null) {
                createLocationRequest();
            }
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                } else {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }
            } else {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        }

        private void createLocationRequest() {
            locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
            locationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT_METERS);
        }

        private void updateUserLocation(Location lastLocation) {
            Log.d(TAG, "**** LAT : " + lastLocation.getLatitude() + " **** LNG : " + lastLocation.getLongitude());
            Session.getInstance().setLocation(lastLocation);
            PreferencesManager.getInstance(getContext()).updateUserLocation(lastLocation);
        }

        private static boolean isApplicationVisible() {
            return started > stopped;
        }

        private static boolean isApplicationInForeground() {
            return resumed > paused;
        }
    }
}
