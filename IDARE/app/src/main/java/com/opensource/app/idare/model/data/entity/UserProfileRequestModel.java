package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akokala on 11/6/2017.
 */

public class UserProfileRequestModel implements Parcelable {


    public static final Creator<UserProfileRequestModel> CREATOR = new Creator<UserProfileRequestModel>() {
        @Override
        public UserProfileRequestModel createFromParcel(Parcel in) {
            return new UserProfileRequestModel(in);
        }

        @Override
        public UserProfileRequestModel[] newArray(int size) {
            return new UserProfileRequestModel[size];
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
    @SerializedName("location")
    @Expose
    private IDareLocation iDareLocation;


    public UserProfileRequestModel() {
    }

    protected UserProfileRequestModel(Parcel in) {
        username = in.readString();
        password = in.readString();
        name = in.readString();
        email = in.readString();
        mobile = in.readString();
        alternate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(mobile);
        dest.writeString(alternate);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public IDareLocation getiDareLocation() {
        return iDareLocation;
    }

    public void setiDareLocation(IDareLocation iDareLocation) {
        this.iDareLocation = iDareLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfileRequestModel that = (UserProfileRequestModel) o;

        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        return alternate != null ? alternate.equals(that.alternate) : that.alternate == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (alternate != null ? alternate.hashCode() : 0);
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
                '}';
    }
}
