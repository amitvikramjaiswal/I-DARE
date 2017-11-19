package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajaiswal on 4/6/2016.
 */
public class Geometry implements Parcelable {

    public static final Creator<Geometry> CREATOR = new Creator<Geometry>() {
        @Override
        public Geometry createFromParcel(Parcel in) {
            return new Geometry(in);
        }

        @Override
        public Geometry[] newArray(int size) {
            return new Geometry[size];
        }
    };
    @SerializedName("location")
    private IDareLocation location;

    protected Geometry(Parcel in) {
        location = in.readParcelable(IDareLocation.class.getClassLoader());
    }

    public IDareLocation getLocation() {
        return location;
    }

    public void setLocation(IDareLocation location) {
        this.location = location;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(location, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
