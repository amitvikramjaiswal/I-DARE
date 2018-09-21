package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.opensource.app.idare.component.service.FakeCallService;
import com.opensource.app.idare.utils.ShakeEventManager;
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
        if (!FakeCallService.isIsRunning()) {
            intent = new Intent(context, FakeCallService.class);
            dataListener.startService(intent);
        }
    }

    public void onDestroy() {
        Log.d(TAG, TAG + " Destroyed");
        dataListener.startService(intent);
    }

    public interface DataListener extends BaseViewModel.DataListener {

    }
}
