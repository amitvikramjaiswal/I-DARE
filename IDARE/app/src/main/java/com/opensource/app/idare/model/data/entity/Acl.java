
package com.opensource.app.idare.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Acl implements Parcelable {

    @SerializedName("creator")
    @Expose
    private String creator;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.creator);
    }

    public Acl() {
    }

    protected Acl(Parcel in) {
        this.creator = in.readString();
    }

    public static final Creator<Acl> CREATOR = new Creator<Acl>() {
        @Override
        public Acl createFromParcel(Parcel source) {
            return new Acl(source);
        }

        @Override
        public Acl[] newArray(int size) {
            return new Acl[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Acl acl = (Acl) o;

        return creator != null ? creator.equals(acl.creator) : acl.creator == null;

    }

    @Override
    public int hashCode() {
        return creator != null ? creator.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Acl{" +
                "creator='" + creator + '\'' +
                '}';
    }
}
