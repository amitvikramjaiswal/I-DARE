package com.opensource.app.idare.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.opensource.app.idare.R;
import com.opensource.app.idare.utils.Utils;
import com.opensource.app.idare.viewmodel.IDareBaseViewModel;
import com.opensource.library.sosmodelib.utils.AlertDialogHandler;
import com.opensource.library.sosmodelib.view.activity.BaseActivity;

import java.util.regex.Pattern;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public class IDareBaseActivity extends BaseActivity implements IDareBaseViewModel.DataListener {
    private static final String TAG = IDareBaseActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private AlertDialog.Builder myAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Utils.isLocationServicesEnabled(this)) {
            showAlertDialog(getString(R.string.error_title), getString(R.string.no_location_detected), false, getString(R.string.btn_ok), null, new AlertDialogHandler() {
                @Override
                public void onPositiveButtonClicked() {

                }

                @Override
                public void onNegativeButtonClicked() {

                }
            });
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showAlertDialog(View view, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }

        myAlertDialog = new AlertDialog.Builder(this, R.style.Theme_AlertDialogTheme);

        setPositiveButton(positiveButton, alertDialogHandler);
        setNegativeButton(negativeButton, alertDialogHandler, false);

        if (view != null) {
            myAlertDialog.setView(view);
        }

        myAlertDialog.setCancelable(false);

        alertDialog = myAlertDialog.create();

        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        alertDialog.show();
    }

    private void setNegativeButton(String negativeButton, final AlertDialogHandler alertDialogHandler, boolean cancelable) {
        if (negativeButton != null) {
            myAlertDialog.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {

                @Override
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
            myAlertDialog.setCancelable(cancelable);
        }
    }

    private void setPositiveButton(String positiveButton, final AlertDialogHandler alertDialogHandler) {
        if (positiveButton != null) {
            myAlertDialog.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    if (alertDialogHandler != null) {
                        alertDialogHandler.onPositiveButtonClicked();
                    }
                }
            });
        }
    }

    @Override
    public void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }

        myAlertDialog = new AlertDialog.Builder(this);

        if (title != null) {
            myAlertDialog.setTitle(title);
        }

        if (message != null) {
            SpannableString spannableString = new SpannableString(message);
            Pattern mobilePatern = Pattern.compile("((\\(\\d{3}\\) ?)|(\\d{3}-))?\\d{3}-\\d{4}");
            Linkify.addLinks(spannableString, mobilePatern, "");
            Linkify.addLinks(spannableString, Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
            myAlertDialog.setMessage(spannableString);
        }

        setPositiveButton(positiveButton, alertDialogHandler);
        setNegativeButton(negativeButton, alertDialogHandler, cancelable);
        alertDialog = myAlertDialog.create();
        alertDialog.show();
        ((TextView) (alertDialog.findViewById(android.R.id.message))).setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    @Override
    public void showSnackbar(final int mainTextStringId, final int actionStringId,
                             View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, R.style.MyTheme);
        }
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    @Override
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

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void shakeView(View view) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}