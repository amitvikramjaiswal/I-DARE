package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.LayoutPopupViewBinding;
import com.opensource.library.sosmodelib.utils.AlertDialogHandler;

/**
 * Created by akokala on 11/7/2017.
 */

public class LayoutPopUpViewModel extends IDareBaseViewModel {
    private static android.support.v7.app.AlertDialog.Builder myAlertDialog;
    private DataListener dataListener;
    private ObservableField<String> passCodeEditText = new ObservableField<>("");

    public LayoutPopUpViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
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


    public void showAlertDialog() {
        LayoutPopupViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_popup_view, null, false);
        binding.setViewModel(new LayoutPopUpViewModel(getContext(), null));
        dataListener.showAlertDialog(binding.getRoot(), null, null, new AlertDialogHandler() {
            @Override
            public void onPositiveButtonClicked() {
            }

            @Override
            public void onNegativeButtonClicked() {
                //Negative Button Clicked

            }
        });
    }


    public interface DataListener extends IDareBaseViewModel.DataListener {

        void finish();
    }
}

