package com.opensource.app.idare.viewmodel;

import android.content.Context;

public class SafePracticesViewModel extends BaseViewModel {
    public SafePracticesViewModel(Context context, DataListener dataListener) {
        super(context);
    }

    public interface DataListener extends BaseViewModel.DataListener {

    }
}
