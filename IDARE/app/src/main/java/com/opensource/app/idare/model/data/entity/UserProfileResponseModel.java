package com.opensource.app.idare.model.data.entity;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;
import com.opensource.app.idare.utils.Session;

public class UserProfileResponseModel implements Parcelable, ClusterItem {

    public static final Creator<UserProfileResponseModel> CREATOR = new Creator<UserProfileResponseModel>() {
        @Override
        public UserProfileResponseModel createFromParcel(Parcel in) {
            return new UserProfileResponseModel(in);
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
    @SerializedName("location")
    @Expose
    private IDareLocation iDareLocation;
    @SerializedName("_geoloc")
    @Expose
    private double[] lonLat;


    public UserProfileResponseModel() {
    }

    protected UserProfileResponseModel(Parcel in) {
        username = in.readString();
        password = in.readString();
        name = in.readString();
        email = in.readString();
        mobile = in.readString();
        alternate = in.readString();
        kmd = in.readParcelable(Kmd.class.getClassLoader());
        id = in.readString();
        acl = in.readParcelable(Acl.class.getClassLoader());
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
        dest.writeParcelable(kmd, flags);
        dest.writeString(id);
        dest.writeParcelable(acl, flags);
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

    @Override
    public LatLng getPosition() {
        return new LatLng(iDareLocation.getLatitude(), iDareLocation.getLongitude());
    }

    @Override
    public String getTitle() {
        return "I-DARE User";
    }

    @Override
    public String getSnippet() {
        float[] distance = new float[3];
        Location lastLocation = Session.getInstance().getLastLocation();
        Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(), iDareLocation.getLatitude(), iDareLocation.getLongitude(), distance);
        return String.format("%.2f ", distance[0] / 1000f) + "KMs away.";
    }
}
