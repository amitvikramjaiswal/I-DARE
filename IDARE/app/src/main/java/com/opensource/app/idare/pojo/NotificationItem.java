package com.opensource.app.idare.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.opensource.library.sosmodelib.model.IDUser;

/**
 *
 */
public class NotificationItem implements Parcelable {

    public static final Creator<NotificationItem> CREATOR = new Creator<NotificationItem>() {
        @Override
        public NotificationItem createFromParcel(Parcel in) {
            return new NotificationItem(in);
        }

        @Override
        public NotificationItem[] newArray(int size) {
            return new NotificationItem[size];
        }
    };
    private String title;
    private String body;
    private String notificationType;
    private IDUser idUser;

    protected NotificationItem(Parcel in) {
        title = in.readString();
        body = in.readString();
        notificationType = in.readString();
        idUser = in.readParcelable(IDUser.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(notificationType);
        dest.writeParcelable(idUser, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public IDUser getIdUser() {
        return idUser;
    }

    public void setIdUser(IDUser idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "NotificationItem{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}
