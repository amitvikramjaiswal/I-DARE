package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.ActivitySplashBinding;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.viewmodel.SplashViewModel;

import static com.opensource.app.idare.application.IDareApp.getContext;

/**
 * Created by akokala on 11/2/2017.
 */

public class SplashActivity extends BaseActivity implements SplashViewModel.DataListener {
    private static final String TAG = SplashActivity.class.getSimpleName();
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
        context = getApplicationContext();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        viewModel = new SplashViewModel(this, this);
        binding.setViewModel(viewModel);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                Intent intent = RegisterActivity.getStartIntent(getContext());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, Constants.SPLASH_TIME_OUT);
    }
}
