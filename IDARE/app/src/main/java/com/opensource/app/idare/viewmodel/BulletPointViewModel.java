package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class BulletPointViewModel extends BaseViewModel {

    private static final String TAG = BulletPointViewModel.class.getSimpleName();
    private DataListener dataListener;
    private ObservableField<String> observableBulletPoint = new ObservableField<>("");

    public BulletPointViewModel(Context context, DataListener dataListener, String bulletPointText) {
        super(context);
        this.dataListener = dataListener;
        observableBulletPoint.set(bulletPointText);
    }

    public ObservableField<String> getObservableBulletPoint() {
        return observableBulletPoint;
    }

    public interface DataListener extends BaseViewModel.DataListener {

    }
}
