package com.opensource.app.idare.viewmodel;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.opensource.app.idare.R;
import com.opensource.app.idare.view.fragment.ActiveProfileFragment;
import com.opensource.app.idare.view.fragment.AppTourFragment;

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
        AppTourFragment appTourFragment = AppTourFragment.newInstance();
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.app_tour:
                fragment = appTourFragment;
                break;
            case R.id.active_profile:
                ActiveProfileFragment activeProfileFragment = ActiveProfileFragment.newInstance();
                fragment = activeProfileFragment;
                break;
            default:
                fragment = appTourFragment;
                break;
        }
        dataListener.replaceFragment(fragment);
        return true;
    }

    public interface DataListener {

        void replaceFragment(Fragment fragment);
    }
}
