package com.opensource.app.idare.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.opensource.app.idare.view.fragment.DailyReminderFragment;
import com.opensource.app.idare.view.fragment.WhileWalkingFragment;

public class SafePracticesPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = SafePracticesPagerAdapter.class.getSimpleName();

    public SafePracticesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = DailyReminderFragment.newInstance();
                break;
            case 1:
                fragment = WhileWalkingFragment.newInstance();
                break;
            default:
                fragment = DailyReminderFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Daily Reminders";
            case 1:
                return "While Walking";
            default:
                return "Daily Reminders";
        }
    }
}
