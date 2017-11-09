package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.LayoutPopupViewBinding;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;

/**
 * Created by akokala on 11/7/2017.
 */

public class LayoutPopUpViewModel extends BaseViewModel {
    private static android.support.v7.app.AlertDialog.Builder myAlertDialog;
    private DataListener dataListener;
    private ObservableField<String> passCodeEditText = new ObservableField<>("");
    private ObservableField<Integer>  alertVisibility  = new ObservableField<>(View.GONE);

    public LayoutPopUpViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    @BindingAdapter("android:inflate")
    public static void inflateFabMenu(RelativeLayout relativeLayout, LayoutPopUpViewModel layoutPopUpViewModel) {
        LayoutPopupViewBinding layoutPopupViewBinding = DataBindingUtil.inflate(LayoutInflater.from(relativeLayout.getContext()), R.layout.layout_popup_view, relativeLayout, false);
        layoutPopupViewBinding.setViewModel(layoutPopUpViewModel);

        showAlertDialog(layoutPopupViewBinding.getRoot(), getContext().getString(R.string.positive_button), null, new AlertDialogHandler() {
            @Override
            public void onPositiveButtonClicked() {
            }

            @Override
            public void onNegativeButtonClicked() {
                //Negative Button Clicked
            }
        });

    }

    public static void showAlertDialog(View view, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        android.support.v7.app.AlertDialog alertDialog = null;

        if (alertDialog != null) {
            alertDialog.dismiss();
        }

        myAlertDialog = new AlertDialog.Builder(getContext(), R.style.Theme_AlertDialogTheme);

        setPositiveButton(positiveButton, alertDialogHandler);
        setNegativeButton(negativeButton, alertDialogHandler, false);

        if (view != null) {
            myAlertDialog.setView(view);
        }

        myAlertDialog.setCancelable(false);

        alertDialog = myAlertDialog.create();

        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();
    }

    private static void setNegativeButton(String negativeButton, final AlertDialogHandler alertDialogHandler, boolean cancelable) {
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

    private static void setPositiveButton(String positiveButton, final AlertDialogHandler alertDialogHandler) {
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

    //Onclick make passcode button
    public View.OnClickListener onClickMakePassive() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    // Add textWatcher for passCode
    public TextWatcher passCodeChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                passCodeEditText.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    public ObservableField<String> getPassCodeEditText() {
        return passCodeEditText;
    }

    public ObservableField<Integer> getAlertVisibility() {
        return alertVisibility;
    }

    public interface DataListener {

    }
}

