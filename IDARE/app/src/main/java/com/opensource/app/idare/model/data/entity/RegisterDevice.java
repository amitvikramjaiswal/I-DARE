package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amitvikramjaiswal on 18/11/17.
 */

public class RegisterDevice implements Parcelable {

    public static final Creator<RegisterDevice> CREATOR = new Creator<RegisterDevice>() {
        @Override
        public RegisterDevice createFromParcel(Parcel in) {
            return new RegisterDevice(in);
        }

        @Override
        public RegisterDevice[] newArray(int size) {
            return new RegisterDevice[size];
        }
    };
    private String platform;
    private String deviceId;
    private String userId;

    public RegisterDevice() {
    }

    protected RegisterDevice(Parcel in) {
        platform = in.readString();
        deviceId = in.readString();
        userId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(platform);
        dest.writeString(deviceId);
        dest.writeString(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
