package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.opensource.app.idare.R;
import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.Utils;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;
import com.opensource.app.idare.view.activity.MainActivity;

/**
 * Created by akokala on 11/6/2017.
 */

public class EditProfileViewModel extends BaseViewModel implements TextWatcher {
    private DataListener dataListener;

    private ObservableField<String> nameFromEditTExt = new ObservableField<>("");
    private ObservableField<String> emailFromEditText = new ObservableField<>("");
    private ObservableField<String> alternativeNumFromEditText = new ObservableField<>("");
    private ObservableField<String> passwordFromEditText = new ObservableField<>("");
    private ObservableField<Boolean> enableSaveButton = new ObservableField<>(false);

    private String phoneNumberFromBundle;

    public EditProfileViewModel(Context context, DataListener dataListener, String phoneNumberFromBundle) {
        super(context);
        this.dataListener = dataListener;
        this.phoneNumberFromBundle = phoneNumberFromBundle;

        setListener();
    }

    private void setListener() {
        dataListener.getName().addTextChangedListener(this);
        dataListener.getEmail().addTextChangedListener(this);
        dataListener.getAlternativeNumber().addTextChangedListener(this);
        dataListener.getPassword().addTextChangedListener(this);
    }

    public ObservableField<String> getNameFromEditTExt() {
        return nameFromEditTExt;
    }

    public ObservableField<String> getEmailFromEditText() {
        return emailFromEditText;
    }

    public ObservableField<String> getAlternativeNumFromEditText() {
        return alternativeNumFromEditText;
    }

    public ObservableField<String> getPasswordFromEditText() {
        return passwordFromEditText;
    }

    public ObservableField<Boolean> getEnableSaveButton() {
        return enableSaveButton;
    }

    // Set the value for name
    public TextWatcher nameTextChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                nameFromEditTExt.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    // Set the value for password
    public TextWatcher passwordTextChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                passwordFromEditText.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    // Set the value for mobile
    public TextWatcher alternativeNumTextChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                alternativeNumFromEditText.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    // Set the value for email
    public TextWatcher emailTextChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                emailFromEditText.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }


    // Service Call for Post profile details
    private void postProfileDetails() {
        dataListener.showProgress();
        UserProfileRequestModel userProfileBody = SessionFacadeImpl.getInstance().getRequestBody(phoneNumberFromBundle, passwordFromEditText.get(), nameFromEditTExt.get(),
                emailFromEditText.get(), phoneNumberFromBundle, alternativeNumFromEditText.get());
        SessionFacadeImpl.getInstance().postProfileDetails(getContext(), userProfileBody, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel>() {
            @Override
            public void onSuccess(UserProfileResponseModel response) {
                dataListener.hideProgress();
                //OnSuccess Handle
//                PreferencesManager.getInstance(getContext()).setUserContext();

                dataListener.hideKeyBoard();
                dataListener.finish();
                dataListener.startActivity(MainActivity.getStartIntent(getContext()));
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

    // Onclick Save Button
    public View.OnClickListener saveClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postProfileDetails();
            }
        };
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (Utils.emptyOrNullCheck(nameFromEditTExt.get().trim()) && Utils.emptyOrNullCheck(emailFromEditText.get().trim())
                && Utils.emptyOrNullCheck(alternativeNumFromEditText.get().trim()) && Utils.emptyOrNullCheck(passwordFromEditText.get().trim())) {
            enableSaveButton.set(true);
        } else {
            enableSaveButton.set(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public interface DataListener {

        void hideProgress();

        void showProgress();

        void hideKeyBoard();

        void finish();

        EditText getName();

        EditText getEmail();

        EditText getAlternativeNumber();

        EditText getPassword();

        void startActivity(Intent intent);

        void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler);
    }
}
