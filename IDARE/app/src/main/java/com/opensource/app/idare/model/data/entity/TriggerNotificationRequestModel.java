package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TriggerNotificationRequestModel implements Parcelable {

    public static final Creator<TriggerNotificationRequestModel> CREATOR = new Creator<TriggerNotificationRequestModel>() {
        @Override
        public TriggerNotificationRequestModel createFromParcel(Parcel in) {
            return new TriggerNotificationRequestModel(in);
        }

        @Override
        public TriggerNotificationRequestModel[] newArray(int size) {
            return new TriggerNotificationRequestModel[size];
        }
    };
    @SerializedName("userIds")
    @Expose
    private String[] userIds;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private IDareLocation location;

    public TriggerNotificationRequestModel(String[] userIds, String name, IDareLocation location) {
        this.userIds = userIds;
        this.name = name;
        this.location = location;
    }

    protected TriggerNotificationRequestModel(Parcel in) {
        userIds = in.createStringArray();
        name = in.readString();
        location = in.readParcelable(IDareLocation.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(userIds);
        dest.writeString(name);
        dest.writeParcelable(location, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IDareLocation getLocation() {
        return location;
    }

    public void setLocation(IDareLocation location) {
        this.location = location;
    }
}
