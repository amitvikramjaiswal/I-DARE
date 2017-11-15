package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.view.activity.EditProfileActivity;

public class NavigationMenuHeaderViewModel extends BaseViewModel {
    private Context context;
    private DataListener dataListener;

    private ObservableField<String> userName = new ObservableField<>("");

    public NavigationMenuHeaderViewModel(Context context, DataListener dataListener, String userNameFromBundle) {
        super(context);
        this.context = context;
        this.dataListener = dataListener;

        if (userNameFromBundle != null) {
            userName.set(userNameFromBundle);
        } else {
            userName.set(PreferencesManager.getInstance(getContext()).getUserDetails().getName());
        }
    }

    // Onclick Navigation Header
    public View.OnClickListener onClickHeader() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OnEdit click Opens the EditProfile Screen with Prepopulated values
                dataListener.startActivity(EditProfileActivity.getStartIntent(getContext(), null));
            }
        };
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public interface DataListener {

        void startActivity(Intent intent);
    }
}

