package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;

import com.opensource.app.idare.service.Impl.SessionFacadeImpl;
import com.opensource.app.idare.service.SessionFacade;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public abstract class BaseViewModel {

    private static final String TAG = "BaseViewModel";
    private static Context context;

    public BaseViewModel(Context context) {
        this.context = context;
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

    public interface DataListener {

        void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, final AlertDialogHandler alertDialogHandler);

        void showProgress();

        void hideProgress();

        void hideKeyBoard();

        void startActivity(Intent startIntent);
    }

}
