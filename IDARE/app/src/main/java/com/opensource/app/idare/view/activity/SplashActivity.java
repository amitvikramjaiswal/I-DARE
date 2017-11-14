package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.ActivitySplashBinding;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Utility;
import com.opensource.app.idare.viewmodel.SplashViewModel;

import static com.opensource.app.idare.application.IDareApp.getContext;

/**
 * Created by akokala on 11/2/2017.
 */

public class SplashActivity extends BaseActivity implements SplashViewModel.DataListener {
    private ActivitySplashBinding binding;
    private SplashViewModel viewModel;
    private Context context;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        viewModel = new SplashViewModel(this, this);
        context = getApplicationContext();
        binding.setViewModel(viewModel);

        finishOnUiThread();
    }

    public void checkLoginStatus() {
        if (PreferencesManager.getInstance(context).getUserDetails() != null
                && PreferencesManager.getInstance(context).getUserDetails().getUsername() != null
                && PreferencesManager.getInstance(context).getUserDetails().getPassword() != null) {
            // User has already crated the account - Fetch User Call - Redirect to Home screen
            Intent intent = MainActivity.getStartIntent(getContext(), null);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Intent intent = RegisterActivity.getStartIntent(context);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    /*
    * If User has done with registration, Then directly go to main Screen
    * */
    public void finishOnUiThread() {
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             */
            @Override
            public void run() {
                finish();
                checkLoginStatus();
            }
        }, Utility.SPLASH_TIME_OUT);
    }
}
