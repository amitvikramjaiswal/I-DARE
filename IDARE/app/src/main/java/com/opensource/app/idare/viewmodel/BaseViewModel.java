package com.opensource.app.idare.viewmodel;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.opensource.app.idare.model.service.SessionFacade;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public abstract class BaseViewModel {

    private final String TAG = "BaseViewModel";
    private Context context;

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

        void showSnackbar(int mainTextStringId, int actionStringId, View.OnClickListener listener);

        void showProgress();

        void hideProgress();

        void hideKeyBoard();

        void startActivity(Intent startIntent);

        void shakeView(View view);

        void showAlertDialog(View view, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler);

        void finish();

        void startActivityForResult(Intent intent, int requestCode);

        ComponentName startService(Intent intent);

        Activity getActivity();

        boolean stopService(Intent intent);

    }

}
