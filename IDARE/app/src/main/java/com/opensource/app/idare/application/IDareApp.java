package com.opensource.app.idare.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import io.fabric.sdk.android.Fabric;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public class IDareApp extends Application {
    public static IDareApp application;
    private static boolean isActive;

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
        private static int resumed;
        private static int paused;
        private static int started;
        private static int stopped;
        private LocationRequest locationRequest;
        private FusedLocationProviderClient fusedLocationProviderClient;
        private LocationCallback locationCallback;

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

        private static boolean isApplicationVisible() {
            return started > stopped;
        }

        private static boolean isApplicationInForeground() {
            return resumed > paused;
        }
    }
}
