package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;

import com.opensource.app.idare.utils.handler.AlertDialogHandler;

/**
 * Created by akokala on 11/2/2017.
 */

public class SplashViewModel extends BaseViewModel {
    private DataListener dataListener;

    public SplashViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;

    }

    public interface DataListener {

        void startActivity(Intent intent);

        void hideKeyBoard();

        void showProgress();

        void hideProgress();

        void finish();

        void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler);
    }
}
