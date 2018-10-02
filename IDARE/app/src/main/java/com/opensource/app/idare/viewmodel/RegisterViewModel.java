package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.opensource.app.idare.R;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.Utils;
import com.opensource.app.idare.view.activity.EditProfileActivity;
import com.opensource.app.idare.view.activity.MainActivity;
import com.opensource.library.sosmodelib.utils.AlertDialogHandler;

/**
 * Created by amitvikramjaiswal on 25/05/16.
 */
public class RegisterViewModel extends IDareBaseViewModel {
    private DataListener dataListener;
    private Context context;

    private ObservableField<Integer> visibilityOfSendVerification = new ObservableField<>(View.VISIBLE);
    private ObservableField<String> phoneNumber = new ObservableField<>("");
    private ObservableField<Integer> visibilityOfVerify = new ObservableField<>(View.GONE);
    private ObservableField<String> otpText = new ObservableField<>("");

    private ObservableField<Integer> visibilityOfPassword = new ObservableField<>(View.GONE);
    private ObservableField<String> password = new ObservableField<>("");

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

    public ObservableField<Integer> getVisibilityOfPassword() {
        return visibilityOfPassword;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public ObservableField<Integer> getVisibilityOfVerify() {
        return visibilityOfVerify;
    }

    public ObservableField<String> getOtpText() {
        return otpText;
    }

    // Onclick of Continue - After entering Password
    public View.OnClickListener onClickPassword() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the entered password is correct, If wrong-Show Alert Message
                // If Yes - FetchUser Details
                login();
            }
        };
    }

    // Onclick of Send Verification
    public View.OnClickListener onClickSendVerification() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check User is created or not,
                // If Yes - User enters password - Redirect to Main Screen
                // If NO - User enters OTP - Normal Flow
                if (Utils.hasContent(phoneNumber.get()) && phoneNumber.get().length() == 10) {
                    serviceCallIsUserExists();
                } else {
                    dataListener.shakeView(dataListener.getPhoneNumber());
                }
            }
        };
    }

    private void serviceCallIsUserExists() {
        dataListener.hideKeyBoard();
        dataListener.showProgress();
        getSessionFacade().checkIfUserExists(getContext(), phoneNumber.get(), new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
            @Override
            public void onSuccess(UserProfileResponseModel[] response) {
                dataListener.hideKeyBoard();
                dataListener.hideProgress();
                if (response != null && response.length > 0) {
                    if (response[0].getUsername().equalsIgnoreCase(phoneNumber.get())) {
                        // Show Password Screen
                        visibilityOfSendVerification.set(View.GONE);
                        visibilityOfVerify.set(View.GONE);
                        visibilityOfPassword.set(View.VISIBLE);
                    }
                } else {
                    // User is not created - Normal flow
                    visibilityOfSendVerification.set(View.VISIBLE);
                    visibilityOfVerify.set(View.GONE);
                    visibilityOfPassword.set(View.VISIBLE);
                    onSendVerificationClick(phoneNumber.get());
                }

            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                dataListener.hideProgress();
                dataListener.hideKeyBoard();
                if (!Utils.isNetworkAvailable(getContext())) {
                    dataListener.showAlertDialog(getContext().getResources().getString(R.string.error_title),
                            getContext().getResources().getString(R.string.network_error_message), false, getContext().getResources().getString(R.string.positive_button),
                            null, new AlertDialogHandler() {
                                @Override
                                public void onPositiveButtonClicked() {
                                }

                                @Override
                                public void onNegativeButtonClicked() {
                                }
                            });
                } else {
                    dataListener.showAlertDialog(getContext().getResources().getString(R.string.error_title),
                            getContext().getResources().getString(R.string.error_message), false, getContext().getResources().getString(R.string.positive_button),
                            null, new AlertDialogHandler() {
                                @Override
                                public void onPositiveButtonClicked() {
                                }

                                @Override
                                public void onNegativeButtonClicked() {
                                }
                            });
                }
            }
        });
    }

    // Service call if password exists
    private void login() {
        dataListener.hideKeyBoard();
        dataListener.showProgress();
        SessionFacadeImpl.getInstance().login(getContext(), phoneNumber.get(), password.get(), new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
            @Override
            public void onSuccess(UserProfileResponseModel[] response) {
                dataListener.hideKeyBoard();
                dataListener.hideProgress();
                if (response != null) {
                    // If data is present - Go to next Screen - Store details in Session
                    dataListener.finish();
                    dataListener.startActivity(MainActivity.getStartIntent(getContext(), response[0].getName()));
//                    registerDeviceToFcm();
                }
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                dataListener.hideProgress();
                dataListener.hideKeyBoard();
                if (!Utils.isNetworkAvailable(getContext())) {
                    dataListener.showAlertDialog(getContext().getResources().getString(R.string.error_title),
                            getContext().getResources().getString(R.string.network_error_message), false, getContext().getResources().getString(R.string.positive_button),
                            null, new AlertDialogHandler() {
                                @Override
                                public void onPositiveButtonClicked() {
                                }

                                @Override
                                public void onNegativeButtonClicked() {
                                }
                            });
                } else if (error.getException() instanceof AuthFailureError) {
                    // Invalid Credentials - Show dialog saying invalid credentials
                    dataListener.showAlertDialog(getContext().getResources().getString(R.string.app_name),
                            getContext().getResources().getString(R.string.invalid_credentials), false, getContext().getResources().getString(R.string.positive_button),
                            null, new AlertDialogHandler() {
                                @Override
                                public void onPositiveButtonClicked() {
                                }

                                @Override
                                public void onNegativeButtonClicked() {
                                }
                            });
                } else {
                    dataListener.showAlertDialog(getContext().getResources().getString(R.string.error_title),
                            getContext().getResources().getString(R.string.error_message), false, getContext().getResources().getString(R.string.positive_button),
                            null, new AlertDialogHandler() {
                                @Override
                                public void onPositiveButtonClicked() {
                                }

                                @Override
                                public void onNegativeButtonClicked() {
                                }
                            });
                }
            }
        });
    }

//    private void registerDeviceToFcm() {
//        String token = FirebaseInstanceId.getInstance().getToken();
//        SessionFacadeImpl.getInstance().registerDeviceToFCM(context, NotificationServiceImpl.getRequestBody(token), null, null);
//    }

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

    // Set the value for otp in TextWatcher
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

    // Set value for password in Textwatcher
    public TextWatcher onPasswordChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password.set(s.toString());
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
        if (Utils.hasContent(otp) && otp.equalsIgnoreCase(Constants.OTP_VALIDATION)) {
            dataListener.hideKeyBoard();
            onVerifyButtonClick();
        } else {
            dataListener.shakeView(dataListener.getOtp());
        }
    }

    private void onVerifyButtonClick() {
        dataListener.finish();
        dataListener.startActivity(EditProfileActivity.getStartIntent(getContext(), phoneNumber.get()));
    }

    private void onSendVerificationClick(String phoneNumber) {
        visibilityOfPassword.set(View.GONE);
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
        visibilityOfPassword.set(View.GONE);
        visibilityOfVerify.set(View.VISIBLE);
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {

        EditText getPhoneNumber();

        EditText getOtp();

    }
}
