package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;

import com.opensource.app.idare.R;
import com.opensource.app.idare.view.adapter.SafePracticesPagerAdapter;

public class SafePracticesViewModel extends BaseViewModel {

    private static final String TAG = SafePracticesViewModel.class.getSimpleName();
    private ObservableField<SafePracticesPagerAdapter> observableAdapter = new ObservableField<>();
    private ObservableField<Integer> observableColor = new ObservableField<>();

    public SafePracticesViewModel(Context context, DataListener dataListener) {
        super(context);
        observableAdapter.set(new SafePracticesPagerAdapter(dataListener.getSupportFragmentManager(), context));
        observableColor.set(R.color.idare_pink);
    }
    
    public ObservableField<SafePracticesPagerAdapter> getObservableAdapter() {
        return observableAdapter;
    }

    public ObservableField<Integer> getObservableColor() {
        return observableColor;
    }

    public interface DataListener extends BaseViewModel.DataListener {

        FragmentManager getSupportFragmentManager();
    }
}
