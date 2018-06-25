package com.opensource.app.idare.view.adapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.LayoutBulletPointViewBinding;
import com.opensource.app.idare.viewmodel.BulletPointViewModel;

public class IDareBindingAdapter {

    @BindingAdapter("android:bulletPoint")
    public static void inflateMessagesLayout(FrameLayout parent, BulletPointViewModel viewModel) {
        LayoutBulletPointViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_bullet_point_view, parent, false);
        parent.addView(binding.getRoot());
        binding.setViewModel(viewModel);
    }

}
