package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.ActivityRegisterBinding;
import com.opensource.app.idare.viewmodel.RegisterViewModel;

/**
 * Created by amitvikramjaiswal on 25/05/16.
 */
public class RegisterActivity extends IDareBaseActivity implements RegisterViewModel.DataListener {

    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;
    private Toolbar toolbar;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        viewModel = new RegisterViewModel(this, this);
        toolbar = (Toolbar) binding.toolbar.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.registration));
        setSupportActionBar(toolbar);
        binding.setViewModel(viewModel);
    }

    @Override
    public EditText getPhoneNumber() {
        return binding.etPhoneNumber;
    }


    @Override
    public EditText getOtp() {
        return binding.etOtpCode;
    }
}
