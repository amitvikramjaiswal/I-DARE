package com.opensource.app.idare.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.opensource.app.idare.R;
import com.opensource.app.idare.model.data.entity.NearBySafeHouseResultEntity;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.utils.Utils;

public class IDareClusterRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T> {

    private Context context;
    private IconGenerator iconGenerator;

    public IDareClusterRenderer(Context context, GoogleMap map, ClusterManager<T> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
        this.iconGenerator = new IconGenerator(context);
        setupIconGen(iconGenerator, ContextCompat.getDrawable(context, R.mipmap.cluster));
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<T> cluster) {
        //start clustering if 2 or more items overlap
        return cluster.getSize() >= Constants.MINIMUM_CLUSTER_SIZE;
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<T> cluster, MarkerOptions markerOptions) {
        Bitmap icon = iconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected void onBeforeClusterItemRendered(T item,
                                               MarkerOptions markerOptions) {
        NearBySafeHouseResultEntity markerItem = (NearBySafeHouseResultEntity) item;
        markerOptions.icon(BitmapDescriptorFactory.fromResource(Utils.getResource(markerItem.getTypes())));
    }

    private void setupIconGen(IconGenerator generator, Drawable drawable) {
        TextView textView = new TextView(context);
        textView.setTextColor(ContextCompat.getColor(context, R.color.idare_white));
        textView.setId(com.google.maps.android.R.id.amu_text);
        textView.setGravity(android.view.Gravity.CENTER);
        textView.setLayoutParams(new FrameLayout.LayoutParams(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()));
        generator.setContentView(textView);
        generator.setBackground(drawable);
    }

}
