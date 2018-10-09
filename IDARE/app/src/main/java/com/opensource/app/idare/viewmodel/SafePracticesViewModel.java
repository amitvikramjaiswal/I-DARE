package com.opensource.app.idare.viewmodel;

import android.content.Context;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerTabStrip;

import com.opensource.app.idare.R;
import com.opensource.app.idare.view.adapter.SafePracticesPagerAdapter;

public class SafePracticesViewModel extends IDareBaseViewModel {

    private static final String TAG = SafePracticesViewModel.class.getSimpleName();
    private ObservableField<SafePracticesPagerAdapter> observableAdapter = new ObservableField<>();
    private ObservableField<Integer> observableColor = new ObservableField<>();

    public SafePracticesViewModel(Context context, DataListener dataListener) {
        super(context);
        observableAdapter.set(new SafePracticesPagerAdapter(dataListener.getSupportFragmentManager(), context));
        observableColor.set(R.color.idare_pink);
    }

    @BindingAdapter("android:selectedTabColor")
    public static void setSelectedTabColor(PagerTabStrip tabStrip, Integer color) {
        tabStrip.setTabIndicatorColorResource(color);
    }

    public ObservableField<SafePracticesPagerAdapter> getObservableAdapter() {
        return observableAdapter;
    }

    public ObservableField<Integer> getObservableColor() {
        return observableColor;
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {

        FragmentManager getSupportFragmentManager();
    }
}
