package com.opensource.app.idare.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentNotificationItemBinding;
import com.opensource.app.idare.pojo.NotificationItem;
import com.opensource.app.idare.viewmodel.NotificationItemViewModel;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> implements NotificationItemViewModel.DataListener {

    private final List<NotificationItem> notificationItems;
    private final Context context;

    public NotificationAdapter(Context context, List<NotificationItem> notificationItems) {
        this.context = context;
        this.notificationItems = notificationItems;
    }

    public void setNotificationItems(List<NotificationItem> notificationItems) {
        this.notificationItems.addAll(notificationItems);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentNotificationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.fragment_notification_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindData(notificationItems.get(position), this);
    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentNotificationItemBinding binding;

        public ViewHolder(FragmentNotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(NotificationItem notificationItem, NotificationItemViewModel.DataListener dataListener) {
            binding.setViewModel(new NotificationItemViewModel(context, dataListener, notificationItem));
        }
    }
}
