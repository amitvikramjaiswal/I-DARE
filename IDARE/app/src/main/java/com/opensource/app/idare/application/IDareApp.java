package com.opensource.app.idare.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public class IDareApp extends MultiDexApplication {
    private static final String TAG = "IDareApp";
    private static Context context;
//    private static UserContext userContext;
    private static boolean isActive;

    public static boolean isActive() {
        return isActive;
    }

    public static void setIsActive(boolean isActive) {
        IDareApp.isActive = isActive;
    }

//    public static UserContext getUserContext() {
//        return userContext;
//    }

//    public static void setUserContext(UserContext userContext) {
//        IDareApp.userContext = userContext;
//    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        IDareApp.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
        Fabric.with(this, new Crashlytics());
        final Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e("UNCAUGHT EXCEPTION", thread.toString());
                Log.e("UNCAUGHT EXCEPTION", ex.toString());
                // chain this so the app ends correctly
                handler.uncaughtException(thread, ex);
            }
        });

    }
}
