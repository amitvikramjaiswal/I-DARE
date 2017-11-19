package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by amitvikramjaiswal on 18/11/17.
 */

public class RegisterDeviceResponse implements Parcelable {

    public static final Creator<RegisterDeviceResponse> CREATOR = new Creator<RegisterDeviceResponse>() {
        @Override
        public RegisterDeviceResponse createFromParcel(Parcel in) {
            return new RegisterDeviceResponse(in);
        }

        @Override
        public RegisterDeviceResponse[] newArray(int size) {
            return new RegisterDeviceResponse[size];
        }
    };
    @SerializedName("platform")
    @Expose
    private String platform;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("_acl")
    @Expose
    private Acl acl;
    @SerializedName("_kmd")
    @Expose
    private Kmd kmd;
    @SerializedName("_id")
    @Expose
    private String id;

    protected RegisterDeviceResponse(Parcel in) {
        platform = in.readString();
        deviceId = in.readString();
        userId = in.readString();
        acl = in.readParcelable(Acl.class.getClassLoader());
        kmd = in.readParcelable(Kmd.class.getClassLoader());
        id = in.readString();
    }

    public String getPlatform() {
        return platform;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public Acl getAcl() {
        return acl;
    }

    public Kmd getKmd() {
        return kmd;
    }

    public String getId() {
        return id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(platform);
        dest.writeString(deviceId);
        dest.writeString(userId);
        dest.writeParcelable(acl, flags);
        dest.writeParcelable(kmd, flags);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
