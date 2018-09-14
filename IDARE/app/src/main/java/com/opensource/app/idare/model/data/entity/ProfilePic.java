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
    @SerializedName("arrByteImage")
    @Expose
    private byte[] arrByteImage;
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
        arrByteImage = in.createByteArray();
        userId = in.readString();
        acl = in.readParcelable(Acl.class.getClassLoader());
        kmd = in.readParcelable(Kmd.class.getClassLoader());
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profilePicUri);
        dest.writeByteArray(arrByteImage);
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

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte[] getArrByteImage() {
        return arrByteImage;
    }

    public void setArrByteImage(byte[] arrByteImage) {
        this.arrByteImage = arrByteImage;
    }

    public Acl getAcl() {
        return acl;
    }

    public void setAcl(Acl acl) {
        this.acl = acl;
    }

    public Kmd getKmd() {
        return kmd;
    }

    public void setKmd(Kmd kmd) {
        this.kmd = kmd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
