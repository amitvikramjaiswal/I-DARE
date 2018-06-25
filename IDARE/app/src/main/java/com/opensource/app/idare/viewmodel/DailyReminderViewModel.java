package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;

import com.opensource.app.idare.R;
import com.opensource.app.idare.view.adapter.BulletPointsAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class DailyReminderViewModel extends BaseViewModel {

    private static final String TAG = DailyReminderViewModel.class.getSimpleName();
    private DataListener dataListener;
    private ObservableField<LinearLayoutManager> layoutManager = new ObservableField<>();
    private ObservableField<BulletPointsAdapter> observableBulletPoint = new ObservableField<>();

    public DailyReminderViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
        ArrayList<String> arlBulletPoint = (ArrayList<String>) Arrays.asList(getContext().getResources().getStringArray(R.array.arr_safe_prac_passive_val));
        layoutManager.set(new LinearLayoutManager(getContext()));
        observableBulletPoint.set(new BulletPointsAdapter(getContext(), arlBulletPoint));
    }

    public ObservableField<LinearLayoutManager> getLayoutManager() {
        return layoutManager;
    }

    public ObservableField<BulletPointsAdapter> getObservableBulletPoint() {
        return observableBulletPoint;
    }

    public interface DataListener extends BaseViewModel.DataListener {

    }
}
