package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.ActivityRegisterBinding;
import com.opensource.app.idare.viewmodel.RegisterViewModel;
import com.opensource.app.idare.viewmodel.to.RegisterTO;

/**
 * Created by amitvikramjaiswal on 25/05/16.
 */
public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        viewModel = new RegisterViewModel(this, new RegisterTO());
        binding.setViewModel(viewModel);
    }
}
