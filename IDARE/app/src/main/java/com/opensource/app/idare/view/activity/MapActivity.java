package com.opensource.app.idare.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.opensource.app.idare.R;
import com.opensource.app.idare.view.custom.IDareClusterRenderer;

import java.util.List;

public abstract class MapActivity<T extends ClusterItem> extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    protected GoogleMap googleMap;
    protected List<Marker> mMarkers;
    protected ClusterManager<T> clusterManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_safe_house);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.map_container, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    protected void setUpClusterer() {
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
    }

    protected void addClusterItem(List<T> clusterItems) {
        clusterManager.addItems(clusterItems);
        clusterManager.setAnimation(true);
        clusterManager.setRenderer(new IDareClusterRenderer<T>(this, googleMap, clusterManager));
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<T>() {
            @Override
            public boolean onClusterClick(Cluster<T> cluster) {
                setBounds((List<T>) cluster.getItems());
                return true;
            }
        });
    }

    protected void setBounds(List<T> clusterItems) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (T t : clusterItems) {
            builder.include(t.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 50;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
    }
}
