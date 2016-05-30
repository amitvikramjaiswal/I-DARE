package com.opensource.app.idare.viewmodel.to;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amitvikramjaiswal on 28/05/16.
 */
public class RegisterTO implements Parcelable {

    private String mobileNumber;
    private String otp;

    public RegisterTO() {

    }

    protected RegisterTO(Parcel in) {
        mobileNumber = in.readString();
        otp = in.readString();
    }

    public static final Creator<RegisterTO> CREATOR = new Creator<RegisterTO>() {
        @Override
        public RegisterTO createFromParcel(Parcel in) {
            return new RegisterTO(in);
        }

        @Override
        public RegisterTO[] newArray(int size) {
            return new RegisterTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobileNumber);
        dest.writeString(otp);
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
