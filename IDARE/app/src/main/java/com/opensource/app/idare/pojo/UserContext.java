package com.opensource.app.idare.pojo;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akokala on 11/6/2017.
 */

public class UserContext implements Parcelable {

    public static final Creator<UserContext> CREATOR = new Creator<UserContext>() {
        @Override
        public UserContext createFromParcel(Parcel source) {
            return new UserContext(source);
        }

        @Override
        public UserContext[] newArray(int size) {
            return new UserContext[size];
        }
    };
    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String mobile;
    private String alternate;

    public UserContext() {
    }

    protected UserContext(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.mobile = in.readString();
        this.alternate = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeString(this.alternate);
    }
}
