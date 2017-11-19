package com.opensource.app.idare.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.opensource.app.idare.utils.handler.AlertDialogHandler;
import com.opensource.app.idare.view.activity.BaseActivity;
import com.opensource.app.idare.viewmodel.BaseViewModel;

/**
 * Created by ajaiswal on 5/27/2016.
 */
public class BaseFragment extends Fragment implements BaseViewModel.DataListener {

    protected BaseActivity parentActivity;
    protected Context context;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        parentActivity.showAlertDialog(title, message, cancelable, positiveButton, negativeButton, alertDialogHandler);
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
    public void showAlertDialog(View view, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        parentActivity.showAlertDialog(view, positiveButton, negativeButton, alertDialogHandler);
    }

    @Override
    public void finish() {
        parentActivity.finish();
    }
}
