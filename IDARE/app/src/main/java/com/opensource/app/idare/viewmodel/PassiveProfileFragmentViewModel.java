package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.View;

import com.opensource.app.idare.R;
import com.opensource.app.idare.utils.Utils;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;
import com.opensource.app.idare.view.fragment.ActiveProfileFragment;

/**
 * Created by akokala on 11/6/2017.
 */

public class PassiveProfileFragmentViewModel extends BaseViewModel {
    private DataListener dataListener;

    public PassiveProfileFragmentViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    // OnClick of Make Active
    public View.OnClickListener makeActiveClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Onclick  - Check location is enabled, then make app Active
                onMakeActiveClicked();
            }
        };
    }

    private void onMakeActiveClicked() {
        if (Utils.isLocationServicesEnabled(getContext())) {
            dataListener.replaceFragment(ActiveProfileFragment.newInstance());
        } else {
            dataListener.showAlertDialog(null, getContext().getResources().getString(R.string.active_mode_message_from_passive), false, getContext().getResources().getString(R.string.btn_ok),
                    getContext().getResources().getString(R.string.btn_cancel), new AlertDialogHandler() {
                        @Override
                        public void onPositiveButtonClicked() {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            dataListener.startActivity(intent);
                        }

                        @Override
                        public void onNegativeButtonClicked() {

                        }
                    });
        }
    }

    public interface DataListener {

        void replaceFragment(Fragment fragment);

        void startActivity(Intent intent);

        void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler);
    }
}
