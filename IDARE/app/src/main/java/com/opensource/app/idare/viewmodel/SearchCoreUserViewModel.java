package com.opensource.app.idare.viewmodel;

import android.content.Context;

/**
 * Created by amitvikramjaiswal on 19/11/17.
 */

public class SearchCoreUserViewModel extends IDareBaseViewModel {
    private DataListener dataListener;

    public SearchCoreUserViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {

    }
}
