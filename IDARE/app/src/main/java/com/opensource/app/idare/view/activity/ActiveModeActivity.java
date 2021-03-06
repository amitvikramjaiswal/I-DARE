package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.android.clustering.ClusterManager;
import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.ActivityActiveModeBinding;
import com.opensource.app.idare.model.data.entity.NearBySafeHouseResultEntity;
import com.opensource.app.idare.viewmodel.ActiveModeViewModel;
import com.opensource.library.sosmodelib.view.activity.MapActivity;

import java.util.List;

public class ActiveModeActivity extends MapActivity implements ActiveModeViewModel.DataListener {

    private static final String TAG = ActiveModeActivity.class.getSimpleName();
    private ActivityActiveModeBinding binding;
    private Toolbar toolbar;
    private ActiveModeViewModel viewModel;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ActiveModeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_active_mode);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.map_container, mapFragment).commit();
        mapFragment.getMapAsync(this);

        viewModel = new ActiveModeViewModel(this, this);
        binding.setViewModel(viewModel);
    }

    @Override
    public void setUpClusterer() {
        clusterManager = new ClusterManager<NearBySafeHouseResultEntity>(this, googleMap);
        super.setUpClusterer();
    }

    @Override
    public void addClusterItem(List clusterItems) {
        super.addClusterItem(clusterItems);
        setBounds(clusterItems);
    }
}
