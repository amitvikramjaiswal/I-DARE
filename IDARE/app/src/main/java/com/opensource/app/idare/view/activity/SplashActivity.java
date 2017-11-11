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

        finishOnUiThread(PreferencesManager.getInstance(this).isFirstLaunch());
    }

    /*
    * If User has done with registration, Then directly go to main Screen
    * */
    public void finishOnUiThread(final boolean isNotFirstLaunch) {
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             */
            @Override
            public void run() {
                finish();
                Intent i = null;
                if (isNotFirstLaunch) {
                   /* UserContext userContext = new Gson().fromJson(getPreferences().getString(Utility.KEY_USER_CONTEXT, null), UserContext.class);
                    userContext = userContext == null ? new UserContext() : userContext;
                    Session.setUserContext(userContext);*/
                    i = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    i = new Intent(SplashActivity.this, RegisterActivity.class);
                }
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(i);
            }
        }, Utility.SPLASH_TIME_OUT);
    }
}
