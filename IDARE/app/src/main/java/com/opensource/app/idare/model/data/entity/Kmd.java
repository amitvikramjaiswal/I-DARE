package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kmd implements Parcelable {

    public static final Creator<Kmd> CREATOR = new Creator<Kmd>() {
        @Override
        public Kmd createFromParcel(Parcel source) {
            return new Kmd(source);
        }

        @Override
        public Kmd[] newArray(int size) {
            return new Kmd[size];
        }
    };
    @SerializedName("lmt")
    @Expose
    private String lmt;
    @SerializedName("ect")
    @Expose
    private String ect;
    @SerializedName("authtoken")
    @Expose
    private String authtoken;

    public Kmd() {
    }

    protected Kmd(Parcel in) {
        this.lmt = in.readString();
        this.ect = in.readString();
        this.authtoken = in.readString();
    }

    public String getLmt() {
        return lmt;
    }

    public void setLmt(String lmt) {
        this.lmt = lmt;
    }

    public String getEct() {
        return ect;
    }

    public void setEct(String ect) {
        this.ect = ect;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lmt);
        dest.writeString(this.ect);
        dest.writeString(this.authtoken);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kmd kmd = (Kmd) o;

        if (lmt != null ? !lmt.equals(kmd.lmt) : kmd.lmt != null) return false;
        if (ect != null ? !ect.equals(kmd.ect) : kmd.ect != null) return false;
        return authtoken != null ? authtoken.equals(kmd.authtoken) : kmd.authtoken == null;

    }

    @Override
    public int hashCode() {
        int result = lmt != null ? lmt.hashCode() : 0;
        result = 31 * result + (ect != null ? ect.hashCode() : 0);
        result = 31 * result + (authtoken != null ? authtoken.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Kmd{" +
                "lmt='" + lmt + '\'' +
                ", ect='" + ect + '\'' +
                ", authtoken='" + authtoken + '\'' +
                '}';
    }
}
