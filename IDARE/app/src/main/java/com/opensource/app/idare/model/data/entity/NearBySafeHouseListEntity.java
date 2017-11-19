package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ajaiswal on 4/7/2016.
 */
public class NearBySafeHouseListEntity implements Parcelable {
    public static final Creator<NearBySafeHouseListEntity> CREATOR = new Creator<NearBySafeHouseListEntity>() {
        @Override
        public NearBySafeHouseListEntity createFromParcel(Parcel in) {
            return new NearBySafeHouseListEntity(in);
        }

        @Override
        public NearBySafeHouseListEntity[] newArray(int size) {
            return new NearBySafeHouseListEntity[size];
        }
    };
    @SerializedName("results")
    private List<NearBySafeHouseResultEntity> nearBySafeHouseResultEntities;
    @SerializedName("next_page_token")
    private String nextPageToken;
    @SerializedName("status")
    private String status;

    protected NearBySafeHouseListEntity(Parcel in) {
        nearBySafeHouseResultEntities = in.createTypedArrayList(NearBySafeHouseResultEntity.CREATOR);
        nextPageToken = in.readString();
        status = in.readString();
    }

    public List<NearBySafeHouseResultEntity> getNearBySafeHouseResultEntities() {
        return nearBySafeHouseResultEntities;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(nearBySafeHouseResultEntities);
        dest.writeString(nextPageToken);
        dest.writeString(status);
    }
}
