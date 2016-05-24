package com.opensource.app.idare.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.opensource.app.idare.R;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public boolean checkGooglePlayServices() {
        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (errorCode == ConnectionResult.SUCCESS) {
            return true;
        } else {
            Dialog d = GooglePlayServicesUtil.getErrorDialog(errorCode, this, 0);
            if (d != null) {
                d.show();
            } else {
                showToast("Could not initialize Google Play Services.");
            }
            return false;
        }
    }

    public void showProgressDialog(String status) {
        hideProgressDialog();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.progress_bar_message));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this
                .getSystemService(INPUT_METHOD_SERVICE);
        View currentFocus = this.getCurrentFocus();
        if (currentFocus != null) {
            IBinder windowToken = this.getCurrentFocus().getWindowToken();
            if (windowToken != null) {
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }

    public void showAlertDialog(String title, String message, String positiveButton, String negativeButton, final AlertDialogHandler alertDialogHandler) {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);


        myAlertDialog.setTitle(getResources().getString(R.string.app_name));

        if (message != null) {
            myAlertDialog.setMessage(message);
        }

        if (positiveButton != null) {
            myAlertDialog.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface arg0, int arg1) {
                    if (alertDialogHandler != null) {
                        alertDialogHandler.onPositiveButtonClicked();
                    }
                }
            });
        }

        if (negativeButton != null) {
            myAlertDialog.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface arg0, int arg1) {
                    if (alertDialogHandler != null) {
                        alertDialogHandler.onNegativeButtonClicked();
                    }
                }
            });
            myAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    alertDialogHandler.onNegativeButtonClicked();
                }
            });
        } else {
            myAlertDialog.setCancelable(false);
        }

        myAlertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
