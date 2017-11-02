package com.opensource.app.idare.viewmodel;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.opensource.app.idare.R;
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
                break;
            default:
                fragment = activeProfileFragment;
                break;
        }
        dataListener.replaceFragment(fragment);
        return true;
    }

    public interface DataListener {

        void replaceFragment(Fragment fragment);
    }
}
