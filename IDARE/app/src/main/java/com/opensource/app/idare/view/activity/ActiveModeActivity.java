package com.opensource.app.idare.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;
import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.ActivityActiveModeBinding;
import com.opensource.app.idare.viewmodel.ActiveModeViewModel;
import com.opensource.library.sosmodelib.view.activity.MapActivity;

public class ActiveModeActivity extends MapActivity {

    private static final String TAG = ActiveModeActivity.class.getSimpleName();
    private ActivityActiveModeBinding binding;
    private Toolbar toolbar;
    private ActiveModeViewModel viewModel;

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

        viewModel = new ActiveModeViewModel(this);
    }
}
