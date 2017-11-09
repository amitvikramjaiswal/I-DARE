package com.opensource.app.idare.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.opensource.app.idare.model.data.entity.IDAREError;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.Utility;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public class IDareApp extends Application {
    public static IDareApp application;
    private static boolean isActive;

    private static Context context;
    private static Context applicationContext;
    private static UserProfileResponseModel userProfileResponseModel;

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

    public static UserProfileResponseModel getUserProfileResponseModel() {
        return userProfileResponseModel;
    }

    public static void setUserProfileResponseModel(UserProfileResponseModel userProfileResponseModel) {
        IDareApp.userProfileResponseModel = userProfileResponseModel;
    }

    public static boolean isConnectedToInternet(IDAREResponseHandler.ErrorListener errorListener) {
        if (context == null) {
            return false;
        }

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            return true;

        } else if (errorListener != null) {
            IDAREError idareError = new IDAREError();
            idareError.setErrorCode(Utility.NO_NETWORK_ERROR_CODE);
            errorListener.onError(new IDAREErrorWrapper(idareError, null));

        }
        return false;
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
