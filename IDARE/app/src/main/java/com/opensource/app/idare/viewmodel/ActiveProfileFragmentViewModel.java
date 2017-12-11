package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.widget.Toast;

import com.opensource.app.idare.utils.ShakeEventManager;
import com.opensource.app.idare.utils.Utils;

/**
 * Created by akokala on 10/31/2017.
 */

public class ActiveProfileFragmentViewModel extends BaseViewModel implements ShakeEventManager.ShakeListener {

    private ShakeEventManager shakeEventManager;
    private DataListener dataListener;

    public ActiveProfileFragmentViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
        shakeEventManager = new ShakeEventManager();
        shakeEventManager.setListener(this);
        shakeEventManager.init(context);
        shakeEventManager.register();
    }

    @Override
    public void onShake() {
//        Toast.makeText(getContext(), "SHAKING", Toast.LENGTH_SHORT).show();
        Utils.fakeCall(getContext());
    }

    public void onDestroy() {
        shakeEventManager.deregister();
    }

    public interface DataListener {

    }
}
