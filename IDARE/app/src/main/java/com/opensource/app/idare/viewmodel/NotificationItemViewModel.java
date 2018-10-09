package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.view.View;

import com.opensource.app.idare.pojo.NotificationItem;

import androidx.databinding.ObservableField;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class NotificationItemViewModel extends IDareBaseViewModel {

    private DataListener dataListener;
    private ObservableField<String> ofTitle = new ObservableField<>("Title");
    private ObservableField<String> ofBody = new ObservableField<>("Notification body will replace this.");
    private ObservableField<Integer> ofVisibility = new ObservableField<>(VISIBLE);

    public NotificationItemViewModel(Context context, DataListener dataListener, NotificationItem notificationItem) {
        super(context);
        this.dataListener = dataListener;
        setData(notificationItem);
    }

    private void setData(NotificationItem notificationItem) {
        ofTitle.set(notificationItem.getTitle());
        ofBody.set(notificationItem.getBody());
        if ("DISTRESS".equalsIgnoreCase(notificationItem.getNotificationType())) {
            ofVisibility.set(VISIBLE);
        } else {
            ofVisibility.set(GONE);
        }
    }

    public ObservableField<String> getOfTitle() {
        return ofTitle;
    }

    public ObservableField<String> getOfBody() {
        return ofBody;
    }

    public ObservableField<Integer> getOfVisibility() {
        return ofVisibility;
    }

    public View.OnClickListener onAccept() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// TODO: 10/9/2018 start sosmodeactivity
            }
        };
    }

    public View.OnClickListener onReject() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// TODO: 10/9/2018 reject
            }
        };
    }

    public interface DataListener {

    }
}
