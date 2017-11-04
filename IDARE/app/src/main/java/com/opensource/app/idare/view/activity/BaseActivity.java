package com.opensource.app.idare.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.opensource.app.idare.R;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;
import com.opensource.app.idare.viewmodel.BaseViewModel;

import java.util.regex.Pattern;

/**
 * Created by amitvikramjaiswal on 24/05/16.
 */
public class BaseActivity extends AppCompatActivity implements BaseViewModel.DataListener {
    private ProgressDialog progressDialog;
    private android.support.v7.app.AlertDialog alertDialog;
    private android.support.v7.app.AlertDialog.Builder myAlertDialog;
    private SharedPreferences preferences;
    private static final String PREF_KEY = "com.opensource.app.idare.PREF_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    @Override
    public SharedPreferences getPreferences() {
        return preferences;
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

        myAlertDialog = new android.support.v7.app.AlertDialog.Builder(this);

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

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, R.style.AppTheme);
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
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    @Override
    public void shakeView(View view) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);
    }
}