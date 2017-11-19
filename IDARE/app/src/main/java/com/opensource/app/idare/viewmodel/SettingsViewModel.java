package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.adapters.SeekBarBindingAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.opensource.app.idare.R;
import com.opensource.app.idare.utils.Session;

/**
 * Created by akokala on 11/2/2017.
 */

public class SettingsViewModel extends BaseViewModel {

    private ObservableField<Boolean> isShoppingMallChecked = new ObservableField<>(true);
    private ObservableField<Boolean> isPoliceStationChecked = new ObservableField<>(true);
    private ObservableField<Boolean> isCafeChecked = new ObservableField<>(true);
    private ObservableField<Integer> progressValue = new ObservableField<>(5);
    private ObservableField<String> progressLabel = new ObservableField<>("");
    private DataListener dataListener;

    public SettingsViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
        progressLabel.set(progressValue.get().toString());
    }

    public CompoundButton.OnCheckedChangeListener onCheckChanged() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.shopping_mall:
                        isShoppingMallChecked.set(isChecked);
                        updateUserContext();
                        break;
                    case R.id.police_station:
                        isPoliceStationChecked.set(isChecked);
                        updateUserContext();
                        break;
                    case R.id.cafe:
                        isCafeChecked.set(isChecked);
                        updateUserContext();
                        break;
                }
            }
        };
    }

    private void updateUserContext() {
        Session.getInstance().setSettingsData(isShoppingMallChecked.get(), isPoliceStationChecked.get(), isCafeChecked.get(), progressValue.get());
    }

    public SeekBarBindingAdapter.OnProgressChanged onProgressChanged() {
        return new SeekBarBindingAdapter.OnProgressChanged() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue.set(progress);
                progressLabel.set(getFormattedRadius(progress));
                updateUserContext();
            }
        };
    }

    private String getFormattedRadius(int progress) {
        String formattedRadius;
        if (progress > 1) {
            formattedRadius = String.format("%s kms", progress);
        } else {
            formattedRadius = String.format("%s km", progress);
        }
        return formattedRadius;
    }

    public ObservableField<Boolean> getIsShoppingMallChecked() {
        return isShoppingMallChecked;
    }

    public ObservableField<Boolean> getIsPoliceStationChecked() {
        return isPoliceStationChecked;
    }

    public ObservableField<Boolean> getIsCafeChecked() {
        return isCafeChecked;
    }

    public ObservableField<Integer> getProgressValue() {
        return progressValue;
    }

    public ObservableField<String> getProgressLabel() {
        return progressLabel;
    }

    public interface DataListener extends BaseViewModel.DataListener {

    }
}
