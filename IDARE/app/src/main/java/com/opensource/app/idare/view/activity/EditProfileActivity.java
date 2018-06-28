package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.ActivityEditProfileBinding;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.viewmodel.EditProfileViewModel;

/**
 * Created by akokala on 11/6/2017.
 */

public class EditProfileActivity extends BaseActivity implements EditProfileViewModel.DataListener {
    private ActivityEditProfileBinding binding;
    private EditProfileViewModel viewModel;

    public static Intent getStartIntent(Context context, String phoneNumber) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtra(Constants.PHONE_NUMBER, phoneNumber);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        String phoneNumber = getIntent().getStringExtra(Constants.PHONE_NUMBER);
        viewModel = new EditProfileViewModel(this, this, phoneNumber);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public EditText getName() {
        return binding.etName;
    }

    @Override
    public EditText getEmail() {
        return binding.etEmail;
    }

    @Override
    public EditText getAlternativeNumber() {
        return binding.etAlternateNumber;
    }

    @Override
    public EditText getPassword() {
        return binding.etPassword;
    }

}
