package com.opensource.app.idare.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.LayoutPopupViewBinding;

/**
 * Created by akokala on 11/7/2017.
 */

public class LayoutPopUpViewModel extends BaseViewModel {
    private static AlertDialog alertDialog;
    private DataListener dataListener;
    private ObservableField<String> passCodeEditText = new ObservableField<>("");

    public LayoutPopUpViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;

        alertDialog = new AlertDialog.Builder(getContext()).create();
    }

    @BindingAdapter("android:inflate")
    public static void inflateFabMenu(RelativeLayout relativeLayout, AlertDialog alertDialog) {
        LayoutPopupViewBinding layoutPopupViewBinding = DataBindingUtil.inflate(LayoutInflater.from(relativeLayout.getContext()), R.layout.layout_popup_view, relativeLayout, false);
        relativeLayout.addView(layoutPopupViewBinding.getRoot());

        alertDialog.setView(layoutPopupViewBinding.getRoot());
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alertDialog.show();
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

    public interface DataListener {

    }
}

