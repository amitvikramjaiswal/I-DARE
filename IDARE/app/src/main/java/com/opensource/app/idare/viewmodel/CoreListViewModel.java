package com.opensource.app.idare.viewmodel;

import android.content.Context;

/**
 * Created by akokala on 11/2/2017.
 */

public class CoreListViewModel extends BaseViewModel {
    private DataListener dataListener;

    public CoreListViewModel(Context context,DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    public interface DataListener{

    }
}
