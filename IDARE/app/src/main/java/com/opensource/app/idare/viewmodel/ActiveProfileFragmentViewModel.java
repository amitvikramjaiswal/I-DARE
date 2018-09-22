package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.opensource.app.idare.component.service.FakeCallService;
import com.opensource.app.idare.model.data.entity.TriggerNotificationResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.Utils;

/**
 * Created by akokala on 10/31/2017.
 */

public class ActiveProfileFragmentViewModel extends BaseViewModel {

    private static final String TAG = ActiveProfileFragmentViewModel.class.getSimpleName();
    private Intent intent;
    private DataListener dataListener;

    public ActiveProfileFragmentViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
        if (!Utils.isMyServiceRunning(getContext(), FakeCallService.class)) {
            intent = new Intent(context, FakeCallService.class);
            dataListener.startService(intent);
        }
    }

    public View.OnClickListener onAlertClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionFacadeImpl.getInstance().initiateNotification(getContext(), new IDAREResponseHandler.ResponseListener<TriggerNotificationResponseModel>() {
                    @Override
                    public void onSuccess(TriggerNotificationResponseModel response) {
                        Log.d(TAG, "onSuccess(). " + response);
                    }
                }, new IDAREResponseHandler.ErrorListener() {
                    @Override
                    public void onError(IDAREErrorWrapper error) {

                    }
                });
            }
        };
    }

    public void onDestroy() {
        Log.d(TAG, TAG + " Destroyed");
        dataListener.stopService(intent);
    }

    public interface DataListener extends BaseViewModel.DataListener {

    }
}
