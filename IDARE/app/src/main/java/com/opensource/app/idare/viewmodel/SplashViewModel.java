package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.NotificationServiceImpl;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.view.activity.MainActivity;

/**
 * Created by akokala on 11/2/2017.
 */

public class SplashViewModel extends BaseViewModel {
    private DataListener dataListener;

    public SplashViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
        checkLoginStatus();
    }

    public void checkLoginStatus() {
        if (PreferencesManager.getInstance(getContext()).wasUserLoggedIn()) {
            // User has already crated the account - Fetch User Call - Redirect to Home screen
            login();
        } else {
            dataListener.finishOnUiThread();
        }
    }

    private void login() {
        SessionFacadeImpl.getInstance().login(getContext(), PreferencesManager.getInstance(getContext()).getUsername(),
                PreferencesManager.getInstance(getContext()).getUserPass(), new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
                    @Override
                    public void onSuccess(UserProfileResponseModel[] response) {
                        Intent intent = MainActivity.getStartIntent(getContext(), PreferencesManager.getInstance(getContext()).getUsername());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        dataListener.startActivity(intent);
                        dataListener.finish();
//                        registerDeviceToFcm();
                    }
                }, new IDAREResponseHandler.ErrorListener() {
                    @Override
                    public void onError(IDAREErrorWrapper error) {
                        // TODO: 18/11/17 handle offline behavior
                    }
                });
    }

//    private void registerDeviceToFcm() {
//        String token = FirebaseInstanceId.getInstance().getToken();
//        SessionFacadeImpl.getInstance().registerDeviceToFCM(getContext(), NotificationServiceImpl.getRequestBody(token), null, null);
//    }

    public interface DataListener extends BaseViewModel.DataListener {
        void finishOnUiThread();
    }
}
