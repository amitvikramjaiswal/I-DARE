package com.opensource.app.idare.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public class IDareApp extends Application {
    public static IDareApp application;

    private static Context context;
    private static Context applicationContext;

    public static IDareApp getInstance() {
        if (application == null) {
            application = new IDareApp();
            applicationContext = application.getApplicationContext();
        }
        return application;
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
        context = this.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
    }
}
