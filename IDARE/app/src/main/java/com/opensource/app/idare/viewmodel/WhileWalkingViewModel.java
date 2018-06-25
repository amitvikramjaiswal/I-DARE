package com.opensource.app.idare.viewmodel;

import android.content.Context;

public class WhileWalkingViewModel extends BaseViewModel {

    private static final String TAG = WhileWalkingViewModel.class.getSimpleName();
    private DataListener dataListener;

    public WhileWalkingViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    public interface DataListener extends BaseViewModel.DataListener{

    }
}
