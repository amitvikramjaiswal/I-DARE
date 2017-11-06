package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileResponseModel implements Parcelable {

    public static final Creator<UserProfileResponseModel> CREATOR = new Creator<UserProfileResponseModel>() {
        @Override
        public UserProfileResponseModel createFromParcel(Parcel source) {
            return new UserProfileResponseModel(source);
        }

        @Override
        public UserProfileResponseModel[] newArray(int size) {
            return new UserProfileResponseModel[size];
        }
    };
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("alternate")
    @Expose
    private String alternate;
    @SerializedName("_kmd")
    @Expose
    private Kmd kmd;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_acl")
    @Expose
    private Acl acl;

    public UserProfileResponseModel() {
    }

    protected UserProfileResponseModel(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.mobile = in.readString();
        this.alternate = in.readString();
        this.kmd = in.readParcelable(Kmd.class.getClassLoader());
        this.id = in.readString();
        this.acl = in.readParcelable(Acl.class.getClassLoader());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
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

    public Acl getAcl() {
        return acl;
    }

    public void setAcl(Acl acl) {
        this.acl = acl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeString(this.alternate);
        dest.writeParcelable(this.kmd, flags);
        dest.writeString(this.id);
        dest.writeParcelable(this.acl, flags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfileResponseModel that = (UserProfileResponseModel) o;

        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (alternate != null ? !alternate.equals(that.alternate) : that.alternate != null)
            return false;
        if (kmd != null ? !kmd.equals(that.kmd) : that.kmd != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return acl != null ? acl.equals(that.acl) : that.acl == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (alternate != null ? alternate.hashCode() : 0);
        result = 31 * result + (kmd != null ? kmd.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (acl != null ? acl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserProfileResponseModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", alternate='" + alternate + '\'' +
                ", kmd=" + kmd +
                ", id='" + id + '\'' +
                ", acl=" + acl +
                '}';
    }
}
