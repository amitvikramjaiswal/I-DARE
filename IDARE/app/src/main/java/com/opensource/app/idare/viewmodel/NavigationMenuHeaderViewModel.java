package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class NavigationMenuHeaderViewModel extends BaseViewModel {
    private Context context;
    private DataListener dataListener;

    private ObservableField<String> userName = new ObservableField<>("");

    public NavigationMenuHeaderViewModel(Context context,DataListener dataListener) {
        super(context);
        this.context = context;
        this.dataListener = dataListener;
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public interface DataListener{

    }
}

