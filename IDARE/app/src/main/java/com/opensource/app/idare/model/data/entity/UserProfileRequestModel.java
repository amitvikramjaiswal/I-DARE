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
    @SerializedName("_geoloc")
    @Expose
    private double[] lonLat;

    public UserProfileRequestModel() {
    }

    protected UserProfileRequestModel(Parcel in) {
        username = in.readString();
        password = in.readString();
        name = in.readString();
        email = in.readString();
        mobile = in.readString();
        alternate = in.readString();
        iDareLocation = in.readParcelable(IDareLocation.class.getClassLoader());
        lonLat = in.createDoubleArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(mobile);
        dest.writeString(alternate);
        dest.writeParcelable(iDareLocation, flags);
        dest.writeDoubleArray(lonLat);
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

    public double[] getLonLat() {
        return lonLat;
    }

    public void setLonLat(double[] lonLat) {
        this.lonLat = lonLat;
    }


}
