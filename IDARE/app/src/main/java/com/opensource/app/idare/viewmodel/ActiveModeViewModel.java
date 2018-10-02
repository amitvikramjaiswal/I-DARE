package com.opensource.app.idare.viewmodel;

import android.content.Context;

public class ActiveModeViewModel extends IDareBaseViewModel {

    private static final String TAG = ActiveModeViewModel.class.getSimpleName();
    private DataListener dataListener;

    public ActiveModeViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {

    }

}
