package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.opensource.app.idare.R;
import com.opensource.app.idare.utils.Utility;
import com.opensource.app.idare.utils.Utils;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;

/**
 * Created by amitvikramjaiswal on 25/05/16.
 */
public class RegisterViewModel extends BaseViewModel {
    private DataListener dataListener;
    private Context context;

    private ObservableField<Integer> visibilityOfSendVerification = new ObservableField<>(View.VISIBLE);
    private ObservableField<String> phoneNumber = new ObservableField<>("");
    private ObservableField<Integer> visibilityOfVerify = new ObservableField<>(View.GONE);
    private ObservableField<String> otpText = new ObservableField<>("");

    public RegisterViewModel(Context context, DataListener dataListener) {
        super(context);
        this.context = context;
        this.dataListener = dataListener;
    }

    public ObservableField<Integer> getVisibilityOfSendVerification() {
        return visibilityOfSendVerification;
    }

    public ObservableField<String> getPhoneNumber() {
        return phoneNumber;
    }

    public ObservableField<Integer> getVisibilityOfVerify() {
        return visibilityOfVerify;
    }

    public ObservableField<String> getOtpText() {
        return otpText;
    }

    // Onclick of Send Verification
    public View.OnClickListener onClickSendVerification() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendVerificationClick(phoneNumber.get());
            }
        };
    }

    // Set the value for phoneNumber in TextWatcher
    public TextWatcher onPhoneNumberChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                phoneNumber.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    // Set the value for Otp in TextWatcher
    public TextWatcher onOtpChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                otpText.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    // Onclick of Verify
    public View.OnClickListener onclickOfVerify() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVerifyClick(otpText.get());
            }
        };
    }

    private void onVerifyClick(String otp) {
        if (Utils.hasContent(otp) && otp.equalsIgnoreCase(Utility.OTP_VALIDATION)) {
            dataListener.hideKeyBoard();
            onVerifyButtonClick();
        } else {
            dataListener.shakeView(dataListener.getOtp());
        }
    }

    private void onVerifyButtonClick() {
        /*dataListener.finish();
        registerPresenter.saveUserData(etPhoneNumber.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Utility.KEY_SHOW_EDIT_PROFILE, true);
        startActivity(intent);*/
    }

    private void onSendVerificationClick(String phoneNumber) {
        if (Utils.hasContent(phoneNumber) && phoneNumber.length() == 10) {
            dataListener.hideKeyBoard();
            dataListener.showAlertDialog(getContext().getResources().getString(R.string.app_name), getContext().getResources().getString(R.string.verify_confirmation_message, phoneNumber), false, getContext().getResources().getString(R.string.btn_ok), getContext().getResources().getString(R.string.btn_edit), new AlertDialogHandler() {
                @Override
                public void onPositiveButtonClicked() {
                    // SuccessFully entering the phoneNumber, Move to verify Screen,
                    // Also , get a otp to Phone
                    onSendVerificationClick();
                }

                @Override
                public void onNegativeButtonClicked() {

                }
            });
        } else {
            dataListener.shakeView(dataListener.getPhoneNumber());
        }
    }

    public void onSendVerificationClick() {
        visibilityOfSendVerification.set(View.GONE);
        visibilityOfVerify.set(View.VISIBLE);
    }

    public interface DataListener {

        void hideKeyBoard();

        EditText getPhoneNumber();

        EditText getOtp();

        void finish();

        void startActivity(Intent intent);

        void shakeView(View view);

        void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler);
    }
}
