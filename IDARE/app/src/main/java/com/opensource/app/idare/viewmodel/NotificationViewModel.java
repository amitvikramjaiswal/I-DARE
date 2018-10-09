package com.opensource.app.idare.viewmodel;

import android.content.Context;

import com.opensource.app.idare.view.adapter.NotificationAdapter;

import java.util.Collections;

import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationViewModel extends IDareBaseViewModel {

    private DataListener dataListener;
    private ObservableField<RecyclerView.LayoutManager> ofLayoutManager = new ObservableField<>();
    private ObservableField<NotificationAdapter> ofAdapter = new ObservableField<>();

    public NotificationViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        ofLayoutManager.set(new LinearLayoutManager(context));
        ofAdapter.set(new NotificationAdapter(context, Collections.EMPTY_LIST));
    }

    public ObservableField<RecyclerView.LayoutManager> getOfLayoutManager() {
        return ofLayoutManager;
    }

    public ObservableField<NotificationAdapter> getOfAdapter() {
        return ofAdapter;
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {

    }
}
