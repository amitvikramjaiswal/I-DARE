package com.opensource.app.idare.viewmodel;

import android.content.Context;

import com.opensource.app.idare.model.service.SessionFacade;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.library.sosmodelib.viewmodel.BaseViewModel;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public abstract class IDareBaseViewModel extends BaseViewModel {

    private final String TAG = "BaseViewModel";

    public IDareBaseViewModel(Context context) {
        super(context);
    }

    public Context getContext() {
        return context;
    }

    protected void destroy() {
        context = null;
    }

    protected SessionFacade getSessionFacade() {
        return SessionFacadeImpl.getInstance();
    }

    public interface DataListener extends BaseViewModel.DataListener {
    }

}
