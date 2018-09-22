package com.opensource.app.idare.component.adapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.opensource.app.idare.model.data.entity.IDareLocation;
import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.SessionFacade;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Session;

public class LocationSyncAdapter extends AbstractThreadedSyncAdapter {

    private static String TAG = LocationSyncAdapter.class.getSimpleName();
    private Context context;
    private IDareLocation lastLocation;

    public LocationSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.context = context;
    }

    public LocationSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        this.context = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        lastLocation = PreferencesManager.getInstance(context).getLastLocation();
        if (lastLocation != null) {
            fetchUserIfNeeded();
        }
    }

    private void fetchUserIfNeeded() {
        UserProfileResponseModel model = Session.getInstance().getUserProfileResponseModel();
        if (model != null) {
            updateLocation(model.getId(), model.getUsername(), model.getPassword(), model.getName(), model.getEmail(), model.getMobile(), model.getAlternate(), lastLocation);
        }
    }

    private void updateLocation(String id, String username, String password, String name, String email, String mobile, String alternate, IDareLocation lastLocation) {
        UserProfileRequestModel requestBody = SessionFacadeImpl.getInstance().getRequestBody(username, password, name, email, mobile, alternate, lastLocation);

        SessionFacadeImpl.getInstance().updateProfile(context, id, requestBody, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel>() {
            @Override
            public void onSuccess(UserProfileResponseModel response) {
                Log.d(TAG, "Profile Updated.");
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                Log.e(TAG, "Failed to update profile.");
            }
        });
    }
}
