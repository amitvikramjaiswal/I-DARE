package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.ActivitySplashBinding;
import com.opensource.app.idare.utils.Utility;
import com.opensource.app.idare.viewmodel.SplashViewModel;

/**
 * Created by akokala on 11/2/2017.
 */

public class SplashActivity extends BaseActivity implements SplashViewModel.DataListener {
    private ActivitySplashBinding binding;
    private SplashViewModel viewModel;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        viewModel = new SplashViewModel(this, this);
        binding.setViewModel(viewModel);

        finishOnUiThread();
    }

    public void finishOnUiThread() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = MainActivity.getStartIntent(SplashActivity.this);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        }, Utility.SPLASH_SLEEP_TIME);
    }
}
