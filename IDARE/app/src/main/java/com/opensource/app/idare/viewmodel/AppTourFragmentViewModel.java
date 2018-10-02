package com.opensource.app.idare.viewmodel;

import android.content.Context;

/**
 * Created by akokala on 10/31/2017.
 */

public class AppTourFragmentViewModel extends IDareBaseViewModel {
    private DataListener dataListener;

    public AppTourFragmentViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {

    }
}
