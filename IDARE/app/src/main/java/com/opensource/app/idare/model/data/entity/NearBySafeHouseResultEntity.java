package com.opensource.app.idare.model.data.entity;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;
import com.opensource.app.idare.utils.Session;

import java.util.List;

/**
 * Created by ajaiswal on 4/6/2016.
 */
public class NearBySafeHouseResultEntity implements Parcelable, ClusterItem {

    public static final Creator<NearBySafeHouseResultEntity> CREATOR = new Creator<NearBySafeHouseResultEntity>() {
        @Override
        public NearBySafeHouseResultEntity createFromParcel(Parcel in) {
            return new NearBySafeHouseResultEntity(in);
        }

        @Override
        public NearBySafeHouseResultEntity[] newArray(int size) {
            return new NearBySafeHouseResultEntity[size];
        }
    };
    @SerializedName("geometry")
    private Geometry geometry;
    @SerializedName("name")
    private String name;
    @SerializedName("place_id")
    private String placeId;
    @SerializedName("vicinity")
    private String vicinity;
    @SerializedName("types")
    private List<String> types;

    protected NearBySafeHouseResultEntity(Parcel in) {
        geometry = in.readParcelable(Geometry.class.getClassLoader());
        name = in.readString();
        placeId = in.readString();
        vicinity = in.readString();
        types = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(geometry, flags);
        dest.writeString(name);
        dest.writeString(placeId);
        dest.writeString(vicinity);
        dest.writeStringList(types);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getVicinity() {
        return vicinity;
    }

    public List<String> getTypes() {
        return types;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(geometry.getLocation().getLatitude(), geometry.getLocation().getLongitude());
    }

    @Override
    public String getTitle() {
        Location lastLocation = Session.getInstance().getLastLocation();
        float[] distance = new float[3];
        Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(), geometry.getLocation().getLatitude(), geometry.getLocation().getLongitude(), distance);
        return name + String.format(" %.2f", distance[0] / 1000f) + " KM";
    }

    @Override
    public String getSnippet() {
        return vicinity;
    }
}
