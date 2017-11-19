package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajaiswal on 4/6/2016.
 */
public class NearBySafeHouseResultEntity implements Parcelable {

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

    protected NearBySafeHouseResultEntity(Parcel in) {
        geometry = in.readParcelable(Geometry.class.getClassLoader());
        name = in.readString();
        placeId = in.readString();
        vicinity = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(geometry, flags);
        dest.writeString(name);
        dest.writeString(placeId);
        dest.writeString(vicinity);
    }
}
