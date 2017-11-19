package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.opensource.app.idare.utils.Session;
import com.opensource.app.idare.view.activity.EditProfileActivity;

public class NavigationMenuHeaderViewModel extends BaseViewModel {
    private Context context;
    private DataListener dataListener;

    private ObservableField<String> userName = new ObservableField<>("");

    public NavigationMenuHeaderViewModel(Context context, DataListener dataListener, String userNameFromBundle) {
        super(context);
        this.context = context;
        this.dataListener = dataListener;
        userName.set(Session.getInstance().getUserProfileResponseModel().getName());
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

