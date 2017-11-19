package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by amitvikramjaiswal on 19/11/17.
 */

public class IDareLocation implements Parcelable {

    public static final Creator<IDareLocation> CREATOR = new Creator<IDareLocation>() {
        @Override
        public IDareLocation createFromParcel(Parcel in) {
            return new IDareLocation(in);
        }

        @Override
        public IDareLocation[] newArray(int size) {
            return new IDareLocation[size];
        }
    };
    @SerializedName("lat")
    @Expose
    private double latitude;
    @SerializedName("lng")
    @Expose
    private double longitude;

    public IDareLocation() {
    }

    protected IDareLocation(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
