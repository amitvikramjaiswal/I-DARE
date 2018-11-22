package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.util.Log;

import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.pojo.NotificationItem;
import com.opensource.app.idare.view.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationViewModel extends IDareBaseViewModel {

    private static final String TAG = NotificationViewModel.class.getSimpleName();
    private DataListener dataListener;
    private ObservableField<RecyclerView.LayoutManager> ofLayoutManager = new ObservableField<>();
    private ObservableField<NotificationAdapter> ofAdapter = new ObservableField<>();

    public NotificationViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
        setUpRecyclerView();
        fetchNotifications();
    }

    private void setUpRecyclerView() {
        ofLayoutManager.set(new LinearLayoutManager(context));
        ofAdapter.set(new NotificationAdapter(context, new ArrayList<>()));
    }

    private void setData(List<NotificationItem> notificationItems) {
        ofAdapter.get().setNotificationItems(notificationItems);
    }

    private void fetchNotifications() {
        SessionFacadeImpl.getInstance().fetchNotifications(context, response -> {
            Log.d(TAG, "Notifications - " + response.toString());
            setData(response);
        }, error -> Log.e(TAG, "Error fetching notifications - " + error.getMessage()));
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
