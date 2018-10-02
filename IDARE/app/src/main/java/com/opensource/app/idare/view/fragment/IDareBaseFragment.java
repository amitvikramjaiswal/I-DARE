package com.opensource.app.idare.view.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.view.View;

import com.opensource.app.idare.viewmodel.IDareBaseViewModel;
import com.opensource.library.sosmodelib.utils.AlertDialogHandler;
import com.opensource.library.sosmodelib.view.fragment.BaseFragment;

/**
 * Created by ajaiswal on 5/27/2016.
 */
public class IDareBaseFragment extends BaseFragment implements IDareBaseViewModel.DataListener {

    public IDareBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        parentActivity.showAlertDialog(title, message, cancelable, positiveButton, negativeButton, alertDialogHandler);
    }

    @Override
    public void showSnackbar(int mainTextStringId, int actionStringId, View.OnClickListener listener) {
        parentActivity.showSnackbar(mainTextStringId, actionStringId, listener);
    }

    @Override
    public void showProgress() {
        parentActivity.showProgress();
    }

    @Override
    public void hideProgress() {
        parentActivity.hideProgress();
    }

    @Override
    public void hideKeyBoard() {
        parentActivity.hideKeyBoard();
    }

    @Override
    public void shakeView(View view) {
        parentActivity.shakeView(view);
    }

    @Override
    public ComponentName startService(Intent intent) {
        return context.startService(intent);
    }

    @Override
    public boolean stopService(Intent intent) {
        return context.stopService(intent);
    }

    @Override
    public void showAlertDialog(View view, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        parentActivity.showAlertDialog(view, positiveButton, negativeButton, alertDialogHandler);
    }

    @Override
    public void finish() {
        parentActivity.finish();
    }
}
