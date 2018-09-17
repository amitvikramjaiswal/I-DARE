package com.opensource.app.idare.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.LayoutBulletPointViewBinding;
import com.opensource.app.idare.viewmodel.BulletPointViewModel;

import java.util.List;

public class BulletPointsAdapter extends RecyclerView.Adapter<BulletPointsAdapter.BulletPointViewHolder> {

    private List<String> arlBulletPoints;
    private Context context;

    public BulletPointsAdapter(Context context, List<String> arlBulletPoints) {
        this.context = context;
        this.arlBulletPoints = arlBulletPoints;
    }

    @Override
    public BulletPointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutBulletPointViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_bullet_point_view, parent, false);
        return new BulletPointViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BulletPointViewHolder holder, int position) {
        holder.bindData(context, null, arlBulletPoints.get(position));
    }

    @Override
    public int getItemCount() {
        return arlBulletPoints.size();
    }

    public static class BulletPointViewHolder extends RecyclerView.ViewHolder {
        private LayoutBulletPointViewBinding binding;

        public BulletPointViewHolder(LayoutBulletPointViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindData(Context context, BulletPointViewModel.DataListener dataListener, String bulletPoint) {
            binding.setViewModel(new BulletPointViewModel(context, dataListener, bulletPoint));
        }
    }
}
