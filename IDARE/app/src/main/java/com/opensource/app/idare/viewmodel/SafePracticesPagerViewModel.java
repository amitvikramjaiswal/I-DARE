package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;

import com.opensource.app.idare.view.adapter.BulletPointsAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class SafePracticesPagerViewModel extends BaseViewModel {

    private static final String TAG = SafePracticesPagerViewModel.class.getSimpleName();
    private DataListener dataListener;
    private ObservableField<LinearLayoutManager> layoutManager = new ObservableField<>();
    private ObservableField<BulletPointsAdapter> observableBulletPoint = new ObservableField<>();

    public SafePracticesPagerViewModel(Context context, int arrResId, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
        ArrayList<String> arlBulletPoint = new ArrayList<>(Arrays.asList(getContext().getResources().getStringArray(arrResId)));
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
