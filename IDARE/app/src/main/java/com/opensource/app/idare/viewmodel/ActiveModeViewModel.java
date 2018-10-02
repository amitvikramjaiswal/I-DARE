package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.util.Log;

import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.IDAREErrorWrapper;

import java.util.Arrays;
import java.util.List;

public class ActiveModeViewModel extends IDareBaseViewModel {

    private static final String TAG = ActiveModeViewModel.class.getSimpleName();
    private DataListener dataListener;

    public ActiveModeViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
        getNearByUsers();
    }

    private void getNearByUsers() {
        SessionFacadeImpl.getInstance().fetchNearByUsers(context, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
            @Override
            public void onSuccess(UserProfileResponseModel[] nearByUsers) {
                Log.d(TAG, nearByUsers.toString());
                dataListener.setUpClusterer();
                dataListener.addClusterItem(Arrays.asList(nearByUsers));
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {
        void addClusterItem(List<UserProfileResponseModel> nearByUsers);

        void setUpClusterer();
    }

}
