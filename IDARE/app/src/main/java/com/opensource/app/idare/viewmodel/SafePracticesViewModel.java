package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v4.app.FragmentManager;

import com.opensource.app.idare.view.adapter.SafePracticesPagerAdapter;

public class SafePracticesViewModel extends BaseViewModel {

    private static final String TAG = SafePracticesViewModel.class.getSimpleName();
    private ObservableField<SafePracticesPagerAdapter> observableAdapter = new ObservableField<>();

    public SafePracticesViewModel(Context context, DataListener dataListener) {
        super(context);
        observableAdapter.set(new SafePracticesPagerAdapter(dataListener.getSupportFragmentManager()));
    }

    public ObservableField<SafePracticesPagerAdapter> getObservableAdapter() {
        return observableAdapter;
    }

    public interface DataListener extends BaseViewModel.DataListener {

        FragmentManager getSupportFragmentManager();
    }
}
