package com.opensource.app.idare.viewmodel;

import android.content.Context;

public class ActiveModeViewModel extends IDareBaseViewModel {

    private static final String TAG = ActiveModeViewModel.class.getSimpleName();

    public ActiveModeViewModel(Context context) {
        super(context);
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {

    }

}
