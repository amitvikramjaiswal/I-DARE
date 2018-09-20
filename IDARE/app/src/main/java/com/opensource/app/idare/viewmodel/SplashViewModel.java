package com.opensource.app.idare.viewmodel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.opensource.app.idare.BuildConfig;
import com.opensource.app.idare.R;
import com.opensource.app.idare.component.service.IDareLocationService;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.NotificationServiceImpl;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;
import com.opensource.app.idare.view.activity.MainActivity;

/**
 * Created by akokala on 11/2/2017.
 */

public class SplashViewModel extends BaseViewModel {
    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final String TAG = SplashViewModel.class.getSimpleName();
    private boolean mAlreadyStartedService = false;
    private DataListener dataListener;

    public SplashViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
        checkLocationSettings();
    }

    private void checkLocationSettings() {
        startStep1();
    }

    private void startStep1() {

        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {

            //Passing null to indicate that it is executing for the first time.
            startStep2();

        } else {
//            Toast.makeText(getApplicationContext(), R.string.no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(getContext());
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(dataListener.getActivity(), status, 2404).show();
            }
            return false;
        }
        return true;
    }

    /**
     * Step 2: Check & Prompt Internet connection
     */
    private Boolean startStep2() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }

        //Yes there is active internet connection. Next check Location is granted by user or not.

        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startStep3();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }

    /**
     * Show A Dialog with button to refresh the internet state.
     */
    private void promptInternetConnect() {
        dataListener.showAlertDialog(getContext().getString(R.string.no_internet), getContext().getString(R.string.msg_alert_no_internet), false, getContext().getString(R.string.btn_ok), null, new AlertDialogHandler() {
            @Override
            public void onPositiveButtonClicked() {
                //Block the Application Execution until user grants the permissions
                if (startStep2()) {

                    //Now make sure about location permission.
                    if (checkPermissions()) {

                        //Step 2: Start the Location Monitor Service
                        //Everything is there to start the service.
                        startStep3();
                    } else if (!checkPermissions()) {
                        requestPermissions();
                    }

                } else {
                    dataListener.finish();
                }
            }

            @Override
            public void onNegativeButtonClicked() {

            }
        });
    }

    /**
     * Step 3: Start the Location Monitor Service
     */
    private void startStep3() {

        //And it will be keep running until you close the entire application from task manager.
        //This method will executed only once.

        if (!mAlreadyStartedService) {

//            mMsgView.setText(R.string.msg_location_service_started);

            //Start location sharing service to app server.........
            Intent intent = new Intent(getContext(), IDareLocationService.class);
            getContext().startService(intent);

            mAlreadyStartedService = true;
            //Ends................................................

            checkLoginStatus();
        }
    }

    /**
     * Start permissions requests.
     */
    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(dataListener.getActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(dataListener.getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION);


        // Provide an additional rationale to the img_user. This would happen if the img_user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            dataListener.showSnackbar(R.string.permission_rationale,
                    R.string.btn_ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(dataListener.getActivity(),
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the img_user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(dataListener.getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }

    /**
     * Callback received when a permissions request has been completed.
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If img_user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(TAG, "Permission granted, updates requested, starting location updates");
                startStep3();

            } else {
                // Permission denied.

                // Notify the img_user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the img_user for permission (device policy or "Never ask
                // again" prompts). Therefore, a img_user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                dataListener.showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                dataListener.startActivity(intent);
                            }
                        });
            }
        }
    }


    public void checkLoginStatus() {
        if (PreferencesManager.getInstance(getContext()).wasUserLoggedIn()) {
            // User has already crated the account - Fetch User Call - Redirect to Home screen
            login();
        } else {
            dataListener.finishOnUiThread();
        }
    }

    private void login() {
        SessionFacadeImpl.getInstance().login(getContext(), PreferencesManager.getInstance(getContext()).getUsername(),
                PreferencesManager.getInstance(getContext()).getUserPass(), new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
                    @Override
                    public void onSuccess(UserProfileResponseModel[] response) {
                        Intent intent = MainActivity.getStartIntent(getContext(), PreferencesManager.getInstance(getContext()).getUsername());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        dataListener.startActivity(intent);
                        dataListener.finish();
                        registerDeviceToFcm();
                    }
                }, new IDAREResponseHandler.ErrorListener() {
                    @Override
                    public void onError(IDAREErrorWrapper error) {
                        // TODO: 18/11/17 handle offline behavior
                    }
                });
    }

    private void registerDeviceToFcm() {
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null)
            SessionFacadeImpl.getInstance().registerDeviceToFCM(getContext(), NotificationServiceImpl.getRequestBody(token), null, null);
    }

    public interface DataListener extends BaseViewModel.DataListener {
        void finishOnUiThread();
    }
}
