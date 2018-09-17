package com.opensource.app.idare.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.opensource.app.idare.R;
import com.opensource.app.idare.view.fragment.SafePracticesPagerFragment;

public class SafePracticesPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = SafePracticesPagerAdapter.class.getSimpleName();
    private Context context;

    public SafePracticesPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        int arrResId;
        switch (position) {
            case 0:
                arrResId = R.array.arr_safe_prac_passive_val;
                break;
            case 1:
                arrResId = R.array.arr_safe_prac_walking_val;
                break;
            case 2:
                arrResId = R.array.arr_safe_prac_cab_val;
                break;
            case 3:
                arrResId = R.array.arr_safe_prac_elevator_val;
                break;
            case 4:
                arrResId = R.array.arr_safe_prac_social_media_val;
                break;
            case 5:
                arrResId = R.array.arr_safe_prac_active_val;
                break;
            case 6:
                arrResId = R.array.arr_safe_prac_sos_alert_val;
                break;
            default:
                arrResId = R.array.arr_safe_prac_passive_val;
                break;
        }
        return SafePracticesPagerFragment.newInstance(arrResId);
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.safe_prac_passive_title_1);
            case 1:
                return context.getString(R.string.safe_prac_while_walking_title);
            case 2:
                return context.getString(R.string.safe_prac_in_a_cab_title);
            case 3:
                return context.getString(R.string.safe_prac_in_an_elevator);
            case 4:
                return  context.getString(R.string.safe_prac_title_2);
            case 5:
                return context.getString(R.string.safe_prac_active_title);
            case 6:
                return context.getString(R.string.safe_prac_sos_title);
            default:
                return context.getString(R.string.daily_reminders);
        }
    }
}
