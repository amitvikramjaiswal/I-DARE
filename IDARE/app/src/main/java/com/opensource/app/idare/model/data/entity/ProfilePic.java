package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePic implements Parcelable {

    public static final Creator<ProfilePic> CREATOR = new Creator<ProfilePic>() {
        @Override
        public ProfilePic createFromParcel(Parcel in) {
            return new ProfilePic(in);
        }

        @Override
        public ProfilePic[] newArray(int size) {
            return new ProfilePic[size];
        }
    };
    @SerializedName("profilePicUri")
    @Expose
    private String profilePicUri;
    @SerializedName("base64Image")
    @Expose
    private String base64Image;
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


    public ProfilePic() {

    }

    protected ProfilePic(Parcel in) {
        profilePicUri = in.readString();
        base64Image = in.readString();
        userId = in.readString();
        acl = in.readParcelable(Acl.class.getClassLoader());
        kmd = in.readParcelable(Kmd.class.getClassLoader());
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profilePicUri);
        dest.writeString(base64Image);
        dest.writeString(userId);
        dest.writeParcelable(acl, flags);
        dest.writeParcelable(kmd, flags);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getProfilePicUri() {
        return profilePicUri;
    }

    public void setProfilePicUri(String profilePicUri) {
        this.profilePicUri = profilePicUri;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
