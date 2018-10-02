package com.opensource.app.idare.viewmodel;

import android.content.Context;

/**
 * Created by akokala on 11/2/2017.
 */

public class InviteToIDareViewModel extends IDareBaseViewModel {
    private DataListener dataListener;

    public InviteToIDareViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {

    }
}
