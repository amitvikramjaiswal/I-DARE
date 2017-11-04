package com.opensource.app.idare.viewmodel;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.opensource.app.idare.R;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;
import com.opensource.app.idare.view.activity.RegisterActivity;
import com.opensource.app.idare.view.fragment.ActiveProfileFragment;
import com.opensource.app.idare.view.fragment.AppTourFragment;
import com.opensource.app.idare.view.fragment.CoreListFragment;
import com.opensource.app.idare.view.fragment.DonateFragment;
import com.opensource.app.idare.view.fragment.InviteToIDareFragment;
import com.opensource.app.idare.view.fragment.SettingsFragment;

/**
 * Created by akokala on 10/31/2017.
 */

public class MainActivityViewModel extends BaseViewModel {
    private DataListener dataListener;
    private MenuItem mPreviousMenuItem;

    public MainActivityViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    public boolean onNavigationItemSelected(MenuItem menuItem, FragmentTransaction fragmentTransaction) {
        menuItem.setCheckable(true);
        menuItem.setChecked(true);

        if (mPreviousMenuItem != null) {
            mPreviousMenuItem.setChecked(false);
        }
        mPreviousMenuItem = menuItem;
        ActiveProfileFragment activeProfileFragment = ActiveProfileFragment.newInstance();
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.active_profile:
                fragment = activeProfileFragment;
                break;
            case R.id.core_list:
                CoreListFragment coreListFragment = CoreListFragment.newInstance();
                fragment = coreListFragment;
                break;
            case R.id.invite:
                InviteToIDareFragment inviteToIDareFragment = InviteToIDareFragment.newInstance();
                fragment = inviteToIDareFragment;
                break;
            case R.id.app_tour:
                AppTourFragment appTourFragment = AppTourFragment.newInstance();
                fragment = appTourFragment;
                break;
            case R.id.donate:
                DonateFragment donateFragment = DonateFragment.newInstance();
                fragment = donateFragment;
                break;
            case R.id.settings:
                SettingsFragment settingsFragment = SettingsFragment.newInstance();
                fragment = settingsFragment;
                break;
            case R.id.log_out:
                logout();
                break;
            default:
                fragment = activeProfileFragment;
                break;
        }
        if(fragment!=null) {
            dataListener.replaceFragment(fragment);
        }
        return true;
    }

    private void logout() {
        dataListener.showAlertDialog(getContext().getResources().getString(R.string.app_name), getContext().getResources().getString(R.string.logout_message), false, getContext().getResources().getString(R.string.btn_logout), getContext().getResources().getString(R.string.btn_cancel), new AlertDialogHandler() {
            @Override
            public void onPositiveButtonClicked() {
                logoutFromApp();
            }

            @Override
            public void onNegativeButtonClicked() {
            }
        });
    }

    private void logoutFromApp() {
        dataListener.getPreferences().edit().clear().commit();
        relaunch();
    }

    public void relaunch() {
        dataListener.finish();
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        dataListener.startActivity(intent);
    }

    public interface DataListener {

        void replaceFragment(Fragment fragment);

        SharedPreferences getPreferences();

        void startActivity(Intent intent);

        void finish();

        void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler);
    }
}
